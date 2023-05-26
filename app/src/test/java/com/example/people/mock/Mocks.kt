package com.example.people.mock

import com.example.people.domain.PeopleRepository
import com.example.people.service.PeopleApi
import org.mockito.kotlin.mock

object Mocks {
     val mockPeopleApi: PeopleApi = mock()
     val mockPeopleRepository: PeopleRepository = mock()
}
