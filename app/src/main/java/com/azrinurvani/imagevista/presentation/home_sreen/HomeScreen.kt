package com.azrinurvani.imagevista.presentation.home_sreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.azrinurvani.imagevista.R
import com.azrinurvani.imagevista.domain.model.UnsplashImage
import com.azrinurvani.imagevista.presentation.component.ImageVerticalGrid
import com.azrinurvani.imagevista.presentation.component.ImageVistaTopAppBar
import com.azrinurvani.imagevista.presentation.component.ZoomedImageCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    scrollBehavior: TopAppBarScrollBehavior,
    images : List<UnsplashImage>,
    onImageClick : (String) -> Unit,
    onSearchClick : () -> Unit,
    onFABClick : () -> Unit
){

    var showImagePreview by remember { mutableStateOf(false) }
    var activeImage by remember { mutableStateOf<UnsplashImage?>(null) }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ImageVistaTopAppBar(
                onSearchClick = onSearchClick,
                scrollBehavior = scrollBehavior
            )
            ImageVerticalGrid(
                images = images,
                onImageClick = onImageClick,
                onImageDragStart = { image ->
                    activeImage = image
                    showImagePreview = true
                },
                onImageDragEnd = { showImagePreview = false }
            )
        }
        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp),
            onClick = { onFABClick() }
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_save),
                contentDescription = "Favourites",
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        ZoomedImageCard(
            modifier = Modifier.padding(25.dp),
            isVisible = showImagePreview,
            image = activeImage
        )
    }

    
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState())
//    ) {
//        images.forEach { image ->
//
//            ImageCard(image = image)
////
////            Text(text = image.id)
////            Text(text = image.imageUrlRegular, color = Color.Blue)
////            Text(text = image.photographerName, fontWeight = FontWeight.Bold)
////            Text(text = "${image.width}x${image.height}")
////            HorizontalDivider()
//        }
//
//    }
}