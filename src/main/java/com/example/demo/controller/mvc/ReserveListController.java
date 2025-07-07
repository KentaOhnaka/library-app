package com.example.demo.controller.mvc;

import java.security.Principal;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.dto.ReservationDto;
import com.example.demo.service.ReserveService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor

public class ReserveListController {
	
	private final ReserveService reserveService;
	
	@GetMapping("/reservations/list")
	public String showReservations (Model model,
			Principal principal) {
		
		String userId=principal.getName();
		System.out.println(userId);
		List<ReservationDto> reservationList=reserveService.findReservationByUserId(userId);
		System.out.println("中身");
		System.out.println(reservationList);
		model.addAttribute("reservations", reservationList);
		return "reserveList";
	}

}
