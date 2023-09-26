package com.bmatjik.pokedex.ui.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.bmatjik.pokedex.ui.theme.PokeDexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val bottomNavigationItem = listOf(
        BottomNavigationScreens.Home,
        BottomNavigationScreens.MyPoke
    )
    Scaffold(bottomBar = {
        HomeBottomNavigation(navController = navController, items = bottomNavigationItem)
    }) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            NavHost(
                navController = navController,
                startDestination = BottomNavigationScreens.Home.route,
                builder = {
                    composable(route = BottomNavigationScreens.Home.route) {
                        HomeScreen(onPokeItemClick = {
                            val route = MainScreenRoute.DetailPoke.route.replace(
                                oldValue = "{id}",
                                newValue = it.id.toString()
                            )
                            navController.navigate(route)
                        })
                    }
                    composable(route = BottomNavigationScreens.MyPoke.route) {
                        MyPokemonScreen(onPokeItemClick = {
                            val route = MainScreenRoute.DetailPoke.route.replace(
                                oldValue = "{id}",
                                newValue = it.id.toString()
                            )
                            navController.navigate(route)
                        })
                    }
                    composable(route= MainScreenRoute.DetailPoke.route, arguments = listOf(
                        navArgument("id"){
                            type = NavType.StringType
                        }
                    )){
                        val id = it.arguments?.getString("id")
                        DetailPokemonScreen(id= id?:"")
                    }
                })


        }
    }
}

@Composable
@Preview
fun MainScreenPreview() {
    PokeDexTheme {
        MainScreen()
    }
}