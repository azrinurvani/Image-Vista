package com.azrinurvani.imagevista.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.azrinurvani.imagevista.presentation.favourite.FavouriteScreen
import com.azrinurvani.imagevista.presentation.full_image_screen.FullImageScreen
import com.azrinurvani.imagevista.presentation.full_image_screen.FullImageViewModel
import com.azrinurvani.imagevista.presentation.home_sreen.HomeScreen
import com.azrinurvani.imagevista.presentation.home_sreen.HomeViewModel
import com.azrinurvani.imagevista.presentation.profile.ProfileScreen
import com.azrinurvani.imagevista.presentation.search_screen.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraphSetup(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior
){
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen
    ){
        composable<Routes.HomeScreen> {
//            val viewModel = viewModel<HomeViewModel>()
            val viewModel : HomeViewModel = hiltViewModel()
            HomeScreen(
                scrollBehavior,
                images = viewModel.images,
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearchClick = { navController.navigate(Routes.SearchScreen) },
                onFABClick = { navController.navigate(Routes.FavouriteScreen) },
            )
        }
        composable<Routes.SearchScreen> {
            SearchScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable<Routes.FavouriteScreen> {
            FavouriteScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
        composable<Routes.FullImageScreen> {
//            val fullImageViewModel = hiltViewModel<FullImageViewModel>()
            val fullImageViewModel : FullImageViewModel = hiltViewModel()
            FullImageScreen(
                image = fullImageViewModel.image,
                onBackClick = { navController.navigateUp() },
                onPhotographerNameClick = { profileLink ->
                    navController.navigate(Routes.ProfileScreen(profileLink))
                },
                onImageDownloadClick = { url, title ->
                    fullImageViewModel.downloadImage(url,title)
                }
            )
        }
        composable<Routes.ProfileScreen> { backStackEntry ->
            val profileLink = backStackEntry.toRoute<Routes.ProfileScreen>().profileLink
            ProfileScreen(
                profileLink = profileLink,
                onBackClick = { navController.navigateUp() }
            )
        }
    }
}