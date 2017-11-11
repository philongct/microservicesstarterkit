package l.nguyen.ms.transaction.proxy;

import java.util.List;

import l.nguyen.ms.commons.model.GeneratedAuthCode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rx.Observable;

@FeignClient(name = "authcode-service", path = "/authcode")
public interface AuthCodeControllerClientRx {
    @GetMapping(value = "/{merchantId}/{from}/{to}")
    Observable<List<GeneratedAuthCode>> getGeneratedAuthCodes(@PathVariable("merchantId") String merchantId,
                                                              @PathVariable("from") int from,
                                                              @PathVariable("to") int to);
}
