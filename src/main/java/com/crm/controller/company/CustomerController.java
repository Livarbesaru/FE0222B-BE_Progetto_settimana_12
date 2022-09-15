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

import com.crm.model.company.Customer;
import com.crm.model.company.dto.CustomerDTO;
import com.crm.service.company.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/customer")
public class CustomerController {
	
	@Autowired
	private CustomerService customerService;

	@PostMapping("/add")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To add a customer do not specify an id or it will throw an error")
	public ResponseEntity<Customer> addCustomer(@RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<Customer>(customerService.addCustomer(customerDTO),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/edit")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To edit a customer do specify an existing id or it will throw an error")
	public ResponseEntity<Customer> editCustomer(@RequestBody CustomerDTO customerDTO){
		return new ResponseEntity<Customer>(customerService.editCustomer(customerDTO),HttpStatus.OK);
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/delete")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To delete a customer do specify an existing id or it will throw an error")
	public ResponseEntity<String> editCustomer(@RequestParam Long id){
		return new ResponseEntity<String>(customerService.deleteCustomerByID(id).getBody(),HttpStatus.OK);
	}
	
	@GetMapping("/getbyid")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To get a customer do specify an existing id or it will throw an error")
	public ResponseEntity<Customer> getCustomerById(@RequestParam Long id){
		return new ResponseEntity<Customer>(customerService.getCustomerById(id).get(),HttpStatus.OK);
	}
	
	@GetMapping("/get")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To get all customer")
	public ResponseEntity<Page<Customer>> getCustomers(Pageable pageable){
		return new ResponseEntity<Page<Customer>>(customerService.sortByAll(pageable),HttpStatus.OK);
	}
	
	@GetMapping("/sortbyprovince")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To sort the customers based on their registered headquarters's province")
	public ResponseEntity<List<Customer>> sortByProvince(@RequestParam String sort, @RequestParam int page, @RequestParam int size){
		return new ResponseEntity<List<Customer>>(customerService.sortByProvince(sort, page, size),HttpStatus.OK);
	}
	
	@GetMapping("/filter")
	@SecurityRequirement(name="bearerAuth")
	@Operation(summary = "To filter all customer in a range based on their fields, "
			+ "except nameContanct and CustomerType, the 'to' param must be declareted to work")
	public ResponseEntity<List<Customer>> filterCustomer(
			@RequestParam int page,@RequestParam int size,
			@RequestParam String variable, @RequestParam String from,
			@RequestParam String to, @RequestParam String sort){
		return new ResponseEntity<List<Customer>>(customerService.filterAll(page, size, variable, from, to, sort),HttpStatus.OK);
	}
}
