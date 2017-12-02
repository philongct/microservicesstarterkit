package l.nguyen.ms.transaction.controller;

import java.util.Date;
import java.util.List;

import l.nguyen.ms.common.controller.TransactionControllerApi;
import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.common.model.transaction.GeneratedAuthCode;
import l.nguyen.ms.common.model.transaction.TransactionAuthResponse;
import l.nguyen.ms.transaction.repository.AuthCodeRepository;
import l.nguyen.ms.transaction.service.AuthCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController implements TransactionControllerApi {

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private AuthCodeService authCodeService;

	@Autowired
	private AuthCodeRepository authCodeRepository;

	/**
	 * To access this resource directly: <br>
	 * (1)curl "http://anyclient:clientsecret@localhost:9999/uaa/oauth/token" -d "password=user_abc&username=user_abc&grant_type=password&scope=openid" <br>
	 * (2)curl -H "Authorization: Bearer [token]" -H "Content-Type: application/json" -d '{"cardNumber":"123456","paymentReason":"noreason","moneyAmount":2.0}' http://localhost:6000/transaction/authcode/requestBank
	 *
	 * @param requestBank
	 * @return
	 */
	@PostMapping("/authcode/{requestBank}")
	@PreAuthorize("#oauth2.hasScope('openid') && hasAuthority('abc')")
	public TransactionAuthResponse generateAuthCode(@PathVariable String requestBank, @RequestBody CreditCardTransaction transaction) {
		return authCodeService.authorizeTransaction(requestBank, transaction);
	}

	/**
	 * For Microservice client
	 *
	 * @param requestBank
	 * @param fromAuthCode
	 * @param toAuthCode
	 * @return
	 */
	@PreAuthorize("#oauth2.hasScope('openid')")
	@Override
	public List<GeneratedAuthCode> getGeneratedAuthCodes(@PathVariable String requestBank,
														 @PathVariable int fromAuthCode,
														 @PathVariable int toAuthCode) {
		return authCodeRepository.findAllByRequestBankAndAuthCodeBetween(requestBank, fromAuthCode, toAuthCode);
	}

	/**
	 * For Microservice client
	 *
	 * @param requestBank
	 * @param transactionDate
	 * @param fromAuthCode
	 * @param toAuthCode
	 * @return
	 */
	@PreAuthorize("#oauth2.hasScope('openid')")
	@Override
	public List<CreditCardTransaction> getTransactionsByAuthCodes(@PathVariable String requestBank,
																  @PathVariable Date transactionDate,
																  @PathVariable int fromAuthCode,
																  @PathVariable int toAuthCode) {
		return authCodeService.getCreditTransactions(requestBank, fromAuthCode, toAuthCode, transactionDate);
	}
}
