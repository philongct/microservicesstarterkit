package l.nguyen.ms.settlement.proxy;

import l.nguyen.ms.common.controller.TransactionControllerApi;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "transaction-service", path = "/transaction")
public interface TransactionControllerClient extends TransactionControllerApi {
}
