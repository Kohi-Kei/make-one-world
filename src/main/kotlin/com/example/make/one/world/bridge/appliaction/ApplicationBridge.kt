package com.example.make.one.world.bridge.appliaction

import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component
import org.springframework.beans.factory.annotation.Autowired
import com.example.make.one.world.repository.PointRepository
import org.springframework.context.ApplicationContext


@Component
class ApplicationBridge(val applicationContext: ApplicationContext){
	
	
	@Autowired
	lateinit var pointRepository: PointRepository
	
	
	fun fetchPointRepository(): PointRepository{
		return pointRepository
	}
	
	init{
		instance = this
	}
	
	companion object {
		private var instance:ApplicationBridge? = null
		
		fun getApplicationBridge(): ApplicationBridge? {
			return instance?.applicationContext?.getBean(ApplicationBridge::class.java)
		}
    }
	
}