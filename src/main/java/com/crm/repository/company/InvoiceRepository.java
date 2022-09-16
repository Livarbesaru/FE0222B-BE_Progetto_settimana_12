package com.crm.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.crm.model.company.invoices.Invoice;

@Transactional
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

	@Modifying
	@Query(value="delete from invoice as i where i.customer_id = :id",nativeQuery=true)
	public void deleteByCustomer(@Param("id") long id);
}
