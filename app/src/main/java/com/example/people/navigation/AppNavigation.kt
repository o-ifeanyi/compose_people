package com.example.people.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.people.ui.view.*

@Composable
fun AppNavigation() {
    val navigator = rememberNavController()
    NavHost(navController = navigator, startDestination = AppScreens.IndexScreen.name) {
        composable(AppScreens.IndexScreen.name) {
            IndexScreen(navigator = navigator)
        }
        val detailRoute = "${AppScreens.DetailScreen.name}/{id}"
        composable(detailRoute) {
            val id = it.arguments?.getString("id")
            DetailScreen(id = id ?: "")
        }
        composable(AppScreens.CreateScreen.name) {
            CreateScreen(navigator = navigator)
        }
    }
}