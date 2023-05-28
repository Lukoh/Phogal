package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.photo

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIos
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.goforer.phogal.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PictureScreen(
    modifier: Modifier = Modifier,
    navBackStackEntry: NavBackStackEntry,
    id: String,
    visibleViewPhotosButton: Boolean,
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onBackPressed: () -> Unit
) {
    Scaffold(
        contentColor = Color.White,
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        stringResource(id = R.string.picture_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp,
                        fontStyle = FontStyle.Normal,
                        fontWeight = FontWeight.Bold,
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { onBackPressed() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBackIos,
                            contentDescription = "Picture"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = "Favorite"
                        )
                    }
                }
            )
        }, content = { paddingValues ->
            PictureContent(
                modifier = modifier,
                navBackStackEntry = navBackStackEntry,
                contentPadding = paddingValues,
                id = id,
                visibleViewPhotosButton = visibleViewPhotosButton,
                onViewPhotos = onViewPhotos
            )
        }
    )
}