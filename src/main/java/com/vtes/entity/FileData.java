package com.vtes.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Entity
@Table(name = "tbl_exported_file")
@Data
public class FileData {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "USER_ID",referencedColumnName = "ID")
	private User user;
	
	@Column(name = "FILE_NAME")
	private String fileName;
	
	@Column(name = "FILE_CODE")
	private String fileCode;
	
	@Column(name = "EXPORTED_DT")
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "yyyy/MM/dd")
	private Date exportedDate;
	
	@Column(name = "DELETE_FLAG")
	private boolean deleteFlag;
}
