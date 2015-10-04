package com.multilix.kafka.msg.producer;

import kafka.producer.Partitioner;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

class CustomPartitioner implements Partitioner {
    private static final Log LOG = LogFactory.getLog(CustomPartitioner.class);

	public int partition(final Object key, final int numPartitions) {
		final String s = (String) key;
		final Integer i = Integer.parseInt(s);

        int p = i % numPartitions;

        LOG.info("Partition: " + p);
		return p;
	}
}