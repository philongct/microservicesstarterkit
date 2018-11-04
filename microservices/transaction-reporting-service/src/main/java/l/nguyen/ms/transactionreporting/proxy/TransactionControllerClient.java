package l.nguyen.ms.transactionreporting.proxy;

import org.springframework.cloud.netflix.feign.FeignClient;

import l.nguyen.ms.common.controller.TransactionControllerApi;

@FeignClient(name = "transaction-service", path = "/transaction")
public interface TransactionControllerClient extends TransactionControllerApi {
}
