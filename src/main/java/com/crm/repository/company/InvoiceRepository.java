package com.crm.repository.company;

import org.springframework.data.jpa.repository.JpaRepository;

import com.crm.model.company.invoices.Invoice;

public interface InvoiceRepository extends JpaRepository<Invoice, Long> {

}
