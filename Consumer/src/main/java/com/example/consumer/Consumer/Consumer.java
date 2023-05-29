package com.example.consumer.Consumer;

import com.example.consumer.Data.SensorData;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.util.ArrayList;
import java.util.List;

public class Consumer {

    public static List<SensorData> sensorDataList = new ArrayList<>();

    public static void consume() throws Exception{
        final int MAXC = 5;
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("kangaroo-01.rmq.cloudamqp.com");
        factory.setUsername("bomlweei");
        factory.setVirtualHost("bomlweei");
        factory.setPassword("WJl15B7ZbocaZGp0s3bh4PQ8_hheC_X6");
        factory.setPort(5672);

        boolean durable = true;

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare("the_queue", durable, false, false, null);
        System.out.println(
                " [*] Waiting for messages. To exit press CTRL+C");
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");

            String t = message.split(",")[0];
            String i = message.split(",")[1];
            String v = message.split(",")[2];

            if(Double.parseDouble(v) >= MAXC){
                sensorDataList.add(new SensorData(t,i,v));
                System.out.println("EXCEEDED!");
            }

            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume("the_queue", true, deliverCallback,
                consumerTag -> {
                });
    }
}
