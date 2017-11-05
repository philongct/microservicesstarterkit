package l.nguyen.security.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@RequestMapping("/api/hello")
	public Authentication hello() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
