package com.vtes.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;

@Entity
@Table(name = "tbl_commuter_pass")
@Data
public class CommuterPass {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;
	
	@OneToOne
	@JoinColumn(name = "USER_ID",referencedColumnName = "ID")
	private User user;
	
	@Column(name = "DEPARTURE")
	private String departure;
	
	@Column(name = "DESTINATION")
	private String destination;
	
	@Column(name = "VIA")
	private String via;
	
	@Column(name = "VIA_DETAIL")
	private String viaDetail;
	
	@Column(name = "CREATE_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createDt;
	
	@Column(name="UPDATE_DT")
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateDt;
	
	@Column(name = "DELETE_FLAG")
	private Boolean deleteFlag;
}
