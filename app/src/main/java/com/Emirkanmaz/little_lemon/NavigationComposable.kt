package com.Emirkanmaz.little_lemon

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking

@Composable
fun Navigation(navController: NavHostController, database: MenuItemDao.AppDatabase) {
    NavHost(
        navController = navController,
        startDestination = if (hasUserData()) Home.route else Onboarding.route
    ) {
        composable(Onboarding.route) {
            Onboarding(navController)
        }
        composable(Home.route) {
            Home(navController, database)
        }
        composable(Profile.route) {
            Profile(navController)
        }
    }
}

@Composable
fun hasUserData(): Boolean {
    val context = LocalContext.current

    val userDataAvailable = runBlocking {
        val preferences = context.dataStore.data.first()
        val firstName = preferences[FIRST_NAME_KEY] ?: ""
        val lastName = preferences[LAST_NAME_KEY] ?: ""
        val email = preferences[EMAIL_KEY] ?: ""

        firstName.isNotEmpty() && lastName.isNotEmpty() && email.isNotEmpty()
    }
    return userDataAvailable
}