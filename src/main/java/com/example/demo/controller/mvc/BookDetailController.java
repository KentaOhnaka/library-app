package com.example.demo.controller.mvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.dto.BookDetailDto;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookDetailController {
	private final BookService bookService;
	
	@GetMapping("/{isbn}")
	public String showDetail(@PathVariable String isbn, Model model) {
		BookDetailDto bookDetail=bookService.findByIsbn(isbn);
		model.addAttribute("bookDetail",bookDetail);
		return "bookDetail";	
	}
}
