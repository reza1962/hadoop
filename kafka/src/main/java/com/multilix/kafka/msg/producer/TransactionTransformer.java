package com.multilix.kafka.msg.producer;

import com.multilix.kafka.transaction.Transaction;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;

import java.util.Date;


public class TransactionTransformer implements Transformer {

    Log LOG = LogFactory.getLog(getClass());

    @Override
    public Message<?> transform(Message<?> message) {
        if (message.getPayload().getClass().isAssignableFrom(Transaction.class)) {
            Transaction transaction = (Transaction) message.getPayload();
            transaction.setTransactionTime(new Date().getTime());

            LOG.info("transaction transformed " + transaction.getTransactionTime());

            return MessageBuilder.withPayload(transaction).copyHeaders(message.getHeaders()).build();
        }
        return message;
    }
}
