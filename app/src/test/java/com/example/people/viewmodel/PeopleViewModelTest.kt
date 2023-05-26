package com.example.people.viewmodel

import com.example.people.data.Resource
import com.example.people.domain.PeopleRepository
import com.example.people.fixtures.createPersonFixture
import com.example.people.fixtures.singlePersonFixture
import com.example.people.mock.Mocks
import com.example.people.model.CreatePerson
import com.example.people.rule.MainDispatcherRule
import com.example.people.ui.viewmodel.PeopleState
import com.example.people.ui.viewmodel.PeopleViewModel
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.whenever

class PeopleViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var peopleRepository: PeopleRepository
    private lateinit var peopleViewModel: PeopleViewModel

    @Before
    fun setUp() {
        peopleRepository = Mocks.mockPeopleRepository
        peopleViewModel = PeopleViewModel(peopleRepository = peopleRepository)
    }

    @Test
    fun `Get person success case`() = runTest {
        whenever(peopleRepository.getPerson(id = any())).thenReturn(
            Resource.Success(data = singlePersonFixture)
        )

        peopleViewModel.getPerson(id = "id")

        assertNull(peopleViewModel.personState.value.message)
        assertNotNull(peopleViewModel.personState.value.data)
        assertTrue(peopleViewModel.personState.value.data == singlePersonFixture)
    }

    @Test
    fun `Get person error case`() = runTest {
        val errorMsg = "Failed to get person"
        whenever(peopleRepository.getPerson(id = any())).thenReturn(
            Resource.Error(message = errorMsg)
        )

        peopleViewModel.getPerson(id = "id")

        assertNull(peopleViewModel.personState.value.data)
        assertNotNull(peopleViewModel.personState.value.message)
        assertTrue(peopleViewModel.personState.value.message == errorMsg)
    }

    @Test
    fun `Create person success case`() = runTest {
        whenever(peopleRepository.createPerson(person = any())).thenReturn(
            Resource.Success(data = true)
        )

        assertTrue(peopleViewModel.createState.value == PeopleState.Idle)

        peopleViewModel.createPerson(person = createPersonFixture)

        assertTrue(peopleViewModel.createState.value == PeopleState.CreatingPersonSuccess)
    }

    @Test
    fun `Create person error case`() = runTest {
        whenever(peopleRepository.createPerson(person = any())).thenReturn(
            Resource.Error(message = "Failed")
        )

        assertTrue(peopleViewModel.createState.value == PeopleState.Idle)

        peopleViewModel.createPerson(person = createPersonFixture)

        assertTrue(peopleViewModel.createState.value == PeopleState.Idle)
    }


    @Test
    fun `Validate new person`() = runTest {
        val newPerson1 = CreatePerson(firstName = "first", lastName = "last", job = "job")

        assertTrue(peopleViewModel.validateNewPerson(newPerson1))

        val newPerson2 = CreatePerson(firstName = "first", lastName = "last", job = "")

        assertFalse(peopleViewModel.validateNewPerson(newPerson2))
    }
}