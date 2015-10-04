package com.multilix.kafka.msg.consumer;

import com.multilix.kafka.app.TransactionProducerApp;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.integration.transformer.Transformer;
import org.springframework.messaging.Message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PartitionlessTransformer implements Transformer {
    private static final Log LOG = LogFactory.getLog(TransactionProducerApp.class);

	@Override
	@SuppressWarnings("unchecked")
	public Message<?> transform(final Message<?> message) {
		final Map<String, Map<Integer, List<Object>>> origData = (Map<String, Map<Integer, List<Object>>>) message.getPayload();
		final Map<String, List<Object>> nonPartitionedData = new HashMap<>();

        for(final String topic : origData.keySet()) {
            LOG.info("topic: " + topic);

			final Map<Integer, List<Object>> partitionedData = origData.get(topic);
			final Collection<List<Object>> nonPartitionedDataFromTopic = partitionedData.values();

			final List<Object> mergedList = new ArrayList<>();

            for (Integer Int : partitionedData.keySet()) {
                List<Object> dateList = partitionedData.get(Int);

                // Split the message
                for(Object o: dateList) {
                    LOG.info("***** got object from  partitionedData: " + o.toString());
                }
            }

			for (final List<Object> l : nonPartitionedDataFromTopic){
  				mergedList.addAll(l);
			}

			nonPartitionedData.put(topic, mergedList);
		}

		return MessageBuilder.withPayload(nonPartitionedData).build();
	}
}