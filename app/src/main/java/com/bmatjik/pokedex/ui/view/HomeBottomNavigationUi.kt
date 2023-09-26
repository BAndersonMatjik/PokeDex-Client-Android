package com.bmatjik.pokedex.ui.view

import android.graphics.drawable.Icon
import android.graphics.drawable.VectorDrawable
import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.bmatjik.pokedex.R

@Composable
fun HomeBottomNavigation(items: List<BottomNavigationScreens> = listOf(),navController: NavHostController) {
    var selectedItem by remember { mutableStateOf(0) }

    NavigationBar {
        items.forEachIndexed { index, bottomNavigationScreens ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = {
                    selectedItem = index
                    navController.navigate(bottomNavigationScreens.route)
                },
                icon = { Icon(bottomNavigationScreens.icon, contentDescription = bottomNavigationScreens.route) },
                label = { Text(text = stringResource(id = bottomNavigationScreens.resourceId)) })
        }
    }
}

sealed class BottomNavigationScreens(
    val route: String,
    @StringRes val resourceId: Int,
    val icon: ImageVector
) {
    object Home : BottomNavigationScreens("Home", R.string.home_route, Icons.Filled.Home)
    object MyPoke : BottomNavigationScreens("myPoke", R.string.my_poke_route, Icons.Filled.List)
}

sealed class MainScreenRoute(val route:String){
    object DetailPoke:MainScreenRoute("detailPoke?id={id}")
}