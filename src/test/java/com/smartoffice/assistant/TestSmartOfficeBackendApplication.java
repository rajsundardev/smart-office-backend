package com.smartoffice.assistant;

import org.springframework.boot.SpringApplication;

public class TestSmartOfficeBackendApplication {

	public static void main(String[] args) {
		SpringApplication.from(SmartOfficeBackendApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
