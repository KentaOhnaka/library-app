package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReserveDetailDto {

	private Integer reserveId;
	private String bookName;
	private String authorName;
	private String libraryName;
	private String libraryAddress; // 受取館の住所も表示しよう
	private LocalDateTime reserveDate;
	private String reserveMemo;

}
