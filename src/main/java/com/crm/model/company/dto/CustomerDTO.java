package com.crm.model.company.dto;

import java.sql.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {
	private Long id;
	@Min(value = 6) @NotNull private String companyName;
	@Min(value = 11) @NotNull private Long vatNumber;
	@Email @NotNull private String email;
	private Date dateFirstContact;
	private Date dateLastContact;
	@NotNull private Float annualRevenue;
	@Email @NotNull private String pec;
	@NotNull private String phone;
	@Email @NotNull private String emailContact;
	@NotNull private String nameContact;
	@NotNull private String surnameContact;
	@NotNull private String phoneContact;
	@NotNull private HeadquarterDTO operationalHeadquarter=new HeadquarterDTO();
	@NotNull private HeadquarterDTO registeredHeadquarter=new HeadquarterDTO();
	@NotNull private String type;
	
	public CustomerDTO() {}
	
	public CustomerDTO(String companyName, Long vatNumber, String email, Date dateFirstContact, Date dateLastContact,
			Float annualRevenue, String pec, String phone, String emailContact, String nameContact, String surnameContact,
			String phoneContact,String type,HeadquarterDTO operationalHeadquarter, HeadquarterDTO registredHeadquarter) {
		super();
		this.companyName = companyName;
		this.vatNumber = vatNumber;
		this.email = email;
		this.dateFirstContact = dateFirstContact;
		this.dateLastContact = dateLastContact;
		this.annualRevenue = annualRevenue;
		this.pec = pec;
		this.phone = phone;
		this.emailContact = emailContact;
		this.nameContact = nameContact;
		this.surnameContact = surnameContact;
		this.phoneContact = phoneContact;
		this.type=type;
		this.registeredHeadquarter=registredHeadquarter;
		this.operationalHeadquarter=operationalHeadquarter;
	}

	public CustomerDTO(Long id, @Min(6) String companyName, @Min(11) Long vatNumber, @Email String email,
			Date dateFirstContact, Date dateLastContact, Float annualRevenue, @Email String pec, String phone,
			@Email String emailContact, String nameContact, String surnameContact, String phoneContact,
			HeadquarterDTO operationalHeadquarter, HeadquarterDTO registredHeadquarter, String type) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.vatNumber = vatNumber;
		this.email = email;
		this.dateFirstContact = dateFirstContact;
		this.dateLastContact = dateLastContact;
		this.annualRevenue = annualRevenue;
		this.pec = pec;
		this.phone = phone;
		this.emailContact = emailContact;
		this.nameContact = nameContact;
		this.surnameContact = surnameContact;
		this.phoneContact = phoneContact;
		this.operationalHeadquarter = operationalHeadquarter;
		this.registeredHeadquarter = registredHeadquarter;
		this.type = type;
	}
	
	
}
