package com.example.people.data

import com.example.people.domain.PeopleRepository
import com.example.people.model.CreatePerson
import com.example.people.model.PaginatedData
import com.example.people.model.Person
import com.example.people.model.SinglePerson
import com.example.people.service.PeopleApi
import kotlinx.coroutines.delay
import javax.inject.Inject

class PeopleRepositoryImpl @Inject constructor(private val api: PeopleApi): PeopleRepository {
    override suspend fun getPeople(page: Int): PaginatedData<Person>? {
        return try {
            delay(1000)
             api.getPeople(page = page)
        } catch (e: Exception) {
            null
        }
    }

    override suspend fun getPerson(id: String): Resource<SinglePerson> {
        return try {
            delay(1000)
            val res = api.getPerson(id = id)
            Resource.Success(data = res)
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Something went wrong")
        }
    }

    override suspend fun createPerson(person: CreatePerson): Resource<Boolean> {
        return try {
            delay(1000)
            api.createPerson(person = person)
            Resource.Success(data = true)
        } catch (e: Exception) {
            Resource.Error(message = e.message ?: "Something went wrong")
        }
    }
}