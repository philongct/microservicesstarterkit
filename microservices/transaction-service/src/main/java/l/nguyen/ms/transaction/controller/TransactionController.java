package l.nguyen.ms.transaction.controller;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import l.nguyen.ms.commons.model.CreditCardTransaction;
import l.nguyen.ms.commons.model.GeneratedAuthCode;
import l.nguyen.ms.transaction.proxy.AuthCodeControllerClient;
import l.nguyen.ms.transaction.proxy.AuthCodeControllerClientRx;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

	@Autowired
	private AuthCodeControllerClient authCodeClient;

//	@Autowired
//	private AuthCodeControllerClientRx authCodeClientRx;

	@GetMapping("/{merchantId}")
	public List<CreditCardTransaction> getAllTransactions(@PathVariable String merchantId) {
//		authCodeClientRx.getGeneratedAuthCodes("afdfsf", 100, 300).subscribe(a -> {
//			System.out.println(a);
//		});

		List<CreditCardTransaction> transactions = new ArrayList<>();
		int start = RandomUtils.nextInt(100);
		authCodeClient.getGeneratedAuthCodes(merchantId, start, start + 100).forEach(code -> {
			transactions.add(new CreditCardTransaction(merchantId, code.getCreditCardNumber(), "Buy something", 0.0f, code.getGeneratedDt()));
		});

		return transactions;
	}
}
