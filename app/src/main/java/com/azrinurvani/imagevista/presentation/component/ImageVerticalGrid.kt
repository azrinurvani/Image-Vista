package com.azrinurvani.imagevista.presentation.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGesturesAfterLongPress
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import com.azrinurvani.imagevista.domain.model.UnsplashImage

@Composable
fun ImageVerticalGrid(
    modifier: Modifier = Modifier,
    images : LazyPagingItems<UnsplashImage>,
    favouriteImageIds : List<String>,
    onImageClick : (String) -> Unit,
    onImageDragStart : (UnsplashImage?) -> Unit,
    onImageDragEnd : () -> Unit,
    onToggleFavouriteStatus : (UnsplashImage) -> Unit
){
    LazyVerticalStaggeredGrid(
        modifier = modifier,
        columns = StaggeredGridCells.Adaptive(180.dp),
        contentPadding = PaddingValues(10.dp),
        verticalItemSpacing = 10.dp,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        items(count = images.itemCount){ index ->
            val image = images[index]
            ImageCard(
                image = image,
                modifier = Modifier
                    .clickable { image?.id?.let { onImageClick(it) } }
                    .pointerInput(Unit){
                        //for gesture listener from user
                        detectDragGesturesAfterLongPress(
                            onDragStart = { onImageDragStart(image)},
                            onDragCancel = { onImageDragEnd() },
                            onDragEnd = { onImageDragEnd() },
                            onDrag = { _ , _ ->}
                        )
                    },
                onToggleFavouriteStatus = {
                    image?.let { onToggleFavouriteStatus(it) }
                },
                isFavourite = favouriteImageIds.contains(image?.id)
            )

        }

        //using List<UnsplashImage> not LazyPagingItems<UnsplashImage>
//        items(images){ image->
//            ImageCard(
//                image = image,
//                modifier = Modifier
//                    .clickable { image?.id?.let { onImageClick(it) } }
//                    .pointerInput(Unit){
//                        //for gesture listener from user
//                        detectDragGesturesAfterLongPress(
//                            onDragStart = { onImageDragStart(image)},
//                            onDragCancel = { onImageDragEnd() },
//                            onDragEnd = { onImageDragEnd() },
//                            onDrag = { _ , _ ->}
//                        )
//                    }
//            )
//        }
    }
}