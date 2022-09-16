package com.crm.controller.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.crm.repository.territory.MunicipalityRepository;
import com.crm.service.company.CustomerService;

@Controller
@RequestMapping("/web")
public class WebController {
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	MunicipalityRepository municipalityRepository;
	
	@Autowired
	CustomerService customerService;
	
	@GetMapping("/index")
	public String home() {
		return "home";
	}
}
