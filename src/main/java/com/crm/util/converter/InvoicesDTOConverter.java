package com.crm.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.crm.model.company.dto.InvoiceDTO;
import com.crm.model.company.invoices.Invoice;
import com.crm.repository.company.CustomerRepository;

@Component
public class InvoicesDTOConverter implements Converter<InvoiceDTO, Invoice>{
	
	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	InvoiceStatusFactory invoiceConverter;
	
	@Override
	public Invoice convert(InvoiceDTO source) {
		Invoice invoice=new Invoice(
				customerRepository.findById(source.getCustomer()).get(),
				source.getNumber(),
				source.getYear(),
				source.getDate(),
				source.getAmount(),
				invoiceConverter.convert(source.getStatus()));
		invoice.setId(source.getId());
		return invoice;
	}

}
