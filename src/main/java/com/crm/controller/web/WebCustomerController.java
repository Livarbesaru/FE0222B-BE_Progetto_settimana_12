package com.crm.controller.web;

import java.sql.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.crm.model.company.Customer;
import com.crm.model.company.CustomerType;
import com.crm.model.company.dto.CustomerDTO;
import com.crm.model.company.dto.HeadquarterDTO;
import com.crm.repository.territory.MunicipalityRepository;
import com.crm.service.company.CustomerService;
import com.crm.util.exception.CRMException;

@Controller
@RequestMapping("/web/customer")
public class WebCustomerController {
	
	
	@Autowired
	HttpServletRequest request;
	
	@Autowired
	MunicipalityRepository municipalityRepository;
	
	@Autowired
	CustomerService customerService;
	
	
	
	@GetMapping("/formadd")
	public ModelAndView formAddCustomer() {
		ModelAndView model=new ModelAndView("customer//addformcustomer");
		CustomerDTO customer=new CustomerDTO();
		customer.setId(0L);
		model.addObject("customer",customer);
		model.addObject("registered",new HeadquarterDTO());

		model.addObject("operational",new HeadquarterDTO());
		
		model.addObject("types", CustomerType.values());

		model.addObject("municipalities",municipalityRepository.findAll().stream()
				.sorted((m1,m2)->m1.getName().compareTo(m2.getName())).collect(Collectors.toList()));
		return model;
	}
	
	@PostMapping("/add")
	public String addCustomerToDatabase(
			@ModelAttribute("customer") CustomerDTO customer,
			@ModelAttribute("registered") HeadquarterDTO registered,
			@ModelAttribute("operational") HeadquarterDTO operational) {
		customer.setRegisteredHeadquarter(registered);
		customer.setOperationalHeadquarter(operational);
		customer.setDateFirstContact(new Date(new java.util.Date().getTime()));
		customer.setDateLastContact(new Date(new java.util.Date().getTime()));
		customer.setId(null);
		customerService.addCustomer(customer);
		return "home";
	}
	
	@GetMapping("/formedit/{id}")
	public ModelAndView fromEditCustomer(@PathVariable Long id) {
		ModelAndView model=new ModelAndView("customer//editformcustomer");
		Optional<Customer> customer=customerService.getCustomerById(id);
		if(customer.isPresent()) {
			CustomerDTO customerDTO=new CustomerDTO();
			HeadquarterDTO rh=new HeadquarterDTO();
			HeadquarterDTO oh=new HeadquarterDTO();
			
			customerDTO.setId(customer.get().getId());
			customerDTO.setAnnualRevenue(customer.get().getAnnualRevenue());
			customerDTO.setCompanyName(customer.get().getCompanyName());
			customerDTO.setEmail(customer.get().getEmail());
			customerDTO.setEmailContact(customer.get().getEmailContact());
			customerDTO.setPec(customer.get().getPec());
			customerDTO.setNameContact(customer.get().getNameContact());
			customerDTO.setSurnameContact(customer.get().getSurnameContact());
			customerDTO.setPhone(customer.get().getPhone());
			customerDTO.setPhoneContact(customer.get().getPhoneContact());
			customerDTO.setVatNumber(customer.get().getVatNumber());
			customerDTO.setOperationalHeadquarter(oh);
			customerDTO.setRegisteredHeadquarter(rh);
			
			oh.setId(customer.get().getOperationalHeadquarter().getId());
			oh.setLocality(customer.get().getOperationalHeadquarter().getLocality());
			oh.setMunicipality(customer.get().getOperationalHeadquarter()
					.getMunicipality().getIdM());
			oh.setPostalCode(customer.get().getOperationalHeadquarter().getPostalCode());
			oh.setStreet(customer.get().getOperationalHeadquarter().getStreet());
			oh.setStreetNumber(customer.get().getOperationalHeadquarter().getStreetNumber());
			
			rh.setId(customer.get().getRegisteredHeadquarter().getId());
			rh.setLocality(customer.get().getRegisteredHeadquarter().getLocality());
			rh.setMunicipality(customer.get().getRegisteredHeadquarter()
					.getMunicipality().getIdM());
			rh.setPostalCode(customer.get().getRegisteredHeadquarter().getPostalCode());
			rh.setStreet(customer.get().getRegisteredHeadquarter().getStreet());
			rh.setStreetNumber(customer.get().getRegisteredHeadquarter().getStreetNumber());
			
			model.addObject("registered",rh);
			model.addObject("operational",oh);
			model.addObject("customer",customerDTO);
			model.addObject("id",id);
			model.addObject("types", CustomerType.values());
			model.addObject("municipalities",municipalityRepository.findAll().stream()
					.sorted((m1,m2)->m1.getName().compareTo(m2.getName())).collect(Collectors.toList()));
			return model;
		}
		throw new CRMException("The customer's id you gave does not exist");
	}
	
	@PostMapping("/edit")
	public String editCustomerToDatabase(
			@ModelAttribute("customer") CustomerDTO customer,
			@ModelAttribute("id") Long id,
			@ModelAttribute("registered") HeadquarterDTO rh,
			@ModelAttribute("operational") HeadquarterDTO oh) {
		Optional<Customer> customerDB=customerService.getCustomerById(id);
		if(customerDB.isPresent()) {
			customer.setId(id);
			oh.setId(customerDB.get().getOperationalHeadquarter().getId());
			rh.setId(customerDB.get().getRegisteredHeadquarter().getId());
			customer.setOperationalHeadquarter(oh);
			customer.setRegisteredHeadquarter(rh);
			customer.setDateFirstContact(customerDB.get().getDateFirstContact());
			customer.setDateLastContact(customerDB.get().getDateLastContact());
			customerService.editCustomer(customer);
		}
		return "home";
	}
	
	@GetMapping("/formdelete/{id}")
	public ModelAndView deletedForm(@PathVariable Long id) {
		ModelAndView model=new ModelAndView("customer//deleteformcustomer");
		Optional<Customer> customer=customerService.getCustomerById(id);
		if(customer.isPresent()) {
			model.addObject("customer",customer.get());
			model.addObject("id",customer.get().getId().longValue());
			return model;
		}
		throw new CRMException("The customer's id you gave does not exist");
	}
	
	@PostMapping("/delete/{id}")
	public String deleteCustomer(@PathVariable Long id) {
		Optional<Customer> customer=customerService.getCustomerById(id);
		if(customer.isPresent()) {
			customerService.deleteCustomerByID(id);
			return "redirect:/web/customer/list?size=20&page=0";
		}
		throw new CRMException("The customer's id you gave does not exist");
	}
	
	@RequestMapping("/list")
	public ModelAndView listCustomer(@RequestParam int page,@RequestParam int size) {
		ModelAndView model=new ModelAndView("customer//customerlist");
		model.addObject("list",customerService.sortByAll(PageRequest.of(page, size)));
		return model;
	}

}
