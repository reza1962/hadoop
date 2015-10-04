package com.multilix.kafka.app;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TransactionConsumerApp {
	private static final String CONFIG = "/META-INF/spring/app-consumer-context.xml";

	public static void main(final String args[]) {
		final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(CONFIG, TransactionConsumerApp.class);
		
		ctx.start();
	}
}