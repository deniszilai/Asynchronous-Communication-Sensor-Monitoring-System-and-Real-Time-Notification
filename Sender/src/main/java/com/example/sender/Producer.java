package com.example.sender;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Timestamp;
import java.time.Instant;

public class Producer {
    private static int device = 0;

    public static void send() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("kangaroo-01.rmq.cloudamqp.com");
        factory.setUsername("bomlweei");
        factory.setVirtualHost("bomlweei");
        factory.setPassword("WJl15B7ZbocaZGp0s3bh4PQ8_hheC_X6");
        factory.setPort(5672);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("the_queue", true, false, false, null);

        BufferedReader csv = new BufferedReader(new FileReader("D:/Sender/src/sensor.csv"));
        String value = "";
        boolean ok = false;
       while ((value = csv.readLine()) != null && !ok) {
            int id = device % 2 == 0 ? 1 : 2;
            device++;

            String message = Timestamp.from(Instant.now()) + "," + id + "," + value;

            channel.basicPublish("", "the_queue", null, message.getBytes());
            System.out.println("Sent the message: " + message);
            Thread.sleep(500);
            if(device == 10)
                ok = true;
        }
    }
}
