package com.example.demo.entity;

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
@Table(name = "LIBRARY")
public class Library {

	@Id
	@Column(name = "LIBRARY_CODE")
	private String libraryCode;
	@Column(name = "LIBRARY_NAME")
	private String libraryName;

	@Column(name = "LIBRARY_TEL")
	private String libraryTel;

	@Column(name = "LIBRARY_FAX")
	private String libraryFax;

	@Column(name = "LIBRARY_ADDRESS")
	private String libraryAddress;

	@Column(name = "OPENING_HOURS")
	private String openingHours;

	// 図書館(多) 対 区画(一) の関係
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "WARD_CODE")
	private Ward ward; // ※Wardエンティティも必要になる

}
