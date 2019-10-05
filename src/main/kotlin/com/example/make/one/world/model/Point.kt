package com.example.make.one.world.model

import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import com.fasterxml.jackson.databind.JsonNode

@RedisHash("Points")
data class Point(
	@Indexed val id: String,
	val x: Int,
	val y: Int,
	val z: Int,
	val voxelId: Int
) {

}

fun jsonNodetoPoint(pointNode: JsonNode): Point {
	val x = pointNode.get("x").asInt()
	val y = pointNode.get("y").asInt()
	val z = pointNode.get("z").asInt()
	val voxelId = pointNode.get("voxelId").asInt()
	val id = Integer.toString(x) + Integer.toString(y) + Integer.toString(z)
	return Point(id, x, y, z, voxelId)
}