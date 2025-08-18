# 📘 Project Title

This project is a very short POC for producing and consuming messages using Kafka.

---

## 🛠️ Technologies Used

- Java 17+
- Maven 3.8+
- Spring Boot 3.5.4
- Kafka 

---

## 🚀 Getting Started

### Prerequisites

- Java JDK 17 installed
- Maven installed
- Kafka

### Build the Project

```bash
mvn clean install
```

### Run the Application

1. Run Zookeeper with downloaded Kafka package.
`bin\windows\zookeeper-server-start.bat config\zookeeper.properties`
2. Run Kafka Server.
`bin\windows\kafka-server-start.bat config\server.properties`
3. Configure application.properties according to you configurations. 
3. Run /deliveryboy-kafka/src/main/java/com/devileryboy/DeliveryboyKafkaApplication.java
4. Run /end-user/src/main/java/com/enduser/EndUserApplication.java
5. Use Postman to produce message using POST request i.e `http://localhost:8080/location/update`
6. Consumed messages will be appear on IDE console or use CMD with command: `bin\windows\kafka-console-consumer.bat --topic {your-topic-name} --from-beginning --bootstrap-server localhost:9092`

---

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

---

## 📌 Version

Current version: `1.0.0`
