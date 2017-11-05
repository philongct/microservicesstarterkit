package l.nguyen.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping("/api/hello")
	@PreAuthorize("hasAuthority('abc')")
	public Authentication hello() {
		return SecurityContextHolder.getContext().getAuthentication();
	}

}
