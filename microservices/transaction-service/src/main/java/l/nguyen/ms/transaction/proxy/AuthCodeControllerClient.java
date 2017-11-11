package l.nguyen.ms.transaction.proxy;

import java.util.List;

import com.netflix.hystrix.HystrixCommand;
import l.nguyen.ms.commons.controller.AuthCodeControllerApi;
import l.nguyen.ms.commons.model.GeneratedAuthCode;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import rx.Observable;

@FeignClient(name = "authcode-service", path = "/authcode")
public interface AuthCodeControllerClient extends AuthCodeControllerApi {
}
