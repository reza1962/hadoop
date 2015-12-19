package com.multilix.kafka.app;

import com.multilix.kafka.transaction.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.integration.kafka.support.KafkaHeaders;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.MessageChannel;

import java.util.Date;

public class TransactionProducerApp {
	private static final String CONFIG = "/META-INF/spring/app-producer-context.xml";
	private static final Log LOG = LogFactory.getLog(TransactionProducerApp.class);

	public static void main(final String args[]) {
		final ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext(CONFIG, TransactionProducerApp.class);
		ctx.start();

		final MessageChannel channel = ctx.getBean("inputToKafkaChannel", MessageChannel.class);
		LOG.info("inputToKafkaChannel:" + channel.getClass());

        // Produce messages every six seconds
        int msgCount = 0;
		while (true) {
			final Transaction transaction = new Transaction();
            transaction.setFromIBAN("fromIBAN" + msgCount);
            transaction.setToIBAN("toIBAN" + msgCount);
            transaction.setAmount(100.42);
            transaction.setTransactionTime(new Date().getTime());

            channel.send(MessageBuilder.withPayload(transaction)
							.setHeader("messageKey", String.valueOf(msgCount))
							.setHeader(KafkaHeaders.TOPIC, "transaction.topic").build());
            msgCount++;
            LOG.info("message sent " + msgCount);
            try {
                Thread.sleep(1000); // Six seconds
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}

	}
}