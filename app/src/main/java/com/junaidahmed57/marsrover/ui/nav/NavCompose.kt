@file:OptIn(ExperimentalSharedTransitionApi::class)

package com.junaidahmed57.marsrover.ui.nav

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.junaidahmed57.marsrover.ui.nav.Destinations.MANIFEST
import com.junaidahmed57.marsrover.ui.nav.Destinations.PHOTO
import com.junaidahmed57.marsrover.ui.theme.MarsRoverTheme
import com.junaidahmed57.marsrover.ui.view.ManifestScreen
import com.junaidahmed57.marsrover.ui.view.PhotoListSavedScreen
import com.junaidahmed57.marsrover.ui.view.PhotoScreen
import com.junaidahmed57.marsrover.ui.view.RoverList

@Composable
fun NavCompose() {

    val items = listOf(
        Screen.Home,
        Screen.Saved
    )

    val navController = rememberNavController()

    MarsRoverTheme {
        Scaffold(
            bottomBar = {
                NavigationBar {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination

                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = {Icon(painter = painterResource(id = screen.drawableId), contentDescription = stringResource(
                                id = screen.resourceId))},
                            label = {
                                Text(text = stringResource(id = screen.resourceId))
                            },
                            selected = currentDestination?.hierarchy?.any {
                                if (it.route?.contains(MANIFEST) == true || it.route?.contains(PHOTO) == true){
                                    screen.route == Home.toString()
                                }
                                else { it.route == screen.route }
                            } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }

                }
            }
        ) { innerPadding ->

            SharedTransitionLayout {

                NavHost(
                    navController = navController,
                    startDestination = "Home",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("Home") {
                        RoverList(this) { roverName, roverImg ->
                            navController.navigate(
                                Manifest(
                                    roverName = roverName,
                                    roverImg = roverImg
                                )
                            )
                        }
                    }
                    composable<Manifest> {
                        val args = it.toRoute<Manifest>()
                        ManifestScreen(
                            animatedVisibilityScope = this,
                            roverName = args.roverName,
                            roverImg = args.roverImg,
                            marsRoverManifestViewModel = hiltViewModel(),
                            onClick = { roverName, sol ->
                                navController.navigate(
                                    Photo(
                                        roverName = roverName,
                                        sol = sol
                                    )
                                )
//                            navController.navigate("photo/${roverName}?sol=${sol}")
                            }
                        )
                    }
                    composable<Photo> { backStackEntry ->
                        val args = backStackEntry.toRoute<Photo>()
                        PhotoScreen(
                            roverName = args.roverName,
                            sol = args.sol,
                            marsRoverPhotoViewModel = hiltViewModel()
                        )
                    }
                    composable("Saved") {
                        PhotoListSavedScreen(
                            modifier = Modifier,
                            marsRoverSaveViewModel = hiltViewModel()
                        )
                    }
                }
            }
        }
    }

}