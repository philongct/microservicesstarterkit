package l.nguyen.ms.commons.controller;

import java.util.ArrayList;
import java.util.List;

import l.nguyen.ms.commons.model.GeneratedAuthCode;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface AuthCodeControllerApi {

	@GetMapping(value = "/{merchantId}/{from}/{to}")
	List<GeneratedAuthCode> getGeneratedAuthCodes(@PathVariable("merchantId") String merchantId,
												 @PathVariable("from") int from,
												 @PathVariable("to") int to);
}
