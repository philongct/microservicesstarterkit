package l.nguyen.ms.transaction.service;

import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.common.model.transaction.GeneratedAuthCode;
import l.nguyen.ms.common.model.transaction.TransactionAuthResponse;
import l.nguyen.ms.common.model.transaction.TransactionAuthResponse.AuthorizationStatus;
import l.nguyen.ms.transaction.repository.AuthCodeRepository;
import l.nguyen.ms.transaction.repository.TransactionRepository;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AuthCodeService {

    private Map<String, AtomicInteger> authCodes = new ConcurrentHashMap<>();

    @Autowired
    private AuthCodeRepository authCodeRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransactionValidatorService transactionValidationService;

    @Transactional
    public TransactionAuthResponse authorizeTransaction(String requestBank, CreditCardTransaction transaction) {
        if (transactionValidationService.accept(transaction)) {
            authCodes.computeIfAbsent(requestBank, k -> new AtomicInteger(0));

            int authCodeValue = authCodes.get(requestBank).getAndIncrement() % 999999;
            GeneratedAuthCode authCode = new GeneratedAuthCode(requestBank, authCodeValue);
            authCodeRepository.save(authCode);

            transaction.setAuthCode(authCode);
            transactionRepository.save(transaction);

            TransactionAuthResponse resp = new TransactionAuthResponse(AuthorizationStatus.APPROVED,
                    StringUtils.leftPad(Integer.toString(authCode.getAuthCode()), 6, "0"),
                    authCode.getGeneratedDt());
            return resp;
        }

        return new TransactionAuthResponse(AuthorizationStatus.DENIED, null, new Date());
    }

    public List<CreditCardTransaction> getCreditTransactions(String requestBank, int fromAuthCode, int toAuthCode, Date transactionDate) {
        Date startOfDay = new DateTime(transactionDate).withTimeAtStartOfDay().toDate();
        Date endOfDay = new DateTime(transactionDate).withTime(23, 59, 59, 999).toDate();

        Specification<CreditCardTransaction> cctSpec = (root, cq, cb) -> {
            Root<GeneratedAuthCode> authCodeRoot = cq.from(GeneratedAuthCode.class);
            Predicate authCodePredicate = cb.between(authCodeRoot.get("authCode"), fromAuthCode, toAuthCode);
            Predicate datetimePredicate = cb.between(authCodeRoot.get("generatedDt"), startOfDay, endOfDay);
            Predicate requestBankPredicate = cb.equal(authCodeRoot.get("requestBank"), requestBank);
            Predicate authCodeCondition = cb.and(authCodePredicate, datetimePredicate, requestBankPredicate);

            root.fetch("authCode", JoinType.LEFT);
            return cq.where(authCodeCondition).getRestriction();
        };
        return transactionRepository.findAll(cctSpec);
    }
}
