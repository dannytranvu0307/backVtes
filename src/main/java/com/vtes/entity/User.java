package com.vtes.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tbl_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@Column(name ="ID")
	@GeneratedValue(strategy =GenerationType.IDENTITY)
	private Integer id;
	
	@OneToOne(mappedBy = "user")
	private CommuterPass commuterPass;
	
	@OneToMany(mappedBy = "user")
	private Set<Fare> fares;

	public User(Integer id) {
		super();
		this.id = id;
	}
	
	
	
}
