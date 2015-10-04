package com.multilix.kafka;


import com.multilix.kafka.transaction.Transaction;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.integration.kafka.serializer.avro.AvroSpecificDatumBackedKafkaDecoder;
import org.springframework.integration.kafka.serializer.avro.AvroSpecificDatumBackedKafkaEncoder;

import java.util.Date;

public class TransactionSerializerTest {

    @Test
    public void testEncodeDecode() {
        final AvroSpecificDatumBackedKafkaEncoder<Transaction> avroBackedKafkaEncoder = new AvroSpecificDatumBackedKafkaEncoder<Transaction>(Transaction.class);
        final Transaction transaction = new Transaction("fromIBAN", "toIBAN", new Date().getTime(), 12.42);
        final byte[] data = avroBackedKafkaEncoder.toBytes(transaction);

        final AvroSpecificDatumBackedKafkaDecoder<Transaction> avroSpecificDatumBackedKafkaDecoder = new AvroSpecificDatumBackedKafkaDecoder<Transaction>(Transaction.class);
        final Transaction decodedTransaction = avroSpecificDatumBackedKafkaDecoder.fromBytes(data);

        Assert.assertEquals(transaction.getFromIBAN(), decodedTransaction.getFromIBAN().toString());
        Assert.assertEquals(transaction.getToIBAN(), decodedTransaction.getToIBAN().toString());
    }
}

