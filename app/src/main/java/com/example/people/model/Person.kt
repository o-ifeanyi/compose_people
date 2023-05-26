package com.example.people.model

import com.google.gson.annotations.SerializedName

data class Person(
    val avatar: String,
    val email: String,
    @SerializedName(value = "first_name")
    val firstName: String,
    val id: Int,
    @SerializedName(value = "last_name")
    val lastName: String
)