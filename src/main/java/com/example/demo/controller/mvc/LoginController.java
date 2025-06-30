package com.example.demo.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

	@GetMapping("/login")
	public String showLoginForm() {
//        System.out.println("LoginController: /login の門番が呼び出されました！");
		return "login";
	}
}
