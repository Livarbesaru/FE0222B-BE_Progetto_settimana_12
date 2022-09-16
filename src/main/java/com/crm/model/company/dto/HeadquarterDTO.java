package com.crm.model.company.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HeadquarterDTO {
	private Long id;
	private String street;
	private int streetNumber;
	private String locality;
	private String postalCode;
	private Long municipality;

	public HeadquarterDTO(String street, int streetNumber, String locality, String postalCode,
			Long municipality) {
		super();
		this.street = street;
		this.streetNumber = streetNumber;
		this.locality = locality;
		this.postalCode = postalCode;
		this.municipality=municipality;
	}
	public HeadquarterDTO(Long id, String street, int streetNumber, String locality, String postalCode,
			Long municipality) {
		super();
		this.id = id;
		this.street = street;
		this.streetNumber = streetNumber;
		this.locality = locality;
		this.postalCode = postalCode;
		this.municipality = municipality;
	}
}
