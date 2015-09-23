== HDFS and MapReduce sample
This the copy of the Spring hadoop samples. I have add the functionality: 
	. to work with Hortonworks ditribution 
	. Security (Kerberos)

See: 
	https://github.com/spring-projects/spring-hadoop/wiki/Sample-Projects
	http://docs.spring.io/spring-hadoop/docs/2.1.2.RELEASE/reference/html/index.html
	
In this sample we will execute HDFS operations and a MapReduce job.  
The MapReduce job is the familiar wordcount job.  

=== Building and running
see 
	application-context.xml if you have enabled kerberos
	
To install Kerberos:
		https://github.com/abajwa-hw/security-workshops/blob/master/Setup-kerberos-Ambari.md

+++++++Check your vi /var/lib/ambari-server/resources/scripts/krb5.conf file to setup application-context.xml for kerberos
Mine looks like:

[logging]
 default = FILE:/var/log/krb5libs.log
 kdc = FILE:/var/log/krb5kdc.log
 admin_server = FILE:/var/log/kadmind.log

[libdefaults]
 default_realm = HORTONWORKS.COM
 dns_lookup_realm = false
 dns_lookup_kdc = false
 ticket_lifetime = 24h
 renew_lifetime = 7d
 forwardable = true

[realms]
 HORTONWORKS.COM = {
  kdc = sandbox.hortonworks.com
  admin_server = sandbox.hortonworks.com
 }
++++++	
	
Use the following commands to build and run the sample
*** Please adjust your path

	$ cd mapreduce
    $ mvn clean package

    $ scp -r target/ reza@sandbox.krb:/home/reza/multilix-hadoop-hbase/projects/mapreduce/target

On Hortonworks-Server:
	1) cd /home/reza/multilix-hadoop-hbase/projects/mapreduce/target
	2) touch mr.sh
	
	3) Copy the folowing lines to the mr.sh
	
#!/bin/bash -x
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
	
	
	4) check your Kerberos (if you activate it)
		klist
	5) init your ticket
		kinit <user>
	6) if the get error:
			kadmin.local
			addprinc reza@HORTONWORKS.COM
				
	7) chmod -R 777 /hadoop/yarn/local/usercache/reza
	8) sudo -u hdfs hdfs dfs -chmod 777 /user
	9) hadoop fs -mkdir -p /user/reza/libMR
   10) hadoop fs -mkdir -p /user/reza/input 
   11) hadoop fs -copyFromLocal target/appassembler/data/nietzsche-chapter-1.txt  /user/reza/input 
   12) hadoop fs -rm -r -skipTrash /user/reza/output/
   13) hadoop fs -rm -r -skipTrash /user/reza/libMR/*.jar
   14) hadoop fs -copyFromLocal target/appassembler/repo/*.jar /user/reza/libMR
   15) sh mr.sh
   16) hadoop fs -cat /user/reza/output/part-r-00000	



