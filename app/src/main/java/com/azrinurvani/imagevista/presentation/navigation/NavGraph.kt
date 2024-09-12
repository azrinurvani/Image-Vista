package com.azrinurvani.imagevista.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.azrinurvani.imagevista.presentation.favourite.FavouriteScreen
import com.azrinurvani.imagevista.presentation.full_image_screen.FullImageScreen
import com.azrinurvani.imagevista.presentation.full_image_screen.FullImageViewModel
import com.azrinurvani.imagevista.presentation.home_sreen.HomeScreen
import com.azrinurvani.imagevista.presentation.home_sreen.HomeViewModel
import com.azrinurvani.imagevista.presentation.profile.ProfileScreen
import com.azrinurvani.imagevista.presentation.search_screen.SearchScreen
import com.azrinurvani.imagevista.presentation.search_screen.SearchViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraphSetup(
    navController: NavHostController,
    scrollBehavior: TopAppBarScrollBehavior,
    snackbarHostState: SnackbarHostState,
    searchQuery : String,
    onSearchQueryChange : (String) -> Unit,
){
    NavHost(
        navController = navController,
        startDestination = Routes.HomeScreen
    ){
        composable<Routes.HomeScreen> {
//            val viewModel = viewModel<HomeViewModel>()
            val homeViewModel : HomeViewModel = hiltViewModel()
            HomeScreen(
                snackBarHostState = snackbarHostState,
                snackBarEvent = homeViewModel.snackBarEvent,
                scrollBehavior,
                images = homeViewModel.images,
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearchClick = { navController.navigate(Routes.SearchScreen) },
                onFABClick = { navController.navigate(Routes.FavouriteScreen) },
            )
        }
        composable<Routes.SearchScreen> {
            val searchViewModel : SearchViewModel = hiltViewModel()
            val searchImages = searchViewModel.searchImages.collectAsLazyPagingItems()
            SearchScreen(
                snackBarHostState = snackbarHostState,
                snackBarEvent = searchViewModel.snackBarEvent,
                searchImages = searchImages,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onBackClick = { navController.navigateUp() },
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearch = { searchViewModel.searchImages(it) }
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
                snackBarHostState = snackbarHostState,
                snackBarEvent = fullImageViewModel.snackBarEvent,
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