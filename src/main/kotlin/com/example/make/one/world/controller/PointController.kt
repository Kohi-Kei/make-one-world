package com.example.make.one.world.controller

import org.springframework.web.bind.annotation.RestController
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.http.HttpStatus
import com.example.make.one.world.repository.PointRepository
import com.example.make.one.world.model.Point


@RestController
@RequestMapping("v1/points")
class PointController(val pointRepository: PointRepository) {
	
	@GetMapping
	@ResponseStatus(HttpStatus.ACCEPTED)
	fun getCurrentAiiPoints(): List<Point?> = pointRepository.findAll().toList()
	
}