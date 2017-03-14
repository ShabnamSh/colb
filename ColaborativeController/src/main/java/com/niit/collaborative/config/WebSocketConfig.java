package com.niit.collaborative.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
@ComponentScan("com.niit")
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{

	private static final Logger logger = 
			LoggerFactory.getLogger(WebSocketConfig.class);
	 @Override
	  public void configureMessageBroker(MessageBrokerRegistry config) {
		logger.debug("Starting method configureMessageBroker");
	    config.enableSimpleBroker("/topic");
	    config.setApplicationDestinationPrefixes("/app");
	    logger.debug("Ending method configureMessageBroker");
	  }

	public void registerStompEndpoints(StompEndpointRegistry registry) {
		// TODO Auto-generated method stub
		logger.debug("Starting method registerStompEndpoints");
	    registry.addEndpoint("/chat").withSockJS();
	   
	    logger.debug("Ending method registerStompEndpoints");
		
	}

}
