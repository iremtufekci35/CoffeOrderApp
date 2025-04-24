package com.example.coffeeorderapp.presentation.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.coffeeorderapp.presentation.AppNavGraph
import com.example.coffeeorderapp.presentation.login.LoginViewModel
import androidx.compose.ui.platform.LocalContext
import com.example.coffeeorderapp.data.local.DataStoreManager
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import com.example.coffeeorderapp.R


@Composable
fun MainScreen(loginViewModel: LoginViewModel) {
    val context = LocalContext.current
    val dataStoreManager = DataStoreManager(context)
    val loginSuccessState = dataStoreManager.isLoggedIn.collectAsState(initial = null)
    val loginSuccess = loginSuccessState.value
    val navController = rememberNavController()

    MaterialTheme {
        when (loginSuccess) {
            null -> {
                SplashScreen()
            }
            else -> {
                AppNavGraph(
                    navController = navController,
                    loginViewModel = loginViewModel,
                    context = context,
                    startDestination = if (loginSuccess) "home" else "login"
                )
            }
        }
    }
}
@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            Box(
                modifier = Modifier
                    .size(140.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFF5F5F5)),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.coffee_logo),
                    contentDescription = "App Logo",
                    modifier = Modifier.size(100.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Coffee Order",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF6F4E37)
            )

            Spacer(modifier = Modifier.height(32.dp))

            CircularProgressIndicator(color = Color(0xFF6F4E37))
        }
    }
}


