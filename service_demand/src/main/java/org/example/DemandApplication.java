package org.example;

import io.vertx.core.Vertx;
import org.example.config.WebSocketVerticle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemandApplication {
    public static void main(String[] args) {
        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle(WebSocketVerticle.class.getName());
        SpringApplication.run(DemandApplication.class, args);
    }
}
