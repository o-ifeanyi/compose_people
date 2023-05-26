package com.example.people.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ListItem
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.people.ui.viewmodel.SettingsViewModel
import com.example.people.util.TestTags

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    Column {
        ListItem(
            headlineContent = { Text(text = "Dark Mode") },
            supportingContent = { Text(text = "Change the look of the app") },
            trailingContent = {
                Switch(
                    modifier = Modifier.testTag(TestTags.themeSwitch),
                    checked = viewModel.dark.value,
                    onCheckedChange = { newVal ->
                        viewModel.setDark(newVal)
                    },
                )
            }
        )
    }
}