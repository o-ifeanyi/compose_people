package com.example.people.ui.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.people.data.PagingResource
import com.example.people.data.Resource
import com.example.people.domain.PeopleRepository
import com.example.people.model.CreatePerson
import com.example.people.model.SinglePerson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

enum class PeopleState {
    Idle,
    GettingPerson,
    CreatingPerson,
    CreatingPersonSuccess,
}

data class PeopleData<T>(val data: T? = null, val message: String? = null)

@HiltViewModel
class PeopleViewModel @Inject constructor(private val peopleRepository: PeopleRepository) :
    ViewModel() {

    private val _state = mutableStateOf(PeopleState.Idle)
    val peopleState = _state

    private val _person = mutableStateOf(PeopleData<SinglePerson>())
    val personState = _person

    private val _create = mutableStateOf(PeopleState.Idle)
    val createState = _create

    val people = Pager(config = PagingConfig(pageSize = 10)) {
        PagingResource {
            peopleRepository.getPeople(it)
        }
    }.flow.cachedIn(viewModelScope)

    fun getPerson(id: String) {
        _state.value = PeopleState.GettingPerson
        viewModelScope.launch {
            val res = peopleRepository.getPerson(id)
            _person.value = when (res) {
                is Resource.Success -> _person.value.copy(data = res.data)
                is Resource.Error -> _person.value.copy(message = res.message)
            }
            _state.value = PeopleState.Idle
        }
    }

    fun createPerson(person: CreatePerson) {
        _create.value = PeopleState.CreatingPerson
        viewModelScope.launch {
            when (peopleRepository.createPerson(person)) {
                is Resource.Success -> _create.value = PeopleState.CreatingPersonSuccess
                is Resource.Error -> _create.value = PeopleState.Idle
            }
        }
    }

    fun validateNewPerson(n: CreatePerson): Boolean {
        return n.firstName.trim().isNotEmpty() && n.lastName.trim().isNotEmpty() && n.job.trim()
            .isNotEmpty()
    }
}