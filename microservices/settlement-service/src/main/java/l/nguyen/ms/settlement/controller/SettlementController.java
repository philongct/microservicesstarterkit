package l.nguyen.ms.settlement.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import l.nguyen.ms.common.model.transaction.CreditCardTransaction;
import l.nguyen.ms.settlement.service.SettlementService;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SettlementController {

	@Autowired
	private SettlementService settlementService;

	@GetMapping("/export")
	public String exportAllTransactions() {
		return settlementService.startSettlement();
	}
}
