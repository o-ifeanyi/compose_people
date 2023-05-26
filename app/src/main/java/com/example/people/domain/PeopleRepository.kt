package com.example.people.domain

import com.example.people.data.Resource
import com.example.people.model.CreatePerson
import com.example.people.model.PaginatedData
import com.example.people.model.Person
import com.example.people.model.SinglePerson

interface PeopleRepository {
    suspend fun getPeople(page: Int): PaginatedData<Person>?

    suspend fun getPerson(id: String): Resource<SinglePerson>

    suspend fun createPerson(person: CreatePerson): Resource<Boolean>
}