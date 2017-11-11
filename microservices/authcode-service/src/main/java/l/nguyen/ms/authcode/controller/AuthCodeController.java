package l.nguyen.ms.authcode.controller;

import java.util.ArrayList;
import java.util.List;

import l.nguyen.ms.authcode.model.GeneratedAuthCode;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthCodeController {

	private static final Logger logger = LoggerFactory.getLogger(AuthCodeController.class);
	
	@GetMapping("/{merchantId}/{from}/{to}")
	public List<GeneratedAuthCode> getGeneratedAuthCode(@PathVariable String merchantId,
														@PathVariable int from, @PathVariable int to) {
		List<GeneratedAuthCode> ret = new ArrayList<>();

		for (int i = from; i <= to; ++i) {
			ret.add(new GeneratedAuthCode(merchantId, i, RandomStringUtils.randomNumeric(16)));
		}

		// simulate 30% not found authcodes
		int ran = RandomUtils.nextInt((to - from)/3);
		for (int i = ran; i > 0; --i) {
			ret.remove(RandomUtils.nextInt(ret.size()));
		}

		return ret;
	}
}
