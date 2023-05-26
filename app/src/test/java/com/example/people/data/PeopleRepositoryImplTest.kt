package com.example.people.data

import com.example.people.fixtures.*
import com.example.people.mock.Mocks
import com.example.people.service.PeopleApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class PeopleRepositoryImplTest {

    private lateinit var mockApi: PeopleApi
    private lateinit var peopleRepositoryImpl: PeopleRepositoryImpl

    @Before
    fun setUp() {
        mockApi = Mocks.mockPeopleApi
        peopleRepositoryImpl = PeopleRepositoryImpl(api = mockApi)
    }

    @Test
    fun `Get people success case`() = runTest {
        whenever(mockApi.getPeople(page = any())).thenReturn(
            paginatedPersonFixture
        )

        val res = peopleRepositoryImpl.getPeople(page = 1)

        assertNotNull(res)
        assertTrue(res?.page == 1)
        assertTrue(res?.totalPages == 2)
        assertTrue(res?.perPage == 10)
        assertTrue(res?.total == 20)
        assertTrue(res?.data?.size == 2)

        assertEquals(personFixture, res?.data?.first())
    }

    @Test
    fun `Get people error case`() = runTest {
        whenever(mockApi.getPeople(page = any())).thenThrow()

        val res = peopleRepositoryImpl.getPeople(page = 1)

        assertNull(res)
    }

    @Test
    fun `Get single person success case`() = runTest {
        whenever(mockApi.getPerson(id = any())).thenReturn(
            singlePersonFixture
        )

        val res = peopleRepositoryImpl.getPerson(id = "")

        assertNull(res.message)
        assertNotNull(res.data)
        assertTrue(res.data?.support == supportFixture)
        assertTrue(res.data?.data == personFixture)
    }

    @Test
    fun `Get single person error case`() = runTest {
        whenever(mockApi.getPerson(id = any())).thenThrow()

        val res = peopleRepositoryImpl.getPerson(id = "")

        assertNull(res.data)
        assertNotNull(res.message)
    }

    @Test
    fun `Create person success case`() = runTest {
        whenever(mockApi.createPerson(person = any())).thenReturn(Unit)

        val res = peopleRepositoryImpl.createPerson(person = createPersonFixture)

        assertNull(res.message)
        assertNotNull(res.data)
        assertTrue(res.data == true)
    }

    @Test
    fun `Create person error case`() = runTest {
        whenever(mockApi.createPerson(person = any())).thenThrow()

        val res = peopleRepositoryImpl.createPerson(person = createPersonFixture)

        assertNull(res.data)
        assertNotNull(res.message)
    }
}