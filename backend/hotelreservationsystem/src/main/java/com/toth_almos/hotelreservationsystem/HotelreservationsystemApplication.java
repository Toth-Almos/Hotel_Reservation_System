package com.toth_almos.hotelreservationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.toth_almos.hotelreservationsystem.mapper")
public class HotelreservationsystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelreservationsystemApplication.class, args);
	}

}
