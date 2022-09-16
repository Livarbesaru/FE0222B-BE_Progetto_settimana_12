package com.crm.service.company;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.crm.model.company.Customer;
import com.crm.model.company.dto.CustomerDTO;
import com.crm.repository.company.CustomerRepository;
import com.crm.repository.company.HeadquarterRepository;
import com.crm.repository.company.InvoiceRepository;
import com.crm.repository.territory.MunicipalityRepository;
import com.crm.service.CustomerTypeFactory;
import com.crm.util.converter.CustomerDTOConverter;
import com.crm.util.exception.CustomerException;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepository;
	
	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	HeadquarterRepository headquarterRepository;
	
	@Autowired
	MunicipalityRepository municipalityRepository;
	
	@Autowired
	CustomerTypeFactory customerTypeConverter;
	
	@Autowired
	CustomerDTOConverter customerDTOConverter;
	
	public Customer addCustomer(CustomerDTO customerDTO){
			Customer customer=customerDTOConverter.convert(customerDTO);
			customerRepository.save(customer);
			return customer;
	}
	
	public Customer editCustomer(CustomerDTO customerDTO) {
		Customer customer=customerDTOConverter.convert(customerDTO);
		if(customer.getId()!=null 
			&& customer.getOperationalHeadquarter().getId()!=null 
			&& customer.getRegisteredHeadquarter().getId()!=null) {
			Optional<Customer> customFinded=customerRepository.findById(customer.getId());
			if(customFinded.isPresent()) {
				if(customer.getRegisteredHeadquarter().getId().longValue() == customFinded.get().getRegisteredHeadquarter().getId().longValue()) {
					if(customer.getOperationalHeadquarter().getId().longValue() == customFinded.get().getOperationalHeadquarter().getId().longValue()) {
						customFinded.get().setValues(customer);
						//gestire UNIQUE
						customerRepository.save(customFinded.get());
						return customer;
					}
				}
			}
		}
		throw new CustomerException("Id of customer, operational headquarters and registered headquarters must have their original values");
	}
	
	public Optional<Customer> getCustomerById(Long id){
		return customerRepository.findById(id);
	}
	
	public ResponseEntity<String> deleteCustomerByID(Long id) {
		Optional<Customer> customer=customerRepository.findById(id);
		if(customer.isPresent()) {
			invoiceRepository.deleteByCustomer(customer.get().getId().longValue());
			customerRepository.delete(customer.get());
			headquarterRepository.deleteByIdCustom(customer.get().getOperationalHeadquarter().getId());
			headquarterRepository.deleteByIdCustom(customer.get().getRegisteredHeadquarter().getId());
			return new ResponseEntity<String>("Customer has been deleted",HttpStatus.OK);
		}
		throw new CustomerException("Id is not associeted to any customer");
	}
	
	public Page<Customer> sortByAll(Pageable pageable){
		return customerRepository.findAll(pageable);
	}
	
	public List<Customer> findAll(){
		return customerRepository.findAll();
	}
	
	public List<Customer> sortByProvince(String order,int page,int size){
		if(order.toLowerCase().equals("asc")) {
			return customerRepository.orderByProvince(PageRequest.of(page,size, Sort.by("registeredHeadquarter.municipality.province.name").ascending()));
		}else {
			return customerRepository.orderByProvince(PageRequest.of(page,size,Sort.by("registeredHeadquarter.municipality.province.name").descending()));
		}
	}
	
	public List<Customer> filterAll(int page, int size, String variable,String value1,String value2, String sort) {
		List<Customer> customer=null;
		if(variable.toLowerCase().equals("nameandsurname")) {
			if(sort.toLowerCase().equals("asc")) {
				customer=customerRepository.findAll(PageRequest.of(page,size, Sort.by(value1,value2).ascending())).stream()
						.filter(x->x.compareVariables(variable, value1, value2,customerTypeConverter)).toList();
				return customer;
			}else {
				customer=customerRepository.findAll(PageRequest.of(page,size, Sort.by(value1,value2).descending())).stream()
						.filter(x->x.compareVariables(variable, value1, value2,customerTypeConverter)).toList();
				return customer;
			}
		}
		else {
			if(sort.toLowerCase().equals("asc")) {
				customer=customerRepository.findAll(PageRequest.of(page,size, Sort.by(variable).ascending())).stream()
						.filter(x->x.compareVariables(variable, value1, value2,customerTypeConverter)).toList();
				return customer;
			}else {
				customer=customerRepository.findAll(PageRequest.of(page,size, Sort.by(variable).descending())).stream()
						.filter(x->x.compareVariables(variable, value1, value2,customerTypeConverter)).toList();
				return customer;
			}
		}

	}
}
