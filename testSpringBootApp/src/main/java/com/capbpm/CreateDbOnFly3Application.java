package com.capbpm;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnitUtil;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

//import com.component.Company;
import com.component.Customer;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.component", "com.capbpm" })
@ComponentScan(basePackages = {"com.component" , "com.capbpm" })

public class CreateDbOnFly3Application {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private EntityManagerFactory entityManagerFactory;
	
    public static void main(String[] args) {
    	for (int idx=0; idx < args.length; idx++){
    		System.out.println("args idx="+idx+" val:"+args[idx]);
    	}
        SpringApplication.run(CreateDbOnFly3Application.class, args);
    }

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepository) {
		return (args) -> {
			
			
			//Configuration cfg = new Configuration();
			//cfg.configure("hibernate.cfg.xml");
			//SessionFactory factory = cfg.buildSessionFactory();
			//Session session = factory.openSession();
			
			//EntityManager entityManager = entityManagerFactory.createEntityManager();
			//entityManager.getTransaction().begin();


			Customer e1 = new Customer();
			e1.setAccountBalance(42);
			e1.setAge(101);
			e1.setFirstName("test");
			e1.setId(1);
			e1.setIsActive(true);
			e1.setLastName("insert");
			e1.setStartDate(new Date());

			Object o = e1;
			
			//entityManager.persist( e1 );
			//entityManager.persist( o );
			
			customerRepository.save(e1);
		
			//Company c1 = new Company();
			//c1.setAddress("1313 Mockingbird Lane, Eerie, Pennsylvania");
            //c1.setNetWorth(100000);
			//
			//Object o2 = c1;
			//
			//entityManager.persist( o2 );
			
			//entityManager.getTransaction().commit();
			//entityManager.close();
			
			/*
			// save a couple of customers
			repository.save(new Employee("Jack", "Bauer"));
			repository.save(new Employee("Chloe", "O'Brian"));
			repository.save(new Employee("Kim", "Bauer"));
			repository.save(new Employee("David", "Palmer"));
			repository.save(new Employee("Michelle", "Dessler"));

			// fetch all customers
			log.info("Employees found with findAll():");
			log.info("-------------------------------");
			for (Employee employee : repository.findAll()) {
				log.info(employee.toString());
			}
            log.info("");

			// fetch an individual customer by ID
			Employee employee = repository.findOne(1L);
			log.info("Employee found with findOne(1L):");
			log.info("--------------------------------");
			log.info(employee.toString());
            log.info("");

			// fetch customers by last name
			log.info("Employee found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			for (Employee bauer : repository.findByLastName("Bauer")) {
				log.info(bauer.toString());
			}
            log.info("");*/
		};
	}
}
