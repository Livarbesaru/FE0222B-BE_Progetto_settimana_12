package com.crm.util.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.crm.model.company.Customer;
import com.crm.model.company.dto.CustomerDTO;
import com.crm.repository.company.CustomerRepository;
import com.crm.repository.company.HeadquarterRepository;
import com.crm.repository.territory.MunicipalityRepository;
import com.crm.service.CustomerTypeFactory;

@Component
public class CustomerDTOConverter implements Converter<CustomerDTO, Customer> {
	
	@Autowired
	private CustomerTypeFactory typeConverter;
	
	@Autowired
	private HeadQuartersDTOConverter headquartersConverter;
	
	@Override
	public Customer convert(CustomerDTO value) {
		Customer customer=new Customer();
			customer.setId(value.getId());
			customer.setCompanyName(value.getCompanyName());
			customer.setVatNumber(value.getVatNumber());
			customer.setEmail(value.getEmail());
			customer.setDateFirstContact(value.getDateFirstContact());
			customer.setDateLastContact(value.getDateLastContact());
			customer.setAnnualRevenue(value.getAnnualRevenue());
			customer.setPec(value.getPec());
			customer.setPhone(value.getPhone());
			customer.setEmailContact(value.getEmailContact());
			customer.setNameContact(value.getNameContact());
			customer.setSurnameContact(value.getSurnameContact());
			customer.setPhoneContact(value.getPhoneContact());
			customer.setType(typeConverter.convert(value.getType()));
			customer.setRegisteredHeadquarter(
					headquartersConverter.convert(value.getRegisteredHeadquarter()));
			customer.setOperationalHeadquarter(
					headquartersConverter.convert(value.getOperationalHeadquarter()));
		return customer;
	}

}
