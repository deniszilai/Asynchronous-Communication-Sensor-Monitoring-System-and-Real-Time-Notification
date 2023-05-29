package com.example.consumer;

import com.example.consumer.Consumer.Consumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
        try {
            Consumer.consume();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
