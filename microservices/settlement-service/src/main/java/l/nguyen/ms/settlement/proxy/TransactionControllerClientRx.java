package l.nguyen.ms.settlement.proxy;

import java.util.Date;
import java.util.List;

import l.nguyen.ms.common.model.transaction.GeneratedAuthCode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rx.Observable;

@FeignClient(name = "transaction-service", path = "/transaction")
public interface TransactionControllerClientRx {

    @GetMapping(value = "/banks/{transactionDate}")
    public Observable<List<String>> getBankIds(@PathVariable("transactionDate")
                                               @DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
                                                       Date transactionDate);
}
