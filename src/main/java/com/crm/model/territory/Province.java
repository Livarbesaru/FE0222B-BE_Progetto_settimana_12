package com.crm.model.territory;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Province {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int idP;
	private String region;
	private String name;
	private String abbreviation;
	
	public Province(String name, String abbreviation,String region) {
		super();
		this.name = name;
		this.abbreviation = abbreviation;
		this.region=region;
	}
}
