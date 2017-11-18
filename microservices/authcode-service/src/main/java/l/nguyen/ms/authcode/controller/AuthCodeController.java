package l.nguyen.ms.authcode.controller;

import java.util.ArrayList;
import java.util.List;

import l.nguyen.ms.commons.controller.AuthCodeControllerApi;
import l.nguyen.ms.commons.model.GeneratedAuthCode;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthCodeController implements AuthCodeControllerApi {

	private static final Logger logger = LoggerFactory.getLogger(AuthCodeController.class);

	/**
	 * To access this resource directly: <br>
	 * (1)curl "http://anyclient:clientsecret@localhost:9999/uaa/oauth/token" -d "password=user_abc&username=user_abc&grant_type=password&scope=openid" <br>
	 * (2)curl -H "Authorization: Bearer [token]" http://localhost:6000/merchantId
	 *
	 * @param merchantId
	 * @return
	 */
	@GetMapping("/{merchantId}")
	@PreAuthorize("#oauth2.hasScope('openid') && hasAuthority('abc')")
	public String generateAuthCode(@PathVariable String merchantId) {
		return RandomStringUtils.randomNumeric(6);
	}

	/**
	 * For microservice client
	 *
	 * @param merchantId
	 * @param from
	 * @param to
	 * @return
	 */
	@PreAuthorize("#oauth2.hasScope('openid')")
	@Override
	public List<GeneratedAuthCode> getGeneratedAuthCodes(@PathVariable String merchantId,
														 @PathVariable int from,
														 @PathVariable int to) {
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
