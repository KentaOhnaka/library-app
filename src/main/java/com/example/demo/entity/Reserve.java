package com.example.demo.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="RESERVE")
public class Reserve {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="RESERVE_ID")
	private Integer reserveId;
	
	@Column(name="RESERVE_DATE")
	private LocalDateTime reserveDate;
	
	@Column(name="RESERVE_MEMO")
	private String reserveMemo;
	
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="USER_ID",nullable=false)
	private Users users;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="ISBN",nullable=false)
	private Book book;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="LIBRARY_CODE",nullable=false)
	private Library library;

}
