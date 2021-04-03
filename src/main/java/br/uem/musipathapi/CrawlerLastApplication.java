package br.uem.musipathapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CrawlerLastApplication implements CommandLineRunner{

	public static void main(String[] args) {

		SpringApplication.run(CrawlerLastApplication.class, args);		

	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Musipath API iniciada!");
	}

}
