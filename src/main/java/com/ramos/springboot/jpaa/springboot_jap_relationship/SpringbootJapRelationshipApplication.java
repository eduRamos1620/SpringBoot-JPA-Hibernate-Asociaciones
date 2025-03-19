package com.ramos.springboot.jpaa.springboot_jap_relationship;

import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Client;
import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Invoice;
import com.ramos.springboot.jpaa.springboot_jap_relationship.repositories.ClientRepository;
import com.ramos.springboot.jpaa.springboot_jap_relationship.repositories.InvoiceRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class SpringbootJapRelationshipApplication implements CommandLineRunner{

	@Autowired
    private ClientRepository clientRepository;
	@Autowired
	private InvoiceRepository invoiceRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJapRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		manyToOneFindByIdClient();
	}

	@Transactional
	public void manyToOne(){
		Client client = new Client("Juan", "Sanchez");//se crea el cliente
		clientRepository.save(client);//se guarda el cliente en BD

		Invoice invoice = new Invoice("Compras de motos", 3500L);//se crea la factura
		invoice.setClient(client);//a la factura se le asigna el cliente
		Invoice invoiceDB = invoiceRepository.save(invoice);//se gurada la factura en BD y se obtiene los datos de la factura
		System.out.println(invoiceDB);
	}

	@Transactional
	public void manyToOneFindByIdClient(){
		Optional<Client> optionalClient = clientRepository.findById(1L); //se obtiene el cliente desde la Bd por su id

		//se valida si existe el registro
		if( optionalClient.isPresent()){
			Client client = optionalClient.orElseThrow(); //se obtiene los vlaores del cliente
			
			Invoice invoice = new Invoice("Compras de motos", 3500L);//se crea la factura
			invoice.setClient(client);//a la factura se le asigna el cliente
			Invoice invoiceDB = invoiceRepository.save(invoice);//se gurada la factura en BD y se obtiene los datos de la factura
			System.out.println(invoiceDB);
		}
	}
}
