package com.multilix.hadoop.hbase;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CustomerApp {

	private static final Log log = LogFactory.getLog(CustomerApp.class);

	public static void main(String[] args) throws Exception {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("/META-INF/spring/application-context.xml", CustomerApp.class);
		log.info("HBase Application Running");
		context.registerShutdownHook();
		
		CustomerUtils customerUtils = context.getBean(CustomerUtils.class);		
		customerUtils.initialize();
		customerUtils.addCustomers();

		CustomerRepository customerRepository = context.getBean(CustomerRepository.class);

		List<Customer> customers = customerRepository.findAll();
		System.out.println("=========================== users ===========================");
		System.out.println("Number of Customers = " + customers.size());
		System.out.println(customers);
		System.out.println("=========================== END ===========================");

		
		int cnt = 100;
		for (int i=0; i< cnt; i++) {
				Customer customer = customerRepository.findByEmail(i + "@yahoo.com");
				System.out.println("=========================== user ===========================");
				System.out.println(customer);
				System.out.println("=========================== END ===========================");
		}
		
	}
}
