package com.vtes.entity;

import java.time.Instant;
import javax.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Long id;

	@Column(name = "`FULL_NAME`")
	private String fullName;

	@Column(name = "`EMAIL`")
	private String email;

	@Column(name = "`PASSWORD`")
	private String password;

	@ManyToOne
	@JoinColumn(name = "DEPARTMENT_ID")
	private Department department;

	@Column(name = "`STATUS`")
	private Short status;

	@Column(name = "`VERIFY_CODE`")
	private String verifyCode;

	@Column(name = "`CREATE_DT`")
	private Instant createDt;

	@Column(name = "`UPDATE_DT`")
	private Instant updateDt;

	public User(String fullName, String email, String password, Department department) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.department = department;
	}

}
