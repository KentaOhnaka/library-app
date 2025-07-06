package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.example.demo.dto.ReservationDto;
import com.example.demo.entity.Reserve;
import com.example.demo.repository.ReserveRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReserveService {
	
	private final ReserveRepository reserveRepository;
	
	public List<ReservationDto> findReservationByUserId(String userId){
		List<Reserve> reserves=reserveRepository.findByUsers_UserIdOrderByReserveDateDesc(userId);
		return reserves.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}
	
	private ReservationDto convertToDto(Reserve reserve) {
		ReservationDto dto =new ReservationDto();
		dto.setBookName(reserve.getBook().getBookName());
		dto.setAuthorName(reserve.getBook().getAuthor().getAuthorName());
		dto.setLibraryName(reserve.getLibrary().getLibraryName());
		dto.setReserveDate(reserve.getReserveDate());
		return dto;
	}

}
