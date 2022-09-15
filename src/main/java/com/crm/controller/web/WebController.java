package com.crm.controller.web;

import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.crm.model.company.dto.CustomerDTO;
import com.crm.repository.territory.MunicipalityRepository;
import com.crm.service.company.CustomerService;

@Controller
@RequestMapping("/web")
public class WebController {
	
	@Autowired
	MunicipalityRepository municipalityRepository;
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/index")
	public String home() {
		return "home";
	}
	
	@GetMapping("/customer/formadd")
	public ModelAndView fromAddCustomer() {
		ModelAndView model=new ModelAndView("addformcustomer");
		CustomerDTO customer=new CustomerDTO();
		customer.setDateFirstContact(new Date(new java.util.Date().getTime()));
		customer.setDateLastContact(new Date(new java.util.Date().getTime()));
		model.addObject("customer",customer);
		model.addObject("municipalities",municipalityRepository.findAll());
		return model;
	}
	
	@GetMapping("/customer/add")
	public String addCustomerToDatabase(@ModelAttribute("customer") CustomerDTO customer) {
		customerService.addCustomer(customer);
		return "home";
	}
}
