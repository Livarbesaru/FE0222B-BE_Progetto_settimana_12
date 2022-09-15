package com.dblibrary;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import com.crm.model.company.dto.CustomerDTO;
import com.crm.model.company.dto.HeadquarterDTO;
import com.crm.service.company.CustomerService;

@SpringBootTest
@ContextConfiguration(classes = Progetto11ApplicationTests.class)
class Progetto11ApplicationTests {
	
//	@Autowired
//	CustomerService customerService;
		
	@Test
	public void addCustomer() {
//		HeadquarterDTO hr = new HeadquarterDTO("via qualcosa", 36, "localita", "AAAA", 5034L);
//		HeadquarterDTO ho = new HeadquarterDTO("via Qualcosaltro", 32, "loc", "BBBBB", 5034L);
//		CustomerDTO customer = new CustomerDTO("Compania qualcosa", 20202020202020L, "ciapo@banana.it",
//				new Date(Date.valueOf("1923-11-10").getTime()), new Date(Date.valueOf("1923-11-10").getTime()), 35.35F,
//				"ciapo@banana.it", "333333", "ciapo@banana.it", "giano", "francano", "333333", "srl", hr, ho);
//
//		assertTrue(customer.getCompanyName().equals(customerService
//				.getCustomerById((customerService.addCustomer(customer)).getId()).get().getCompanyName()));
	}

}
