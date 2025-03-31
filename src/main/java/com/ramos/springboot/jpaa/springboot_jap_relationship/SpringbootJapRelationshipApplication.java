package com.ramos.springboot.jpaa.springboot_jap_relationship;

import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Address;
import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Client;
import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.ClientDetails;
import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Course;
import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Invoice;
import com.ramos.springboot.jpaa.springboot_jap_relationship.entities.Student;
import com.ramos.springboot.jpaa.springboot_jap_relationship.repositories.ClientDetailsRepository;
import com.ramos.springboot.jpaa.springboot_jap_relationship.repositories.ClientRepository;
import com.ramos.springboot.jpaa.springboot_jap_relationship.repositories.CourseRepository;
import com.ramos.springboot.jpaa.springboot_jap_relationship.repositories.InvoiceRepository;
import com.ramos.springboot.jpaa.springboot_jap_relationship.repositories.StudentRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

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

	@Autowired
	private ClientDetailsRepository clientDetailsRepository;

	@Autowired
	private StudentRepository studentRepository;

	@Autowired
	private CourseRepository courseRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJapRelationshipApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		manyToManyRemoveFind();
	}

	@Transactional
	public void manyToManyRemoveFind(){
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);

		Optional<Student> studentOptionalDb = studentRepository.findOneWithCourse(1L);
		if (studentOptionalDb.isPresent()) {
			Student studentDb = studentOptionalDb.get();
			Optional<Course> courseOptionalDb = courseRepository.findById(2L);

			if (courseOptionalDb.isPresent()) {
				Course courseDb = courseOptionalDb.get();
				studentDb.getCourses().remove(courseDb);

				studentRepository.save(studentDb);
				System.out.println(studentDb);
			}
		}
	}

	@Transactional
	public void manyToManyFind(){
		Optional<Student> studentOptional1 = studentRepository.findById(1L);
		Optional<Student> studentOptional2 = studentRepository.findById(2L);

		Student student1 = studentOptional1.get();
		Student student2 = studentOptional2.get();

		Course course1 = courseRepository.findById(1L).get();
		Course course2 = courseRepository.findById(2L).get();

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	@Transactional
	public void manyToMany(){
		Student student1 = new Student("Isdh", "Dhis");
		Student student2 = new Student("Eduardo", "Ramos");

		Course course1 = new Course("Java", "Andres");
		Course course2 = new Course("Python", "Juan");

		student1.setCourses(Set.of(course1, course2));
		student2.setCourses(Set.of(course2));

		studentRepository.saveAll(Set.of(student1, student2));

		System.out.println(student1);
		System.out.println(student2);
	}

	@Transactional
	public void oneToOneBidireccionalFindById(){
		Optional<Client> clientOptional = clientRepository.findOne(2L);
		
		clientOptional.ifPresent(client -> {
			ClientDetails clientDetails = new ClientDetails(true, 5000);
	
			client.setClientDetails(clientDetails);
			Client clientDb = clientRepository.save(client);
	
			System.out.println(clientDb);
		});
	}

	@Transactional
	public void oneToOneBidireccional(){
		Client client = new Client("Miguel", "Hidalgo");
		
		ClientDetails clientDetails = new ClientDetails(true, 5000);

		client.setClientDetails(clientDetails);
		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void oneToOneFindById(){
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Optional<Client> clientOptional = clientRepository.findOne(2L);
		clientOptional.ifPresent(client -> {
			client.setClientDetails(clientDetails);
			clientRepository.save(client);
	
			System.out.println(client);
		});
	}

	@Transactional
	public void oneToOne(){
		ClientDetails clientDetails = new ClientDetails(true, 5000);
		clientDetailsRepository.save(clientDetails);

		Client client = new Client("Miguel", "Hidalgo");
		client.setClientDetails(clientDetails);
		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void removeBidireccional(){
		Client client = new Client("Josefa", "Ortiz");//Se crea el cliente

			Invoice invoice1 = new Invoice("Compras de juegos", 399L);
			Invoice invoice2 = new Invoice("Compras de oficina", 8999L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			Client clientDB = clientRepository.save(client);

			System.out.println(clientDB);

		Optional<Client> optionalClient2 = clientRepository.findOne(3L);
		optionalClient2.ifPresent(client2 -> {
			Optional<Invoice> invOptional = invoiceRepository.findById(2L);
			invOptional.ifPresent(invoice -> {
				client2.getInvoices().remove(invoice);
				invoice.setClient(null);
				clientRepository.save(client2);
				System.out.println(client2);
			});
		});
	}

	@Transactional
	public void oneToManyRemoveBidireccionalFindById(){
		Optional<Client> optionalClient = clientRepository.findOne(1L);

		optionalClient.ifPresent(client -> {
			Invoice invoice1 = new Invoice("Compras de juegos", 399L);
			Invoice invoice2 = new Invoice("Compras de oficina", 8999L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			Client clientDB = clientRepository.save(client);

			System.out.println(clientDB);
		});

		Optional<Client> optionalClient2 = clientRepository.findOne(1L);

		optionalClient2.ifPresent(client -> {
			Optional<Invoice> invOptional = invoiceRepository.findById(2L);
			invOptional.ifPresent(invoice -> {
				client.getInvoices().remove(invoice);
				invoice.setClient(null);
				clientRepository.save(client);
				System.out.println(client);
			});
		});
	}

	@Transactional
	public void oneToManyBidireccionalFindById(){
		Optional<Client> optionalClient = clientRepository.findOne(1L);

		optionalClient.ifPresent(client -> {
			Invoice invoice1 = new Invoice("Compras de juegos", 399L);
			Invoice invoice2 = new Invoice("Compras de oficina", 8999L);

			client.addInvoice(invoice1).addInvoice(invoice2);

			Client clientDB = clientRepository.save(client);

			System.out.println(clientDB);
		});
	}

	@Transactional
	public void oneToManyBidireccional(){
		Client client = new Client("Josefa", "Ortiz");//Se crea el cliente

		Invoice invoice1 = new Invoice("Compras de juegos", 399L);
		Invoice invoice2 = new Invoice("Compras de oficina", 8999L);

		
		client.addInvoice(invoice1).addInvoice(invoice2);

		clientRepository.save(client);

		System.out.println(client);
	}

	@Transactional
	public void removeAddressFindById(){
		Optional<Client> optionalClient  = clientRepository.findById(2L);
		optionalClient.ifPresent(client -> {
			Address address1 = new Address("Centro", 123);//Se crea una direccion
			Address address2 = new Address("camino viejo", 1234);//Se crea una segunda direccion
	
			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);
	
			Client clientDB =  clientRepository.save(client);//Se guarda el cliente con sus direcciones en la BD
	
			System.out.println(clientDB);

			Optional<Client> optionalClient2 = clientRepository.findOneWithAddresses(2L);
			optionalClient2.ifPresent(c -> {
				c.getAddresses().remove(address1);
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
	
			Set<Address> addresses = new HashSet<>();
			addresses.add(address1);
			addresses.add(address2);
			client.setAddresses(addresses);
	
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
