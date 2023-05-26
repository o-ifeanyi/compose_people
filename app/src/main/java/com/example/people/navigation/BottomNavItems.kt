package com.example.people.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import com.example.people.util.TestTags

sealed class BottomNavItem(
    val testTag: String,
    val index: Int,
    val icon: @Composable () -> Unit
) {
    object People : BottomNavItem(
        testTag = TestTags.peopleTab,
        index = 0,
        icon = {
            Icon(imageVector = Icons.Default.Person, contentDescription = "People Tab")
        }
    )

    object Settings : BottomNavItem(
        testTag = TestTags.settingsTab,
        index = 1,
        icon = {
            Icon(imageVector = Icons.Default.Settings, contentDescription = "Settings Tab")
        }
    )
}

val bottomNavItems = listOf(
    BottomNavItem.People,
    BottomNavItem.Settings
)