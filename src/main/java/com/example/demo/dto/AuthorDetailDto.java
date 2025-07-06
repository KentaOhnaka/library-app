package com.example.demo.dto;

import java.util.List;

import lombok.Data;

@Data
public class AuthorDetailDto {
	
	private String authorCode;
	private String authorName;
	private String authorMail;
	private String authorHomepage;
	private String authorBelong;
	private List<BookSearchResultDto> books;
	

}
