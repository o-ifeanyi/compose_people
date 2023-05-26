package com.example.people.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.people.navigation.AppScreens
import com.example.people.ui.components.PersonItem
import com.example.people.ui.viewmodel.PeopleViewModel

@Composable
fun PeopleScreen(navigator: NavController, viewModel: PeopleViewModel = hiltViewModel()) {

    val people = viewModel.people.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(count = 2),

        ) {
        items(people.itemSnapshotList) { person ->
            person?.let {
                PersonItem(person = person) {
                    navigator.navigate("${AppScreens.DetailScreen.name}/${person.id}")
                }
            }
        }
        fun manageState(state: LoadState) {
            when(state) {
                is LoadState.Loading -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        LinearProgressIndicator(modifier = Modifier.padding(vertical = 6.dp))
                    }
                }
                is LoadState.Error -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Text(text = "An error occurred", color = MaterialTheme.colorScheme.onSurface)
                    }
                }
                else -> {
                    item(span = { GridItemSpan(maxLineSpan) }) {
                        Box {}
                    }
                }
            }
        }
        manageState(people.loadState.refresh)
        manageState(people.loadState.append)
    }
}
