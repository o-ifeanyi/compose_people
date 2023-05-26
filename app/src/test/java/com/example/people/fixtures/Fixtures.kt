package com.example.people.fixtures

import com.example.people.model.*



val personFixture = Person(
    id = 1,
    firstName = "first name",
    lastName = "last name",
    email = "email",
    avatar = "avatar"
)

val supportFixture = Support(text = "text", url = "url")

val paginatedPersonFixture = PaginatedData(
    page = 1,
    perPage = 10,
    total = 20,
    totalPages = 2,
    support = supportFixture,
    data = listOf(personFixture, personFixture)
)

val singlePersonFixture = SinglePerson(
    data = personFixture,
    support = supportFixture,
)

val createPersonFixture =
    CreatePerson(firstName = " first name", lastName = "last name", job = "job")

