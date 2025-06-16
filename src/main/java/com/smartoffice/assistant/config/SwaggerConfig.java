// src/main/java/com/smartoffice/backend/config/SwaggerConfig.java
package com.smartoffice.assistant.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI smartOfficeOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Smart Office API")
                        .description("API documentation for Smart Office Application")
                        .version("v1.0")
                        .contact(new Contact()
                                .name("Developer Support")
                                .email("support@smartoffice.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0")));
    }
}