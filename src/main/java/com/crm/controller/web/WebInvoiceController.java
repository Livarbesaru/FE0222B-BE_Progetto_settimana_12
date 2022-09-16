package com.crm.controller.web;

import java.sql.Date;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.crm.model.company.dto.InvoiceDTO;
import com.crm.model.company.invoices.InvoicesStatus;
import com.crm.repository.company.InvoiceRepository;
import com.crm.service.company.CustomerService;
import com.crm.service.company.InvoiceService;

@Controller
@RequestMapping("/web/invoice")
public class WebInvoiceController {
	
	@Autowired
	InvoiceService invoiceService;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/formadd")
	public ModelAndView formadd() {
		ModelAndView model=new ModelAndView("invoice//addforminvoice");
		model.addObject("customers",customerService.findAll().stream()
				.sorted((c1,c2)->c1.getNameContact().compareTo(c2.getNameContact())).collect(Collectors.toList()));
		model.addObject("invoice",new InvoiceDTO());
		model.addObject("status", InvoicesStatus.values());
		return model;
	}
	
	@PostMapping("/add")
	public String formadd(@ModelAttribute("invoice") InvoiceDTO invoice) {
		invoice.setYear((new java.util.Date().getYear())+1900);
		invoice.setDate(new Date(new java.util.Date().getTime()));
		invoiceService.addInvoice(invoice);
		return "redirect:/web/invoice/list?size=20&page=0";
	}
	
	@GetMapping("/formedit")
	public ModelAndView formedit() {
		ModelAndView model=new ModelAndView("invoice//editforminvoice");
		model.addObject("customers",customerService.findAll().stream()
				.sorted((c1,c2)->c1.getNameContact().compareTo(c2.getNameContact())).collect(Collectors.toList()));
		model.addObject("invoice",new InvoiceDTO());
		model.addObject("status", InvoicesStatus.values());
		return model;
	}
	
	@GetMapping("/list")
	public ModelAndView formadd(@RequestParam int page,@RequestParam int size) {
		ModelAndView model=new ModelAndView("invoice//invoicelist");
		model.addObject("list",invoiceService.getAll(PageRequest.of(page, size)));
		return model;
	}

}
