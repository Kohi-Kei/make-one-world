package com.example.make.one.world.socket

import org.springframework.web.socket.handler.TextWebSocketHandler
import org.springframework.web.socket.WebSocketSession
import java.util.concurrent.atomic.AtomicLong
import org.springframework.web.socket.TextMessage
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.web.socket.CloseStatus
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import com.example.make.one.world.repository.PointRepository
import javax.annotation.PostConstruct
import com.example.make.one.world.model.Point
import com.example.make.one.world.model.jsonNodetoPoint
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import com.example.make.one.world.bridge.appliaction.ApplicationBridge

class User(val id: Long, val name: String)
class Message(val msgType: String, val data: Any)

//@Component
class ChatHandler : TextWebSocketHandler() {

	val sessionList = HashMap<WebSocketSession, User>()
	var uids = AtomicLong(0)
	val pointRepository = ApplicationBridge.getApplicationBridge()!!.fetchPointRepository()
	

	public override fun handleTextMessage(session: WebSocketSession?, message: TextMessage?) {
		val json = ObjectMapper().readTree(message?.payload)
		when (json.get("type").asText()) {
			"join" -> {
				val user = User(uids.getAndIncrement(), json.get("data").asText())
				sessionList.put(session!!, user)

				emit(session, Message("users", sessionList.values))

				broadcastToOthers(session, Message("join", user))
			}
			"put" -> {
				broadcast(Message("put", json.get("data")))
				val pointNode = json.get("data")

				
				println(pointRepository.toString())
				pointRepository.save(jsonNodetoPoint(pointNode))
				println(json.get("data"))
			}
			"delete" -> {
				broadcast(Message("delete", json.get("data")))
			}
		}

	}

	@Throws(Exception::class)
	override fun afterConnectionClosed(session: WebSocketSession, status: CloseStatus) {
		sessionList -= session
	}

	fun emit(session: WebSocketSession, msg: Message) {
		session.sendMessage(TextMessage(jacksonObjectMapper().writeValueAsString(msg)))
	}

	fun broadcast(msg: Message) = sessionList.forEach { emit(it.key, msg) }

	fun broadcastToOthers(me: WebSocketSession, msg: Message) {
		sessionList.filterNot { it.key == me }.forEach { emit(it.key, msg) }
	}


}