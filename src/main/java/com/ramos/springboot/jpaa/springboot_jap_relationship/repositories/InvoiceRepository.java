package com.ramos.springboot.jpaa.springboot_jap_relationship.repositories;

import org.springframework.data.repository.CrudRepository;
import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Invoice;

public interface InvoiceRepository  extends CrudRepository<Invoice, Long>{
    
}
