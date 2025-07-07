package com.example.demo.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.ReserveDetailDto;
import com.example.demo.service.ReserveService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/reserveList")
@RequiredArgsConstructor
public class ReserveDetailController {
	
	private final ReserveService reserveService;
	
	@GetMapping("/{id}")
	public String showDetail(@PathVariable Integer id, Model model) {
		ReserveDetailDto reserveDetail=reserveService.findById(id);
		model.addAttribute("reserve", reserveDetail);
		return "reserveDetail";
	}

}
