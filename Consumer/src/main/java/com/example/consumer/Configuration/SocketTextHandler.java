package com.example.consumer.Configuration;

import com.example.consumer.Consumer.Consumer;
import com.example.consumer.Data.SensorData;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import java.io.IOException;

@Component
public class SocketTextHandler extends TextWebSocketHandler {

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws InterruptedException, IOException {

        JSONObject payload = new JSONObject(message.getPayload());
        if (payload.getBoolean("isConsuming")) {
            if (Consumer.sensorDataList.size() != 0) {
                session.sendMessage(new TextMessage(Consumer.sensorDataList
                        .stream().map(SensorData::toString).reduce("", (a, b) -> a += b)));
            } else {
                session.sendMessage(new TextMessage("No message consumed with the specified maximum value!"));
            }

        }
    }
}
//import org.springframework.messaging.simp.config.MessageBrokerRegistry;
//import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
//import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
//import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;
//
//@Configuration
//@EnableWebSocketMessageBroker
//public class WebSocketConfig1 implements WebSocketMessageBrokerConfigurer {
//
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/rabbitmq").withSockJS();
//    }
//}