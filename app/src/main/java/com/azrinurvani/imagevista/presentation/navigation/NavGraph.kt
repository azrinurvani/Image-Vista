package com.azrinurvani.imagevista.presentation.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import androidx.paging.compose.collectAsLazyPagingItems
import com.azrinurvani.imagevista.presentation.favourite.FavouriteScreen
import com.azrinurvani.imagevista.presentation.favourite.FavouriteViewModel
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
            val images = homeViewModel.images.collectAsLazyPagingItems()
            val favouriteImageIds by homeViewModel.favouriteImageIds.collectAsStateWithLifecycle()
            HomeScreen(
                snackBarHostState = snackbarHostState,
                snackBarEvent = homeViewModel.snackBarEvent,
                scrollBehavior,
                images = images,
                favouriteImageIds = favouriteImageIds,
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearchClick = { navController.navigate(Routes.SearchScreen) },
                onFABClick = { navController.navigate(Routes.FavouriteScreen) },
                onToggleFavouriteStatus = { homeViewModel.toggleFavouriteStatus(it) }
            )
        }
        composable<Routes.SearchScreen> {
            val searchViewModel : SearchViewModel = hiltViewModel()
            val searchImages = searchViewModel.searchImages.collectAsLazyPagingItems()

            val favouriteImageIds by searchViewModel.favouriteImageIds.collectAsStateWithLifecycle()

            SearchScreen(
                snackBarHostState = snackbarHostState,
                snackBarEvent = searchViewModel.snackBarEvent,
                searchImages = searchImages,
                favouriteImageIds = favouriteImageIds,
                searchQuery = searchQuery,
                onSearchQueryChange = onSearchQueryChange,
                onBackClick = { navController.navigateUp() },
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onSearch = { searchViewModel.searchImages(it) },
                onToggleFavouriteStatus = { searchViewModel.toggleFavouriteStatus(it)}
            )

        }
        composable<Routes.FavouriteScreen> {
            val favouritesViewModel : FavouriteViewModel = hiltViewModel()
            val favouriteImages = favouritesViewModel.favouriteImages.collectAsLazyPagingItems()
            val favouriteImageIds by favouritesViewModel.favouriteImageIds.collectAsStateWithLifecycle()

            FavouriteScreen(
                snackBarHostState = snackbarHostState,
                favouriteImages = favouriteImages,
                snackBarEvent = favouritesViewModel.snackBarEvent,
                scrollBehavior = scrollBehavior,
                onSearchClick = { navController.navigate(Routes.SearchScreen) },
                favouriteImageIds = favouriteImageIds,
                onBackClick = { navController.navigateUp() },
                onImageClick = { imageId ->
                    navController.navigate(Routes.FullImageScreen(imageId))
                },
                onToggleFavouriteStatus = { favouritesViewModel.toggleFavouriteStatus(it) }
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