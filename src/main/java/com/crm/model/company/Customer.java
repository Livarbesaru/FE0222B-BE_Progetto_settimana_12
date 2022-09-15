package com.crm.model.company;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;

import com.crm.util.converter.CustomerTypeFactory;
import com.crm.util.exception.CRMException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Customer {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Min(value = 6)
	private String companyName;
	
	@Min(value = 11)
	@Column
	private Long vatNumber;
	
	@Email
	@Column
	private String email;
	
	private Date dateFirstContact;
	private Date dateLastContact;
	private Float annualRevenue;
	
	@Email
	@Column
	private String pec;
	
	@Column
	private String phone;
	
	@Email
	@Column
	private String emailContact;
	
	private String nameContact;
	private String surnameContact;
	
	@Column
	private String phoneContact;
	
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private Headquarter operationalHeadquarter;
	
	@ManyToOne(cascade = {CascadeType.PERSIST,CascadeType.MERGE})
	private Headquarter registeredHeadquarter;
	
	@Enumerated(EnumType.STRING)
	private CustomerType type;
	
	public Customer(@Min(value = 6) String companyName,@Min(value = 11) Long vatNumber,@Email String email, Date dateFirstContact, Date dateLastContact,
			Float annualRevenue,@Email String pec, String phone,@Email String emailContact, String nameContact, String surnameContact,
			String phoneContact,CustomerType type,Headquarter registeredHeadquarter,Headquarter operationalHeadquarter) {
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
		this.registeredHeadquarter=registeredHeadquarter;
		this.operationalHeadquarter=operationalHeadquarter;
	}
	
	public void setValues(Customer customer) {
		this.companyName = customer.companyName;
		this.vatNumber = customer.vatNumber;
		this.email = customer.email;
		this.dateFirstContact = customer.dateFirstContact;
		this.dateLastContact = customer.dateLastContact;
		this.annualRevenue = customer.annualRevenue;
		this.pec = customer.pec;
		this.phone = customer.phone;
		this.emailContact = customer.emailContact;
		this.nameContact = customer.nameContact;
		this.surnameContact = customer.surnameContact;
		this.phoneContact = customer.phoneContact;
		this.type=customer.type;
		this.registeredHeadquarter=customer.registeredHeadquarter;
		this.operationalHeadquarter=customer.operationalHeadquarter;
	}
	
	public boolean compareVariables(String variable,String value1,String value2,CustomerTypeFactory converterType){
		switch (variable.toLowerCase()) {
		case "annualrevenue":
			return (this.annualRevenue >= Float.parseFloat(value1)
			&& this.annualRevenue <= Float.parseFloat(value2));
		case "datefirstcontact":
			return (this.dateFirstContact.getTime() >= Date.valueOf(value1).getTime()
			&& this.dateFirstContact.getTime() <= Date.valueOf(value2).getTime());
		case "datelastcontact":
			return (this.dateLastContact.getTime() >= Date.valueOf(value1).getTime()
			&& this.dateLastContact.getTime() <= Date.valueOf(value2).getTime());
		case "namecontact":
			return (this.nameContact.toLowerCase().contains(value1.toLowerCase()));
		case "companyName":
			return (this.companyName.toLowerCase().contains(value1.toLowerCase()));
		case "surnamecontact":
			return (this.surnameContact.toLowerCase().contains(value1.toLowerCase()));
		case "nameandsurname":
			return (this.surnameContact.toLowerCase().contains(value2.toLowerCase()) 
					&& this.nameContact.toLowerCase().contains(value1.toLowerCase()));
		case "type":
			return (this.type.toString().equals(converterType.convert(value1).toString()));
		default:
			throw new CRMException("the variable you are searching is not present in this class or the values are wrong");
		}
	}
	
	
}
