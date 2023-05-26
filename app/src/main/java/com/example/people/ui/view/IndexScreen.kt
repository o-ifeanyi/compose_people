package com.example.people.ui.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.people.navigation.AppScreens
import com.example.people.navigation.bottomNavItems
import com.example.people.util.TestTags

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IndexScreen(navigator: NavController) {
    val index = remember { mutableStateOf(0) }
    val pageHeaders = listOf("People", "Settings")

    Scaffold(
        containerColor = MaterialTheme.colorScheme.surface,
        topBar = {
            TopAppBar(
                title = { Text(text = pageHeaders[index.value]) },
            )
        },
        floatingActionButton = {
            if (index.value == 0) {
                FloatingActionButton(
                    onClick = { navigator.navigate(AppScreens.CreateScreen.name) },
                    modifier = Modifier.testTag(TestTags.createBtn)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Create Button"
                    )
                }
            }
        },
        bottomBar = {
            NavigationBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        selected = index.value == item.index,
                        icon = item.icon,
                        onClick = { index.value = item.index },
                        modifier = Modifier.testTag(item.testTag)
                    )
                }
            }
        }
    ) {
        Box(
            modifier = Modifier.padding(
                top = it.calculateTopPadding(),
                bottom = it.calculateBottomPadding(),
                start = 15.dp, end = 15.dp
            ),
        ) {
            when (index.value) {
                0 -> PeopleScreen(navigator = navigator)
                1 -> SettingsScreen()
                else -> Box {}
            }
        }
    }
}