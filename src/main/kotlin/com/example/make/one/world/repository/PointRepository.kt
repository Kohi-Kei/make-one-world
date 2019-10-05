package com.example.make.one.world.repository

import org.springframework.data.repository.CrudRepository
import com.example.make.one.world.model.Point
import org.springframework.stereotype.Repository

@Repository
interface PointRepository : CrudRepository<Point, String>