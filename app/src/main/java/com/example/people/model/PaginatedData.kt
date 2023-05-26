package com.example.people.model

import com.google.gson.annotations.SerializedName

data class PaginatedData<T>(
    val data: List<T>,
    val page: Int,
    @SerializedName(value = "per_page")
    val perPage: Int,
    val support: Support,
    val total: Int,
    @SerializedName(value = "total_pages")
    val totalPages: Int
)