package com.example.people.ui.view

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.people.MainActivity
import com.example.people.di.AppModule
import com.example.people.navigation.AppNavigation
import com.example.people.theme.PeopleTheme
import com.example.people.util.TestTags
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
@UninstallModules(AppModule::class)
class AppTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.activity.setContent {
            PeopleTheme {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    AppNavigation()
                }
            }
        }
    }

    @Test
    fun testAppFlow() {
        composeRule.onNodeWithText("People").assertExists()

        // Settings
        composeRule.onNodeWithTag(TestTags.settingsTab).performClick()
        composeRule.onNodeWithText("People").assertDoesNotExist()
        composeRule.onNodeWithText("Settings").assertExists()

        val switch = composeRule.onNodeWithTag(TestTags.themeSwitch)
        switch.assertIsToggleable().assertIsOff()
        composeRule.onNodeWithTag(TestTags.themeSwitch).performClick()
        switch.assertIsOn()
        composeRule.onNodeWithTag(TestTags.themeSwitch).performClick()
        switch.assertIsOff()

        composeRule.onNodeWithTag(TestTags.peopleTab).performClick()

        // Detail
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodesWithText("#1").fetchSemanticsNodes().size == 1
        }

        val firstPerson = composeRule.onAllNodesWithText("#1").onFirst()

        firstPerson.assertExists()
        firstPerson.performClick()
        composeRule.onNodeWithText("People").assertDoesNotExist()
        composeRule.onNodeWithText("Details").assertExists()
        composeRule.waitUntil(5_000) {
            composeRule.onAllNodesWithText("First Name").fetchSemanticsNodes().size == 1
        }
        composeRule.onNodeWithText("Last Name").assertExists()
        composeRule.onNodeWithText("Email").assertExists()
        composeRule.onNodeWithText("Support Reqres").assertExists()

        composeRule.activityRule.scenario.onActivity { activity ->
            activity.onBackPressedDispatcher.onBackPressed()
        }
        composeRule.onNodeWithText("People").assertExists()

        // Create
        composeRule.onNodeWithTag(TestTags.createBtn).performClick()
        composeRule.onNodeWithText("People").assertDoesNotExist()
        composeRule.onNodeWithText("Create").assertExists()

        composeRule.onNodeWithTag(TestTags.firstName).performTextInput("Ifeanyi")
        composeRule.onNodeWithTag(TestTags.lastName).performTextInput("Onuoha")
        composeRule.onNodeWithTag(TestTags.submitBtn).performClick()
        composeRule.onNodeWithText("Create").assertExists()
        composeRule.onNodeWithTag(TestTags.job).performTextInput("Developer")

        composeRule.onNodeWithTag(TestTags.firstName).assertTextEquals("Ifeanyi")
        composeRule.onNodeWithTag(TestTags.lastName).assertTextEquals("Onuoha")
        composeRule.onNodeWithTag(TestTags.job).assertTextEquals("Developer")

        composeRule.onNodeWithTag(TestTags.submitBtn).performClick()

        composeRule.waitUntil(5_000) {
            composeRule.onAllNodesWithText("People").fetchSemanticsNodes().size == 1
        }
    }


}