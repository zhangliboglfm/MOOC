package com.myself.WebScoketDemo.Subscription;

import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class subController {

    @RequestMapping(value = "/stomp")
    public String getStomp(){
        return "stomp";
    }

    /**
     *   接收前端发送消息： stompClient.send("/app/welcome", {atytopic:"greetings"}, "123");
     *   并广播出去，广播地址为：/topic/CCC
     * @param message
     * @param topic
     * @param headers
     * @return
     */
    @MessageMapping("/welcome")
    @SendTo("/topic/CCC")
    public String sendMessage(String message, @Header("atytopic")String topic, @Headers Map<String,Object> headers) {
        System.out.println(topic);
        System.out.println(headers);
        System.out.println(message);
        return message;
    }
}
