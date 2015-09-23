#!/bin/bash -x

# hadoop fs -mkdir /user/reza/input 
# hadoop fs -copyFromLocal target/appassembler/data/nietzsche-chapter-1.txt  /user/reza/input 
# hadoop fs -rm -r -skipTrash /user/reza/output/
# hadoop fs -rm -r -skipTrash /user/reza/libMR/*.jar
# hadoop fs -copyFromLocal target/appassembler/repo/*.jar /user/reza/libMR
 
export JAR_NAME="multilix-hadoop-mapreduce-1.0.0.BUILD-SNAPSHOT.jar"
export JAR_MAIN="com.multilix.hadoop.mapreduce.Wordcount"
export BD_LOCAL_APP_DIR="/home/reza/multilix-hadoop-hbase/projects/mapreduce/target/appassembler/repo"
 
export HADOOP_CLASSPATH=$HADOOP_CLASSPATH:/usr/hdp/current/hbase-client/lib/*:$BD_LOCAL_APP_DIR/*
echo "HADOOP_CLASSPATH:$HADOOP_CLASSPATH"
 
export LIBJARS=/usr/hdp/current/hbase-client/lib/hbase-common.jar,/usr/hdp/current/hbase-client/lib/hbase-protocol.jar,/usr/hdp/current/hbase-client/lib/hbase-client.jar,/usr/hdp/current/hbase-client/lib/hbase-hadoop-compat.jar,/usr/hdp/current/hbase-client/lib/hbase-server.jar,/usr/hdp/current/hbase-client/lib/htrace-core.jar
echo "LIBJARS:$LIBJARS"
command="hadoop jar $BD_LOCAL_APP_DIR/$JAR_NAME $JAR_MAIN -libjars ${LIBJARS}  -libDirsHdfs /user/reza/libMR/ -conf /etc/hbase/conf/hbase-site.xml  -logLevel DEBUG"
 
echo "$command"
$command

