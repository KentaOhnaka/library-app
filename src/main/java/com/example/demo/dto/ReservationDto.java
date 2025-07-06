package com.example.demo.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data

public class ReservationDto {
	private String bookName;
	private String authorName;
	private String libraryName;
	private LocalDateTime reserveDate;

}
