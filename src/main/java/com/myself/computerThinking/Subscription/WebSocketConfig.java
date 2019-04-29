package com.myself.computerThinking.Subscription;

import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Component
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        /**
         * 用户可以订阅来自“/topic，/user”为前缀的消息
         * 客户端只可以订阅这个前缀的主题
         */
        registry.enableSimpleBroker("/topic");

        /**
         * 客户端发送过来的消息，需要以“/app”为前缀，再通过Broker转发给响应的Controller
         */
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {

        /**
         * 路径："websocket/socketServer.action"被注册为STOMP端点，对外暴露，客户端通过该路径接入webScoket服务
         */
       registry.addEndpoint("websocket/socketServer.action")
       .setAllowedOrigins("*")  //设置可以跨域请求
       .withSockJS();           // 添加SockJS支持       http

    }


}
