package com.azrinurvani.imagevista.presentation.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.azrinurvani.imagevista.R
import com.azrinurvani.imagevista.domain.model.UnsplashImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageVistaTopAppBar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior,
    title : String = "Image Vista",
    onSearchClick : () -> Unit = {},
    navigationIcon: @Composable () -> Unit = {},
){
    CenterAlignedTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary
                    )
                ){
                    append(title.split(" ").first())
                }
                withStyle(
                    style = SpanStyle(
                        color = MaterialTheme.colorScheme.secondary
                    )
                ){
                    append(" ${title.split(" ").last()}")

                }

            },
            fontWeight = FontWeight.ExtraBold)
        },
        actions = {
            IconButton(onClick = { onSearchClick() }) {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "Search")
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            //When scroll down or top we don't want change color and we use this code to handle it
            scrolledContainerColor = MaterialTheme.colorScheme.background
        ),
        navigationIcon = navigationIcon

    )
}

@Composable
fun FullImageViewTopBar(
    modifier: Modifier = Modifier,
    image: UnsplashImage?,
    isVisible : Boolean,
    onBackClick : () -> Unit,
    onPhotographerNameClick : (String) -> Unit,
    onDownloadImageClick : () -> Unit,
){

    AnimatedVisibility(
        visible = isVisible,
        enter = fadeIn() + slideInVertically(),
        exit = fadeOut() + slideOutVertically()
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { onBackClick() }
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Go Back"
                )
            }
            AsyncImage(
                modifier = Modifier
                    .size(30.dp)
                    .clip(CircleShape),
                model = image?.photographerProfileImageUrl,
                contentDescription = null
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column(
                modifier = Modifier.clickable {
                    image?.let {
                        onPhotographerNameClick(it.photographerProfileLink)
                    }
                }
            ) {
                Text(
                    text = image?.photographerName ?: "",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = { onDownloadImageClick() }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_download),
                    contentDescription = "Download the image",
                    tint = MaterialTheme.colorScheme.onBackground
                )
            }
        }
    }
}