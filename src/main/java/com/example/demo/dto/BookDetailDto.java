package com.example.demo.dto;

import java.time.LocalDate;

import lombok.Data;
@Data
public class BookDetailDto {
	
	private String isbn;
	private String bookName;
	private String authorName;
	private String publishCompany;
	private LocalDate publishDate;
	private String bookSummary;
	private String authorCode;

}
