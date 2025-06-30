package com.example.demo.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.AuthorDetailDto;
import com.example.demo.service.AuthorService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/author")
@RequiredArgsConstructor
public class AuthorDetailController {
	
	private final AuthorService authorService;
	
	@GetMapping("/{authorCode}")
	public String showDetail (@PathVariable String authorCode, Model model) {
		AuthorDetailDto dto=authorService.findByAuthorCode(authorCode);
		model.addAttribute("author",dto);
		return "authorDetail";
	}

}
