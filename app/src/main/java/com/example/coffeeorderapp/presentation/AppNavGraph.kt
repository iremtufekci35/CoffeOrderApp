package com.example.coffeeorderapp.presentation

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.coffeeorderapp.data.local.DataStoreManager
import com.example.coffeeorderapp.presentation.home.HomeScreen
import com.example.coffeeorderapp.presentation.login.LoginScreen
import com.example.coffeeorderapp.presentation.login.LoginViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    loginViewModel: LoginViewModel,
    context: Context,
    startDestination: String
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable("login") {
            LoginScreen(
                viewModel = loginViewModel,
                onLoginClick = { email -> loginViewModel.login(email) },
                onLoginSuccess = {

                }
            )
        }
        composable("home") {
            HomeScreen()
        }
    }
}

