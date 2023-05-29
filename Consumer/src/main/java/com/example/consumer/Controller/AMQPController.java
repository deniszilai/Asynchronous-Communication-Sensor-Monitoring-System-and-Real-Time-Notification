package com.example.consumer.Controller;

import com.example.consumer.Configuration.SocketTextHandler;
import com.example.consumer.Consumer.Consumer;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
@Controller
public class AMQPController implements WebSocketConfigurer {
    @MessageMapping("/rabbitmq")
    @SendTo("/topic/sensors")
    public Consumer consumer() throws Exception {
        Consumer consumer=new Consumer();
        consumer.consume();
        return consumer;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new SocketTextHandler(),"/goodE");
    }

//    @Autowired
//    private SimpMessagingTemplate template;

//    @RequestMapping(path="/messages",method= RequestMethod.POST)
//    public  @ResponseBody
//    void msg() {
//        this.template.convertAndSend("/topic/sensors", "message");
//    }
//
//    @MessageMapping("/rabbitmq")
//    @SendTo("/topic/sensors")
//    public void fct(String abc){
//        this.template.convertAndSend("/topic/sensors",abc);
//    }

}
