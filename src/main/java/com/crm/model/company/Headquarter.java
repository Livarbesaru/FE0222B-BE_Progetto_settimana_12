package com.crm.model.company;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.crm.model.territory.Municipality;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Headquarter {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String street;
	private int StreetNumber;
	private String locality;
	private String postalCode;
	
	@ManyToOne
	@JoinColumn(name="idM")
	private Municipality municipality;
	
	public Headquarter(String street, int streetNumber, String locality, String postalCode,
			Municipality municipality) {
		super();
		this.street = street;
		StreetNumber = streetNumber;
		this.locality = locality;
		this.postalCode = postalCode;
		this.municipality = municipality;
	}
	
	public Headquarter() {
	}
}
