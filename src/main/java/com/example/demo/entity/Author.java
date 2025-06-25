package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity// JPA: このクラスがDBのテーブルに対応するエンティティであることを示す
@Table(name="AUTHOR")  // JPA: 対応するテーブル名を明示的に指定する

public class Author {

		@Id
		@Column(name="AUTHOR_CODE")
		private String authorCode;
		
		@Column(name="AUTHOR_NAME")
		private String authorName;

		@Column(name="AUTHOR_MAIL")
		private String authorMail;
		
		@Column(name="AUTHOR_HOMEPAGE")
		private String authorHomepage;
		
		@Column(name="AUTHOR_BELONG")
		private String authorBelong;
		
	

}
