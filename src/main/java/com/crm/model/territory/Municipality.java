package com.crm.model.territory;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.crm.model.company.Headquarter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Municipality {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long idM;
	
	@Column
	private String name;
	
	@ManyToOne
	@JoinColumn(name="idP")
	private Province province;
	
	public Municipality(String name, Province province) {
		super();
		this.name = name;
		this.province = province;
	}
	
	
}
