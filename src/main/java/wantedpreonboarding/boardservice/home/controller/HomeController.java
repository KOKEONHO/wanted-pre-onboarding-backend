package wantedpreonboarding.boardservice.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/")
	public String displayDocs() {
		return "redirect:/docs/index.html";
	}
}
