package com.example.testallthings.network.model.profile

data class ProfileResponse(
    val cart_amount: Int,
    val cart_count: Int,
    val cart_total: Double,
    val `data`: Data,
    val message: String,
    val notifications: Int,
    val status: String,
    val user: User
)