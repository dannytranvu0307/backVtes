package com.vtes.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "tbl_refresh_token")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshToken {
	@Id
	@Column(name = "ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name = "ACCOUNT_ID")
	@ManyToOne
	private Account account;
	@Column(name="TOKEN")
	private String token;
	
	@Column(name ="CREATE_TIME")
	private Date createTimeDate;
	
	@Column(name ="UPDATE_TIME")
	private Date updateTimeDate;
	
}
