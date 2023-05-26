package com.example.people.service

import com.example.people.model.CreatePerson
import com.example.people.model.PaginatedData
import com.example.people.model.Person
import com.example.people.model.SinglePerson
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface PeopleApi {
    @GET(value = "api/users")
    suspend fun getPeople(@Query(value = "page") page: Int): PaginatedData<Person>

    @GET(value = "api/users/{id}")
    suspend fun getPerson(@Path(value = "id") id: String): SinglePerson

    @POST(value = "api/users")
    suspend fun createPerson(@Body person: CreatePerson)
}