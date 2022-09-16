package com.crm.service;


import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.crm.model.company.invoices.InvoicesStatus;

@Component
public class InvoiceStatusFactory implements Converter<String, InvoicesStatus>{

	@Override
	public InvoicesStatus convert(String value) {
		switch (value.toLowerCase()) {
		case "paid":
			return InvoicesStatus.Invoice_PAID;
		case "notpaid":
			return InvoicesStatus.Invoice_NOTPAID;
		default:
			return InvoicesStatus.Invoice_NOTPAID;
		}
	}

}
