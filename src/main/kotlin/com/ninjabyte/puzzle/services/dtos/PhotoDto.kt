package com.ninjabyte.puzzle.services.dtos

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

class PhotoDto:ArrayList<UnSplashResponse>()

@JsonIgnoreProperties(ignoreUnknown = true)
data class UnSplashResponse(
    val id: String,
    @JsonProperty("created_at")
    val createdAt: String,
    @JsonProperty("updated_at")
    val updatedAt: String,
    @JsonProperty("promoted_at")
    val promotedAt: String,
    val width: Long,
    val height: Long,
    val color: String,
    @JsonProperty("blur_hash")
    val blurHash: String,
    val description: Any?,
    @JsonProperty("alt_description")
    val altDescription: String,
    val likes: Long,
    @JsonProperty("asset_type")
    val assetType: String,
    val user: User,
    val views: Long,
    val urls: HashMap<String,String>
)

@JsonIgnoreProperties(ignoreUnknown = true)
data class User(
    val id: String,
    @JsonProperty("updated_at")
    val updatedAt: String,
    val username: String,
    val name: String,
    @JsonProperty("first_name")
    val firstName: String,
    @JsonProperty("last_name")
    val lastName: String,
)



