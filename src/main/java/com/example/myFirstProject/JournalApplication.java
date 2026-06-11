package com.example.myFirstProject;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {
	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}
	@Bean
	public PlatformTransactionManager add(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
	@Bean
	public MongoClient mongoClient() {
		return MongoClients.create(
				"mongodb+srv://irishmjn_db_user:ZcW7g36TXsdJg5k8@cluster0.zebo8lr.mongodb.net/journaldb"
		);
	}
}
