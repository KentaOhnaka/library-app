package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="USERS")
public class Users {
	@Id
	@Column(name="USER_ID")
	private String userId;
	
	@Column(name="USER_PASSWORD")
	private String userPassword;
	
	@Column(name="USER_NAME")
	private String userName;
	
	@Column(name="USER_ADMIN_FLG")
	private boolean userAdminFlg;

}
	