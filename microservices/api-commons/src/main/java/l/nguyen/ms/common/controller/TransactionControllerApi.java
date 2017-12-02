package l.nguyen.ms.common.controller;

import java.util.Date;
import java.util.List;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.common.model.transaction.GeneratedAuthCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface TransactionControllerApi {

	@GetMapping(value = "/authcode/{requestBank}/{fromAuthCode}/{toAuthCode}")
	List<GeneratedAuthCode> getGeneratedAuthCodes(@PathVariable("requestBank") String requestBank,
												 @PathVariable("fromAuthCode") int fromAuthCode,
												 @PathVariable("toAuthCode") int toAuthCode);

	@GetMapping(value = "/credits/{requestBank}/{transactionDate}/{fromAuthCode}/{toAuthCode}")
	public List<CreditCardTransaction> getTransactionsByAuthCodes(@PathVariable("requestBank") String requestBank,
																  @PathVariable("transactionDate") Date transactionDate,
																  @PathVariable("fromAuthCode") int fromAuthCode,
																  @PathVariable("toAuthCode") int toAuthCode);
}
