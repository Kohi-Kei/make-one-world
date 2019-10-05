package com.example.make.one.world.socket
import com.example.make.one.world.socket.ChatHandler
import org.springframework.context.annotation.Configuration
import org.springframework.web.socket.*
import org.springframework.web.socket.config.annotation.EnableWebSocket
import org.springframework.web.socket.config.annotation.WebSocketConfigurer
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry
import javax.annotation.PostConstruct

@Configuration
@EnableWebSocket
class WebSocketConfig: WebSocketConfigurer{
	override fun registerWebSocketHandlers(registory: WebSocketHandlerRegistry){
		registory.addHandler(ChatHandler(), "/chat").withSockJS()
	}
	
	@PostConstruct
	fun saySomething(){
		println("Websoccccceeeeeet")
		
	}
}