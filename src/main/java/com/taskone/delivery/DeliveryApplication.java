package com.taskone.delivery;

import com.taskone.delivery.client.Client;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DeliveryApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DeliveryApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		// Server is now running, you can start the client
		Client client = new Client();
		client.userMenu();  // Call the method to perform the POST request
	}
}
