package com.crm.model.company.invoices;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.crm.model.company.Customer;
import com.crm.service.InvoiceStatusFactory;
import com.crm.util.exception.CRMException;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Invoice {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private long number;
	private int year;
	private Date date;
	private float amount;
	
	@Enumerated(EnumType.STRING)
	private InvoicesStatus status;
	
	@ManyToOne
	private Customer customer;
	
	public Invoice(Customer customer, long number, int year, Date date, float amount, InvoicesStatus status) {
		super();
		this.customer = customer;
		this.number = number;
		this.year = year;
		this.date = date;
		this.amount = amount;
		this.status = status;
	}
	
	public void setValues(Invoice invoice) {
		this.number = invoice.number;
		this.year = invoice.year;
		this.date = invoice.date;
		this.amount = invoice.amount;
		this.status = invoice.status;
	}
	
	public boolean compareVariables(String variable,String value1,String value2,InvoiceStatusFactory invoiceStatusConverter){
		switch (variable.toLowerCase()) {
		case "customer":
			return (this.customer.getId().longValue() >= Long.parseLong(value1)
			&& this.customer.getId().longValue() <=Long.parseLong(value2));
		case "date":
			return (this.date.getTime() >= Date.valueOf(value1).getTime()
			&& this.date.getTime() <= Date.valueOf(value2).getTime());
		case "year":
			return (this.year >= Integer.parseInt(value1)
			&& this.year <= Integer.parseInt(value2));
		case "number":
			return (this.number >= Long.parseLong(value1)
			&& this.number <= Long.parseLong(value2));
		case "amount":
			return (this.amount >= Float.parseFloat(value1)
			&& this.amount <= Float.parseFloat(value2));
		case "status":
			return (this.status.toString().equals(invoiceStatusConverter.convert(value1).toString()));
		default:
			throw new CRMException("the variable you are searching is not present in this class or the values are wrong");
		}
	}
	
	
}
