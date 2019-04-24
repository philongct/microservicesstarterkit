package l.nguyen.ms.transaction.controller;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import l.nguyen.ms.common.controller.TransactionControllerApi;
import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.common.model.transaction.TransactionAuthResponse;
import l.nguyen.ms.transaction.repository.AuthCodeRepository;
import l.nguyen.ms.transaction.service.AuthCodeService;
import l.nguyen.ms.transaction.service.TransactionGeneratorService;

@RestController
public class TransactionController implements TransactionControllerApi {

	private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

	@Autowired
	private AuthCodeService authCodeService;

	@Autowired
	private AuthCodeRepository authCodeRepository;

	@Autowired
	private TransactionGeneratorService transactionGeneratorService;

	/**
	 * To access this resource directly: <br>
	 * (1)curl "http://anyclient:clientsecret@localhost:9999/uaa/oauth/token" -d "password=user_abc&username=user_abc&grant_type=password&scope=openid" <br>
	 * (2)curl -H "Authorization: Bearer [token]" http://localhost:6000/transaction/authcode/simulate
	 *
	 * @return
	 */
    // Disclaimers: This is just spaghetti code to expose "RPC"
	// It doesn't follow RESTFul API design. I don't have time for it yet
	@GetMapping("/authcode/simulate")
	@PreAuthorize("#oauth2.hasScope('openid') && hasAuthority('abc')")
	public String generateAuthCode(HttpServletRequest request) {
		transactionGeneratorService.startSimulation(request.getHeader("Authorization"),
				200, 500);
		return transactionGeneratorService.getStatus();
	}

	/**
	 * To access this resource directly: <br>
	 * (1)curl "http://anyclient:clientsecret@localhost:9999/uaa/oauth/token" -d "password=user_abc&username=user_abc&grant_type=password&scope=openid" <br>
	 * (2)curl -H "Authorization: Bearer [token]" -H "Content-Type: application/json" -d '{"cardNumber":"123456","paymentReason":"noreason","moneyAmount":2.0,"transactionDt":"2017-07-13 10:20:15"}' http://localhost:6000/transaction/authcode/xxxbank
	 *
	 * @param requestBank
	 * @return
	 */
    // Disclaimers: This is just spaghetti code to expose "RPC"
	// It doesn't follow RESTFul API design. I don't have time for it yet
	@PostMapping("/authcode/{requestBank}")
	@PreAuthorize("#oauth2.hasScope('openid') && hasAuthority('abc')")
	public TransactionAuthResponse generateAuthCode(@PathVariable String requestBank, @RequestBody CreditCardTransaction transaction) {
		return authCodeService.authorizeTransaction(requestBank, transaction);
	}

	/**
	 * For Microservice client
	 *
	 * @param transactionDate
	 * @return
	 */
    // Disclaimers: This is just spaghetti code to expose "RPC"
	// It doesn't follow RESTFul API design. I don't have time for it yet
	@PreAuthorize("#oauth2.hasScope('openid')")
	@Override
	public List<String> getBankIds(@PathVariable @DateTimeFormat(iso=DateTimeFormat.ISO.DATE) Date transactionDate) {
		Date from = new DateTime(transactionDate).withTimeAtStartOfDay().toDate();
		Date to = new DateTime(transactionDate).withTime(23, 59, 59, 999).toDate();
		return authCodeRepository.findAllRequestedBanks(from, to);
	}

	/**
	 * For Microservice client
	 *
	 * @param bankId
	 * @param transactionDate
	 * @param fromAuthCode
	 * @param limit
	 * @return
	 */
    // Disclaimers: This is just spaghetti code to expose "RPC"
	// It doesn't follow RESTFul API design. I don't have time for it yet
	@PreAuthorize("#oauth2.hasScope('openid')")
	@Override
	public List<CreditCardTransaction> getTransactionsByAuthCodes(@PathVariable String bankId,
																  @PathVariable
																  @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
																		  Date transactionDate,
																  @PathVariable int fromAuthCode,
																  @PathVariable int limit) {
		return authCodeService.getCreditTransactions(bankId, transactionDate, fromAuthCode, limit);
	}
}
