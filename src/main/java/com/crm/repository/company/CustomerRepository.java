package com.crm.repository.company;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.crm.model.company.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	//@Query("Select c from Customer c join Headquarter h on c.registeredHeadquarter.id = h.id order by h.id ?1")
	@Query("Select c from Customer c")
	List<Customer> orderByProvince(Pageable pageable);
//	
//	@Query("Select c from Customer c where c.nameContact like %?1% ")
//	List<Customer> findByNameContactCustom(String name,Pageable pageable);
//	
//	@Query("Select c from Customer c where c.annualRevenue between :start And :end")
//	List<Customer> findByAnnualRevenueBeetwen(@Param("start") float start,@Param("end") float end, Pageable pageable);
//	
//	List<Customer> findByDateFirstContactBetween(Date start,Date end, Pageable pageable);
//	
//	List<Customer> findByDateLastContactBetween(Date start,Date end, Pageable pageable);
}
