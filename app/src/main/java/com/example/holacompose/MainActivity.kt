package com.example.holacompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNav()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNav() {
    val navController = rememberNavController()

    MaterialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { androidx.compose.material3.Text("Navegación Compose") }
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "login",
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                // pantalla 1: login
                composable(route = "login") {
                    LoginScreen(navController = navController)
                }

                // pantalla 2: perfil con argumento
                composable(
                    route = "profile/{username}",
                    arguments = listOf(
                        navArgument("username") { type = NavType.StringType }
                    )
                ) { backStackEntry ->
                    val userArg = backStackEntry.arguments
                        ?.getString("username")
                        .orEmpty()

                    ProfileScreen(
                        username = userArg,
                        onLogout = {
                            // volvemos al login
                            navController.popBackStack(
                                route = "login",
                                inclusive = false
                            )
                        }
                    )
                }
            }
        }
    }
}
