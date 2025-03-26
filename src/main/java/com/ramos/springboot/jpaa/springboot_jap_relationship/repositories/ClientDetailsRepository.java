package com.ramos.springboot.jpaa.springboot_jap_relationship.repositories;

import org.springframework.data.repository.CrudRepository;

import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.ClientDetails;

public interface ClientDetailsRepository extends CrudRepository<ClientDetails, Long>{
    
}
