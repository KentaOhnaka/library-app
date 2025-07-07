package com.example.demo.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "BOOK")
public class Book {

	@Id
	@Column(name = "ISBN")
	private String isbn;

	@Column(name = "BOOK_NAME")
	private String bookName;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "AUTHOR_CODE") // 封印を解き放ち、シンプルに！
	private Author author;

//	@Column(name = "AUTHOR_CODE")
//	private String authorCode;

	@Column(name = "PUBLISH_DATE")
	private LocalDate publishDate;

	@Column(name = "PUBLISH_COMPANY")
	private String publishCompany;

	@Column(name = "BOOK_SUMMARY")
	private String bookSummary;

}
