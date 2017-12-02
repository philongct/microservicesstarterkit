package l.nguyen.ms.transaction.proxy;

import java.util.List;

import l.nguyen.ms.common.model.transaction.GeneratedAuthCode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rx.Observable;

@FeignClient(name = "transaction-service", path = "/transaction")
public interface TransactionControllerClientRx {

    @GetMapping(value = "/authcode/{requestBank}/{fromAuthCode}/{toAuthCode}")
    Observable<List<GeneratedAuthCode>> getGeneratedAuthCodes(@PathVariable("requestBank") String requestBank,
                                                              @PathVariable("fromAuthCode") int fromAuthCode,
                                                              @PathVariable("toAuthCode") int toAuthCode);
}
