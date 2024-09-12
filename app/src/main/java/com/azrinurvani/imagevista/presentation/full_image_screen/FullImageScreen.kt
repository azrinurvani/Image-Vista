package com.azrinurvani.imagevista.presentation.full_image_screen

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.animateZoomBy
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.azrinurvani.imagevista.R
import com.azrinurvani.imagevista.domain.model.UnsplashImage
import com.azrinurvani.imagevista.presentation.component.DownloadOptionsBottomSheet
import com.azrinurvani.imagevista.presentation.component.FullImageViewTopBar
import com.azrinurvani.imagevista.presentation.component.ImageDownloadOption
import com.azrinurvani.imagevista.presentation.component.ImageVistaLoadingBar
import com.azrinurvani.imagevista.presentation.util.SnackBarEvent
import com.azrinurvani.imagevista.presentation.util.rememberWindowInsetsController
import com.azrinurvani.imagevista.presentation.util.toggleStatusBars
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

import kotlin.math.max

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun FullImageScreen(
    snackBarHostState : SnackbarHostState,
    snackBarEvent: Flow<SnackBarEvent>,
    image : UnsplashImage?,
    onBackClick : () -> Unit,
    onPhotographerNameClick : (String) -> Unit,
    onImageDownloadClick : (String,String?) -> Unit
){
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    var showBars by rememberSaveable { mutableStateOf(false) }
    val windowInsetsController = rememberWindowInsetsController()

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    var isDownloadBottomSheetOpen by remember{ mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        snackBarEvent.collect{ event ->
            snackBarHostState.showSnackbar(
                message = event.message,
                duration = event.duration
            )
        }
    }

    //Triggered only for the first time use LaunchedEffect
    LaunchedEffect(key1 = Unit) { //use Unit not change the value after initial trigger
        windowInsetsController.toggleStatusBars(show = showBars)
    }

    BackHandler(enabled = !showBars) { //only trigger when back show bars is false
        windowInsetsController.toggleStatusBars(show = true)
        onBackClick()
    }

    DownloadOptionsBottomSheet(
        isOpen = isDownloadBottomSheetOpen,
        sheetState = sheetState,
        onDismissRequest = { isDownloadBottomSheetOpen = false },
        onOptionClick = { option ->
            scope.launch {
                sheetState.hide() //whenever user click option button Bottom Sheet must be close
            }.invokeOnCompletion {
                //checking after bottom sheet hiden
                if (!sheetState.isVisible) isDownloadBottomSheetOpen = false
            }
            val url = when( option ){
                ImageDownloadOption.SMALL -> image?.imageUrlSmall
                ImageDownloadOption.MEDIUM -> image?.imageUrlRegular
                ImageDownloadOption.ORIGINAL -> image?.imageUrlRaw
            }

            url?.let{
                onImageDownloadClick(it,image?.description?.take(20)) //only 20 character
                Toast.makeText(context,"Downloading...",Toast.LENGTH_LONG).show()
            }
        }
    )

    Box(
        modifier = Modifier.fillMaxSize(),
    ){
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            var scale by remember { mutableFloatStateOf(1f) }
            var offset by remember { mutableStateOf(Offset.Zero) }

            val isImageZoomed : Boolean by remember { derivedStateOf { scale != 1f } } //scale != 1f it's mean zoomed

            val transformState = rememberTransformableState { zoomChange, offsetChange, _ ->
                scale = max(scale * zoomChange, 1f) //its mean scale value not below 1f
                val maxX = (constraints.maxWidth * (scale - 1)) / 2
                val maxY = (constraints.maxHeight * (scale - 1)) / 2
                    offset = Offset(
                        x = (offset.x + offsetChange.x).coerceIn(-maxX, maxX),
                        y = (offset.y + offsetChange.y).coerceIn(-maxY, maxY)
                    )
            }

            var isLoading by remember { mutableStateOf(true) }
            var isError by remember { mutableStateOf(false) }

            val imageLoader = rememberAsyncImagePainter(
                model = image?.imageUrlRaw,
                onState = { imageState ->
                    isLoading = imageState is AsyncImagePainter.State.Loading
                    isError = imageState is AsyncImagePainter.State.Error
                }
            )
            if (isLoading) {
                ImageVistaLoadingBar()
            }

            Image(
                painter = if (isError.not()) imageLoader else painterResource(id = R.drawable.ic_error),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .transformable(transformState)
                    .combinedClickable(
                        onDoubleClick = {
                            //if user double tap or double click
                            if (isImageZoomed) {
                                scale = 1f
                                offset = Offset.Zero
                            } else {
                                scope.launch {
                                    transformState.animateZoomBy(zoomFactor = 3f)
                                }
                            }
                        },
                        onClick = {
                            showBars = !showBars
                            windowInsetsController.toggleStatusBars(show = showBars)
                        },
                        indication = null, //provide indication and interactionSource for clickable effect
                        interactionSource = remember {
                            MutableInteractionSource()
                        }
                    )
                    .graphicsLayer {
                        scaleX = scale
                        scaleY = scale
                        translationX = offset.x
                        translationY = offset.y
                    }
            )
        }
        FullImageViewTopBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .fillMaxWidth()
                .padding(horizontal = 5.dp, vertical = 40.dp),
            image = image,
            isVisible = showBars,
            onBackClick = onBackClick,
            onPhotographerNameClick = onPhotographerNameClick,
            onDownloadImageClick = { isDownloadBottomSheetOpen = true }
        )
    }

}