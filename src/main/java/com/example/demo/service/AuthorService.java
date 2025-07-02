package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthorDetailDto;
import com.example.demo.dto.BookSearchResultDto;
import com.example.demo.entity.Author;
import com.example.demo.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthorService {
	private final AuthorRepository authorRepository;
	private final BookService bookService;

	public AuthorDetailDto findByAuthorWithBooks(String authorCode) {

		Author author = authorRepository.findById(authorCode)
				.orElseThrow(() -> new RuntimeException("該当する著者が見つかりません"));
		List<BookSearchResultDto> books = bookService.findByAuthorCode(authorCode);
		AuthorDetailDto authorDetailDto = convertAuthorToDetailDto(author);
		authorDetailDto.setBooks(books);
		
		return authorDetailDto;

	}

	public AuthorDetailDto convertAuthorToDetailDto(Author author) {
		AuthorDetailDto dto = new AuthorDetailDto();
		dto.setAuthorName(author.getAuthorName());
		dto.setAuthorCode(author.getAuthorCode());
		dto.setAuthorMail(author.getAuthorMail());
		dto.setAuthorHomepage(author.getAuthorHomepage());
		dto.setAuthorBelong(author.getAuthorBelong());

		return dto;
	}

}
