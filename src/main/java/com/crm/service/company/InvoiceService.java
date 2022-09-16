package com.crm.service.company;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.crm.model.company.dto.InvoiceDTO;
import com.crm.model.company.invoices.Invoice;
import com.crm.repository.company.InvoiceRepository;
import com.crm.service.InvoiceStatusFactory;
import com.crm.util.converter.InvoicesDTOConverter;
import com.crm.util.exception.InvoiceException;

@Service
public class InvoiceService {
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	InvoicesDTOConverter invoicesDTOConverter;
	
	@Autowired
	InvoiceStatusFactory invoiceStatusConverter;
	
	public Invoice addInvoice(InvoiceDTO invoiceDTO) {
		Invoice invoice=invoicesDTOConverter.convert(invoiceDTO);
		invoiceRepository.save(invoice);
		return invoice;
	}
	
	public Invoice editInvoice(InvoiceDTO invoiceDTO) {
		Optional<Invoice> invoice=invoiceRepository.findById(invoiceDTO.getId());
		if(invoice.isPresent() && invoice.get().getCustomer().getId().longValue() == invoiceDTO.getCustomer().longValue()) {
			invoice.get().setValues(invoicesDTOConverter.convert(invoiceDTO));
			invoiceRepository.save(invoice.get());
			return invoice.get();
		}
		throw new InvoiceException("The customer can't be changed or the invoice's id you passed does not exist");
	}
	
	public String deleteInvoice(Long id) {
		if(invoiceRepository.findById(id).isPresent()) {
			invoiceRepository.deleteById(id);
			return "invoice has been removed";
		}
		throw new InvoiceException("does not exist an invoice with id: "+id);
	}
	
	public Page<Invoice> getAll(Pageable pageable) {
		return invoiceRepository.findAll(pageable);
	}
	
	public List<Invoice> findAll() {
		return invoiceRepository.findAll();
	}
	
	public List<Invoice> filterAll(int page, int size, String variable,String value1,String value2, String sort) {
		if(sort.toLowerCase().equals("asc")) {
			List<Invoice> invoices=invoiceRepository.findAll(PageRequest.of(page,size, Sort.by(variable).ascending())).stream()
					.filter(x->x.compareVariables(variable, value1, value2,invoiceStatusConverter)).toList();
			return invoices;
		}else {
			List<Invoice> invoices=invoiceRepository.findAll(PageRequest.of(page,size, Sort.by(variable).descending())).stream()
					.filter(x->x.compareVariables(variable, value1, value2,invoiceStatusConverter)).toList();
			return invoices;
		}
	}

}
