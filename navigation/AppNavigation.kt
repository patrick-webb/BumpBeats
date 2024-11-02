package com.example.bumpbeats.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.bumpbeats.ui.screens.SignUpScreen
import com.example.bumpbeats.ui.screens.WelcomeScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController, startDestination = "welcome") {
        composable("welcome") {
            WelcomeScreen(
                onSignInClick = { /* Navigate to Sign In screen */ },
                onSignUpClick = { navController.navigate("signup") }
            )
        }
        composable("signup") {
            SignUpScreen(
                onSignUpSubmit = { name, lastName, age, email, password ->
                    // Handle the sign-up submission
                }
            )
        }
    }
}

