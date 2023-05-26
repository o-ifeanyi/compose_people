package com.example.people.ui.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.people.model.CreatePerson
import com.example.people.navigation.AppScreens
import com.example.people.ui.components.InputField
import com.example.people.ui.viewmodel.PeopleState
import com.example.people.ui.viewmodel.PeopleViewModel
import com.example.people.util.TestTags
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateScreen(navigator: NavController, viewModel: PeopleViewModel = hiltViewModel()) {
    val firstName = remember { mutableStateOf("") }
    val lastName = remember { mutableStateOf("") }
    val job = remember { mutableStateOf("") }

    val context = LocalContext.current

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = { Text(text = "Create") },
            )
        },
    ) {
        Box(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding(),
                start = 15.dp, end = 15.dp
            ),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background,
                    shape = MaterialTheme.shapes.medium
                ) {
                    Column(modifier = Modifier.padding(horizontal = 10.dp)) {
                        InputField(input = firstName, label = "First Name")
                        Divider()
                        InputField(input = lastName, label = "Last Name")
                        Divider()
                        InputField(input = job, label = "Job")
                    }
                }

                Button(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
                    .testTag(TestTags.submitBtn),
                    onClick = {
                        val person = CreatePerson(
                            firstName = firstName.value,
                            lastName = lastName.value,
                            job = job.value
                        )
                        val isValid = viewModel.validateNewPerson(person)
                        if (!isValid) return@Button
                        println(person)
                        viewModel.createPerson(person = person)
                    }) {
                    Text(text = "Submit")
                }
            }
            when (viewModel.createState.value) {
                PeopleState.CreatingPerson -> {
                    AlertDialog(modifier = Modifier
                        .size(120.dp)
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = MaterialTheme.shapes.medium
                        ), onDismissRequest = { /*TODO*/ }) {
                        CircularProgressIndicator(
                            modifier = Modifier
                                .padding(40.dp)
                        )
                    }
                }
                PeopleState.CreatingPersonSuccess -> {
                    LaunchedEffect(key1 = Unit) {
                        delay(500)
                        Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                        firstName.value = ""
                        lastName.value = ""
                        job.value = ""
                        navigator.popBackStack(AppScreens.IndexScreen.name, false)
                    }

                }
                else -> {}
            }
        }
    }
}