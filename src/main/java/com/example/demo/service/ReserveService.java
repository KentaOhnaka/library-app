package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.dto.ReservationDto;
import com.example.demo.dto.ReserveDetailDto;
import com.example.demo.entity.Book;
import com.example.demo.entity.Library;
import com.example.demo.entity.Reserve;
import com.example.demo.entity.Users;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.LibraryRepository;
import com.example.demo.repository.ReserveRepository;
import com.example.demo.repository.UsersRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ReserveService {

	private final ReserveRepository reserveRepository;
	private final UsersRepository usersRepository;
	private final BookRepository bookRepository;
	private final LibraryRepository libraryRepository;

	@Transactional(readOnly = true)
	public List<ReservationDto> findReservationByUserId(String userId) {
		List<Reserve> reserves = reserveRepository.findByUsers_UserIdOrderByReserveDateDesc(userId);
		return reserves.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
	}

	private ReservationDto convertToDto(Reserve reserve) {
		ReservationDto dto = new ReservationDto();
		dto.setBookName(reserve.getBook().getBookName());
		dto.setAuthorName(reserve.getBook().getAuthor().getAuthorName());
		dto.setLibraryName(reserve.getLibrary().getLibraryName());
		dto.setReserveDate(reserve.getReserveDate());
		dto.setReserveId(reserve.getReserveId());
		dto.setIsbn(reserve.getBook().getIsbn());

		dto.setAuthorCode(reserve.getBook().getAuthor().getAuthorCode());

		return dto;
	}

	@Transactional
	public void createReservation(String userId, String isbn, String libraryCode, String reserveMemo) {

		Users user = usersRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("利用者が見つかりません。: " + userId));

		Book book = bookRepository.findById(isbn)
				.orElseThrow(() -> new RuntimeException("書籍が見つかりません。" + isbn));

		Library library = libraryRepository.findById(libraryCode)
				.orElseThrow(() -> new RuntimeException("図書館が見つかりません。。" + libraryCode));

		Reserve newReserve = new Reserve();

		newReserve.setUsers(user);
		newReserve.setBook(book);
		newReserve.setLibrary(library);

		newReserve.setReserveDate(LocalDateTime.now());
		newReserve.setReserveMemo(reserveMemo);

		reserveRepository.save(newReserve);

	}

	@Transactional(readOnly = true)
	public ReserveDetailDto findById(Integer reserveId) {
		Reserve reserve = reserveRepository.findById(reserveId)
				.orElseThrow(() -> new RuntimeException("該当する予約が見つかりません."));
		return convertToDetailDto(reserve);
	}

	private ReserveDetailDto convertToDetailDto(Reserve reserve) {
		ReserveDetailDto dto = new ReserveDetailDto();

		dto.setReserveId(reserve.getReserveId());
		dto.setBookName(reserve.getBook().getBookName());
		dto.setAuthorName(reserve.getBook().getAuthor().getAuthorName());
		dto.setLibraryName(reserve.getLibrary().getLibraryName());
		dto.setLibraryAddress(reserve.getLibrary().getLibraryAddress());
		dto.setReserveDate(reserve.getReserveDate());
		dto.setReserveMemo(reserve.getReserveMemo());
		return dto;
	}
}
