package com.crm.controller.company;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.crm.model.company.dto.InvoiceDTO;
import com.crm.model.company.invoices.Invoice;
import com.crm.service.company.InvoiceService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;


@RestController
@RequestMapping("/invoice")
public class InvoiceController {
	
	@Autowired
	InvoiceService invoiceService;
	
	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To add an invoice do not specify an id or it will throw an error")
	public ResponseEntity<Invoice> addInvoice(@RequestBody InvoiceDTO invoiceDTO){
		return new ResponseEntity<Invoice>(invoiceService.addInvoice(invoiceDTO),HttpStatus.OK);
	}
	
	@PutMapping("/edit")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To edit an invoice do specify an existing id or it will throw an error")
	public ResponseEntity<Invoice> editInvoice(@RequestBody InvoiceDTO invoiceDTO){
		return new ResponseEntity<Invoice>(invoiceService.editInvoice(invoiceDTO),HttpStatus.OK);
	}
	
	@DeleteMapping("/edit")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To delete an invoice do specify an existing id or it will throw an error")
	public ResponseEntity<String> deleteInvoice(@RequestParam Long id){
		return new ResponseEntity<String>(invoiceService.deleteInvoice(id),HttpStatus.OK);
	}
	
	@GetMapping("/get")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To get all Invoices")
	public ResponseEntity<Page<Invoice>> getInvoices(Pageable pageable){
		return new ResponseEntity<Page<Invoice>>(invoiceService.getAll(pageable),HttpStatus.OK);
	}
	
	@GetMapping("/filter")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To filter all invoices in a range based on their fields, "
			+ "except nameContanct and CustomerType, the 'to' param must be declareted to work")
	public ResponseEntity<List<Invoice>> filterInvoices(
			@RequestParam int page,@RequestParam int size,
			@RequestParam String variable, @RequestParam String from,
			@RequestParam String to, @RequestParam String sort){
		return new ResponseEntity<List<Invoice>>(invoiceService.filterAll(page, size, variable, from, to, sort),HttpStatus.OK);
	}
}
