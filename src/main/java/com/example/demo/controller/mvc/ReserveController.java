package com.example.demo.controller.mvc;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.ReserveService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReserveController {

	private final ReserveService reserveService;;

	@PostMapping("")
	public String createReservation(
			@RequestParam String isbn,
			@RequestParam String libraryCode,
			@RequestParam String reserveMemo,
			Principal principal) {
		String userId = principal.getName();

		reserveService.createReservation(userId, isbn, libraryCode, reserveMemo);

		return "redirect:/reservations/list";
	}
}
