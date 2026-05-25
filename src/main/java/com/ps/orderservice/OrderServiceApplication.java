package com.ps.orderservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class OrderServiceApplication {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void logUsefulUrls() {
        log.info("Swagger UI available at: http://localhost:8080/swagger-ui.html");
        log.info("H2 Console available at: http://localhost:8080/h2-console");
    }

}
