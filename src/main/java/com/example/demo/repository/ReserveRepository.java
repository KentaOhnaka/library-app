package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.entity.Reserve;

public interface ReserveRepository  extends JpaRepository<Reserve, Integer>{
	
	List<Reserve> findByUsers_UserIdOrderByReserveDateDesc(String userId);

}
