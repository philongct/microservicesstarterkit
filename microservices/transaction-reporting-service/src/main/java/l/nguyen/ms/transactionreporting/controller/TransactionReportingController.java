package l.nguyen.ms.transactionreporting.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import l.nguyen.ms.transactionreporting.service.TransactionReportingService;

@RestController
public class TransactionReportingController {

	@Autowired
	private TransactionReportingService transactionReportingService;

	@GetMapping("/export")
	public String exportAllTransactions() {
		return transactionReportingService.startReporting();
	}
}
