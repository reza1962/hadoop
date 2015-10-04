A sample App. based on Spring-Integration, Avro and Kafka

Contains a message producer and message consumer

++ Adjust</br>
application.properties file

++ Build</br>
mvn clean compile package

+++ To test with Kafka</br>
kafka-topics.sh --list --zookeeper sandbox:2181</br>
kafka-console-producer.sh --broker-list sandbox:6667 --topic transaction.topic</br>
kafka-console-consumer.sh --zookeeper sandbox:2181 --topic transaction.topic --from-beginning</br>

+++ Copy the target to the sandbox</br>
scp -r target/ reza@sandbox.no.krb:/home/<user>/kafka/target</br>


+++ To run on sandbox</br>
sh target/appassembler/bin/transactionProducerApp
sh target/appassembler/bin/transactionConsumerApp
