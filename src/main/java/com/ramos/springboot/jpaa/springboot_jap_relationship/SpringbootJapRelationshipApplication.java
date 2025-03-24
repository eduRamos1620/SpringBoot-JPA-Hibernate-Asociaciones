package com.ramos.springboot.jpaa.springboot_jap_relationship;

import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Address;
import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Client;
import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Invoice;
import com.ramos.springboot.jpaa.springboot_jap_relationship.repositories.ClientRepository;
import com.ramos.springboot.jpaa.springboot_jap_relationship.repositories.InvoiceRepository;

import java.util.Arrays;
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
		removeAddressFindById();
	}

	@Transactional
	public void removeAddressFindById(){
		Optional<Client> optionalClient  = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("Centro", 123);//Se crea una direccion
			Address address2 = new Address("camino viejo", 1234);//Se crea una segunda direccion
	
			client.setAddresses(Arrays.asList(address1, address2));
	
			Client clientDB =  clientRepository.save(client);//Se guarda el cliente con sus direcciones en la BD
	
			System.out.println(clientDB);

			Optional<Client> optionalClient2 = clientRepository.findOne(2L);
			optionalClient2.ifPresent(c -> {
				c.getAddresses().remove(1);
				clientRepository.save(c);
				System.out.println(c);
			});
		});
	}

	@Transactional
	public void removeAddress(){
		Client client = new Client("Josefa", "Ortiz");//Se crea el cliente

		Address address1 = new Address("Centro", 123);//Se crea una direccion
		Address address2 = new Address("camino viejo", 1234);//Se crea una segunda direccion

		client.getAddresses().add(address1);//Se agrega la primer direccion a la lista
		client.getAddresses().add(address2);//Se agrega la segunda direccion a la lista

		clientRepository.save(client);//Se guarda el cliente con sus direcciones en la BD

		System.out.println(client);

		Optional<Client> optionaClient = clientRepository.findById(3L);
		optionaClient.ifPresent(c -> {
			c.getAddresses().remove(address1);
			clientRepository.save(c);
			System.out.println(c);
		});
	}
	@Transactional
	public void oneToMany(){
		Client client = new Client("Josefa", "Ortiz");//Se crea el cliente

		Address address1 = new Address("Centro", 123);//Se crea una direccion
		Address address2 = new Address("camino viejo", 1234);//Se crea una segunda direccion

		client.getAddresses().add(address1);//Se agrega la primer direccion a la lista
		client.getAddresses().add(address2);//Se agrega la segunda direccion a la lista

		clientRepository.save(client);//Se guarda el cliente con sus direcciones en la BD

		System.out.println(client);
	}

	@Transactional
	public void oneToManyFindById(){
		Optional<Client> optionalClient  = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("Centro", 123);//Se crea una direccion
			Address address2 = new Address("camino viejo", 1234);//Se crea una segunda direccion
	
			client.setAddresses(Arrays.asList(address1, address2));
	
			Client clientDB = clientRepository.save(client);//Se guarda el cliente con sus direcciones en la BD
	
			System.out.println(clientDB);

		});

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
