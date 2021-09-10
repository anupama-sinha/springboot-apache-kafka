## Issues with Legacy Design
* Multiple source with multiple targets
* Number of connections is huge(Count of source * target)
* Varied protocols have to be handled as required(TCP,HTTPS,REST,FTP,TLS,JDBC,SSL,etc)
* Data format parsing also can be varied(Binary,CSV,JSON,Avro,etc)
* Increased connection load at varied times

### Why Apache Kafka
* Helps in decoupling data streams & systems
* A high throughput distributed messaging system acting as middleware between source and target

## Owner
* Created by LinkedIn
* 2011 : Maintained by Confluent(Open source)

## Downloads & Installation
* Prerequisite : JDK8, [7-Zip](https://www.7-zip.org/download.html) for Windows Installation
* Download [Zookeeper](http://zookeeper.apache.org/releases.html) & [Kafka](http://kafka.apache.org/downloads.html) and unzip with 7-Zip in Windows

### Installation Option#1 : Downloads can be separate. 
* Rename apache-zookeeper-3.7.0\conf\zoo_sample.cfg to “zoo.cfg” & edit(data.dir=dataDir=C:\Softwares\apache-zookeeper-3.7.0\data)
* Add System Environment Variable(ZOOKEEPER_HOME & Path as %ZOOKEEPER_HOME%\bin)
* Start Zookeeper : apache-zookeeper-3.7.0\bin>zkserver

### Option#2 : Kafka with Zookeeper(Kept in \kafka\bin\windows) as single package can also be installed(Followed this Option)
* ALternatively, just install Kafka. Create 2 folders : zookeeper-data,kafka-logs```properties
* Edit kafka_2.13-2.8.0\config\zookeeper.properties(dataDir=zookeeper-data folder path) & server.properties(log.dirs=kafka-log folder path & as below)
```properties
offsets.topic.num.partitions=1 
offsets.topic.replication.factor=1
transaction.state.log.replication.factor=1
transaction.state.log.min.isr=1
min.insync.replicas=1
default.replication.factor=1
```
* Add Kafka Tools Dir to Path env variable(kafka_2.13-2.8.0\bin\windows)
* Start Zookeeper Server
```properties
PS C:\kafka\bin\windows> .\zookeeper-server-start.bat C:\kafka\config\zookeeper.properties
[2021-09-10 15:29:26,791] INFO Using org.apache.zookeeper.server.NIOServerCnxnFactory as server connection factory (org.apache.zookeeper.server.ServerCnxnFactory)
[2021-09-10 15:29:26,801] INFO Configuring NIO connection handler with 10s sessionless connection timeout, 2 selector thread(s), 16 worker threads, and 64 kB direct buffers. (org.apache.zookeeper.server.NIOServerCnxnFactory)
[2021-09-10 15:29:26,821] INFO binding to port 0.0.0.0/0.0.0.0:2181 (org.apache.zookeeper.server.NIOServerCnxnFactory)
[2021-09-10 15:29:26,867] INFO zookeeper.snapshotSizeFactor = 0.33 (org.apache.zookeeper.server.ZKDatabase)
[2021-09-10 15:29:26,883] INFO Snapshotting: 0x0 to C:kafkazookeeper-data\version-2\snapshot.0 (org.apache.zookeeper.server.persistence.FileTxnSnapLog)
[2021-09-10 15:29:26,894] INFO Snapshotting: 0x0 to C:kafkazookeeper-data\version-2\snapshot.0 (org.apache.zookeeper.server.persistence.FileTxnSnapLog)
[2021-09-10 15:29:26,931] INFO PrepRequestProcessor (sid:0) started, reconfigEnabled=false (org.apache.zookeeper.server.PrepRequestProcessor)
[2021-09-10 15:29:26,950] INFO Using checkIntervalMs=60000 maxPerMinute=10000 (org.apache.zookeeper.server.ContainerManager)
```
* Start Kafka Server
```properties
PS C:\kafka\bin\windows> .\kafka-server-start.bat C:\kafka\config\server.properties
[2021-09-10 15:45:33,932] INFO [KafkaServer id=0] started (kafka.server.KafkaServer)
[2021-09-10 15:45:34,216] INFO [broker-0-to-controller-send-thread]: Recorded new controller, from now on will use broker anupama-sinha-PC:9092 (id: 0 rack: null) (kafka.server.BrokerToControllerRequestThread)
```

### Initial Command Testing
```properties
# List Topics
PS C:\kafka\bin\windows> .\kafka-topics.bat --list --zookeeper localhost:2181
#Describe Topics
PS C:\kafka\bin\windows> .\kafka-topics.bat --describe --zookeeper localhost:2181 --topic testtopic1
# List Consumer Groups
PS C:\kafka\bin\windows> .\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --list
# Describe Consumer Groups
PS C:\kafka\bin\windows> .\kafka-consumer-groups.bat --bootstrap-server localhost:9092 --group console-consumer-64929 --describe
# Start Consumer(Do this step before starting Producer so that Consumer registers with group coordinator first
PS C:\kafka\bin\windows> .\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic testtopic2
# Start Producer now and send messages
PS C:\kafka\bin\windows> .\kafka-console-producer.bat -broker-list localhost:9092 --topic testtopic2
# Create Consumer & read through Broker Server & read messages from beginning
PS C:\kafka\bin\windows> .\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic testtopic2 --from-beginning
# List Broker count & Broker ID
PS C:\kafka\bin\windows> .\zookeeper-shell.bat localhost:2181 ls /brokers/ids
# Delete Topics(Topics marked for deletion)
PS C:\kafka\bin\windows> .\kafka-run-class.bat kafka.admin.TopicCommand --delete --topic testtopic1 --zookeeper localhost:2181
# Create a  Topic
PS C:\kafka\bin\windows> .\kafka-topics.bat --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic test
# Send messages to Topic handled by Zookeeper
PS C:\kafka\bin\windows> .\kafka-console-producer.bat --broker-list localhost:9092  --topic test
# Create a Topic with Bootstrap Server
PS C:\kafka\bin\windows> .\kafka-topics.bat --create --replication-factor 1 --partitions 1 --topic testbroker --bootstrap-server localhost:9092
# Create a Publisher to send messages to above topic
PS C:\kafka\bin\windows> .\kafka-console-consumer.bat --topic testbroker --bootstrap-server localhost:9092 > ..\..\zookeeper-data\sample1.csv
```

## Common Troubleshooting Guidelines
* If all topics are deleted, then broker isn't available. So, stop Zookeeper & Kafka Server and add below property in server.properties & restart both
> delete.topic.enable=true

* cmd Line complaints long cmd size. Folder Length and kafka folder name has to be short

## Features
* Distributed, Resilient Architecture, fault tolerant streaming platform
* Horizontally scalable(Broker count, Messages per sec)
* Realtime performance

## Kafka Architecture
* Producer : Sends messages to Kafka Cluster
* Kafka Cluster : Collection of Brokers/Kafka Server having Zookeeper
* Consumers : Reads messages from Kafka Cluster
* DB Connectors : For DB connectivity
* Stream Processors : Processes streams
* Cluster : Group of computers
* Topic : Unique Kafka stream
* Partition : Part of topic(Sequence ID)
* Offset(Custom,Committed) : Unique ID for message within partition
* Consumer groups : Group of consumers acting as single logical unit(Parallel reading in same application, Only 1 partition owned by single consumer,Group coordinator assigns one consumer as Leader to rebalance partition)

## Zookeeper
* Open source from Hadoop to provide services for distributed system of Brokers

## Producer Workflow
* Properties(bootstrap.server,key.serializer,value.serializer,acks,retries,max.in.flight.requests.per.connection,etc)+Producer Record(Topic name,Parition Number,Timestamp,Key,Value) -> Serializer(Key,Value) -> Partitioner(Assigns Partition#/Round robin partitioning) -> Partition Buffer(Producer Record 1,2,etc) -> Kafka Cluster(Broker with one Leader Partition & other partitions) -> (on Success) ->Record Metadata(Offset,Timestamp)-> Producer

## Acknowledgement Types
* Fire & Forget(ack=0)
* Synchronous Send(ack=1,Leader sends response after saving messages,Retry possible,everything is configurable in properties)
* Asynchronous Send(inflight without acks)

## References
* https://kafka.apache.org/intro
* https://maestralsolutions.com/spring-boot-implementation-for-apache-kafka-with-kafka-tool/
* https://www.baeldung.com/spring-kafka
* https://dzone.com/articles/magic-of-kafka-with-spring-boot
* https://developers.redhat.com/blog/2020/05/19/extending-kafka-connectivity-with-apache-camel-kafka-connectors#summary
* https://github.com/LearningJournal/ApacheKafkaTutorials
