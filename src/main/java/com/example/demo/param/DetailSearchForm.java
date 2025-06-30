package com.example.demo.param;

import java.time.LocalDate;

import lombok.Data;

@Data
public class DetailSearchForm {
	
	private String bookName;
	
	private String authorName;
	
	private String publishCompany;
	
	private LocalDate publicDateForm;

}
