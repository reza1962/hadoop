A sample based on Spring-Integration, Avro and Kafka

Contains a message producer and message consumer

++ Adjust
application.properties file

++ Build
mvn clean compile package

+++ To test with Kafka
kafka-topics.sh --list --zookeeper sandbox:2181
kafka-console-producer.sh --broker-list sandbox:6667 --topic transaction.topic
kafka-console-consumer.sh --zookeeper sandbox:2181 --topic transaction.topic --from-beginning

+++ Copy the target to the sandbox
scp -r target/ reza@sandbox.no.krb:/home/reza/multilix-hadoop-hbase/projects/kafka/targe


+++ To run on sandbox
sh target/appassembler/bin/transactionProducerApp
sh target/appassembler/bin/transactionConsumerApp