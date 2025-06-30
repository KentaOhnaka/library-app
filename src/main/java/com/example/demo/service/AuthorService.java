package com.example.demo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.dto.AuthorDetailDto;
import com.example.demo.entity.Author;
import com.example.demo.repository.AuthorRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor

public class AuthorService {
	private final AuthorRepository authorRepository;
	
	public AuthorDetailDto findByAuthorCode(String authorCode) {
		Optional<Author> authorOptional=authorRepository.findByAuthorCode(authorCode);
		Author author=authorOptional.orElseThrow(() -> new RuntimeException("該当する著者が見つかりません。"));
		return convertToDetailDto(author);
		
	}

	public AuthorDetailDto convertToDetailDto(Author author) {
		AuthorDetailDto dto=new AuthorDetailDto();
		dto.setAuthorName(author.getAuthorName());
		dto.setAuthorCode(author.getAuthorCode());
		dto.setAuthorMail(author.getAuthorMail());
		dto.setAuthorHomepage(author.getAuthorHomepage());
		dto.setAuthorBelong(author.getAuthorBelong());
		
		return dto;
	}
}
