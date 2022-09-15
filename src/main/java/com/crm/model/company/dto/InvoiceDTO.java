package com.crm.model.company.dto;

import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class InvoiceDTO {
	private Long id;
	private long number;
	private int year;
	private Date date;
	private float amount;
	private String status;
	private Long customer;
	
	public InvoiceDTO(long number, int year, Date date, float amount, String status, Long customer) {
		super();
		this.number = number;
		this.year = year;
		this.date = date;
		this.amount = amount;
		this.status = status;
		this.customer = customer;
	}

	public InvoiceDTO(Long id, long number, int year, Date date, float amount, String status, Long customer) {
		super();
		this.id = id;
		this.number = number;
		this.year = year;
		this.date = date;
		this.amount = amount;
		this.status = status;
		this.customer = customer;
	}
}
