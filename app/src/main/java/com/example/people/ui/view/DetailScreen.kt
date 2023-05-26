package com.example.people.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.people.ui.components.PillItem
import com.example.people.ui.viewmodel.PeopleState
import com.example.people.ui.viewmodel.PeopleViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(id: String, viewModel: PeopleViewModel = hiltViewModel()) {
    LaunchedEffect(key1 = Unit) {
        viewModel.getPerson(id = id)
    }

    val state = viewModel.peopleState.value
    val person = viewModel.personState.value


    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = { Text(text = "Details") },
                actions = {}
            )
        }
    ) {
        when (state) {
            PeopleState.GettingPerson -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            else -> {
                if (person.data != null) {
                    val user = person.data.data
                    LazyColumn(
                        modifier = Modifier.padding(
                            top = it.calculateTopPadding() + 10.dp,
                            start = 15.dp, end = 15.dp, bottom = 80.dp
                        ),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        item {
                            AsyncImage(
                                model = user.avatar,
                                contentDescription = "Person Image",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .height(250.dp)
                                    .fillMaxWidth()
                                    .clip(shape = MaterialTheme.shapes.medium)
                            )
                        }
                        item {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Column(
                                    modifier = Modifier.padding(10.dp),
                                    verticalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    PillItem(text = "#${user.id}")
                                    Text(
                                        text = "First Name",
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                    Text(
                                        text = user.firstName,
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Divider()

                                    Text(
                                        text = "Last Name",
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                    Text(
                                        text = user.lastName,
                                        style = MaterialTheme.typography.bodyMedium
                                    )

                                    Divider()

                                    Text(
                                        text = "Email",
                                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.SemiBold)
                                    )
                                    Text(
                                        text = user.email,
                                        style = MaterialTheme.typography.bodyMedium
                                    )
                                }
                            }
                        }
                        item {
                            Surface(
                                modifier = Modifier.fillMaxWidth(),
                                color = MaterialTheme.colorScheme.background,
                                shape = MaterialTheme.shapes.medium
                            ) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(
                                        modifier = Modifier.padding(10.dp),
                                        verticalArrangement = Arrangement.spacedBy(5.dp)
                                    ) {
                                        Text(
                                            text = "Support Reqres",
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                fontWeight = FontWeight.SemiBold
                                            )
                                        )
                                        Text(
                                            text = person.data.support.url,
                                            style = MaterialTheme.typography.bodyMedium
                                        )
                                    }

                                    IconButton(onClick = { /*TODO*/ }) {
                                        Icon(
                                            imageVector = Icons.Default.MailOutline,
                                            contentDescription = "Support"
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}