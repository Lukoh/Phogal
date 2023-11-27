package com.goforer.phogal.presentation.ui.compose.screen.home.common.follow

import android.content.res.Configuration
import androidx.compose.animation.Animatable
import androidx.compose.foundation.background
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.goforer.phogal.presentation.ui.theme.Blue50
import com.goforer.phogal.presentation.ui.theme.ColorText1
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@Composable
fun ShowFollowButton(
    modifier: Modifier = Modifier,
    followColor: Color,
    isUserFollowed: Boolean,
    onFollow: () -> Unit
) {
    var isFollowed by rememberSaveable { mutableStateOf(isUserFollowed) }
    val color = remember { Animatable(Color.Transparent) }

    LaunchedEffect(isFollowed) {
        color.animateTo(Color.Transparent)
    }
    Button(
        onClick = {
            isFollowed = !isFollowed
            onFollow()
        },
        modifier = modifier
            .widthIn(132.dp)
            .indication(
                interactionSource = remember { MutableInteractionSource() },
                indication  = rememberRipple(bounded = false)
            )
            .background(
                color = color.value,
                shape = MaterialTheme.shapes.small
            ),
        shape = MaterialTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            disabledContentColor = Color.Transparent
        ),
        interactionSource = remember { MutableInteractionSource() },
        contentPadding = PaddingValues(
            start = 10.dp,
            top = 6.dp,
            end = 10.dp,
            bottom = 6.dp
        )
    ) {
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(width = 28.dp, height = 28.dp),
                imageVector = if (isFollowed)
                    Icons.Filled.Check
                else
                    Icons.Filled.Add,
                contentDescription = "F",
                tint = if (isFollowed)
                    ColorText1
                else
                    followColor
            )
            Spacer(modifier = Modifier.width(width = 3.dp))
            Text(
                text = if (isFollowed)
                    "F"
                else
                    "F",
                color = if (isFollowed)
                    ColorText1
                else
                    followColor,
                fontStyle = FontStyle.Normal,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode",
    showSystemUi = true
)
@Composable
fun ShowFollowButtonPreview(modifier: Modifier = Modifier) {
    PhogalTheme {
        var isFollowed = false
        val color = remember { Animatable(Color.Transparent) }

        LaunchedEffect(isFollowed) {
            color.animateTo(if (isFollowed) Color.Transparent else Color.Transparent)
        }
        Button(
            onClick = {
                isFollowed = !isFollowed
            },
            modifier = modifier
                .widthIn(132.dp)
                .indication(
                    interactionSource = remember { MutableInteractionSource() },
                    indication  = rememberRipple(bounded = false)
                )
                .background(
                    color = color.value,
                    shape = MaterialTheme.shapes.small
                ),
            shape = MaterialTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
                contentColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                disabledContentColor = Color.Transparent
            ),
            interactionSource = remember { MutableInteractionSource() },
            contentPadding = PaddingValues(
                start = 10.dp,
                top = 6.dp,
                end = 10.dp,
                bottom = 6.dp
            )
        ) {
            Row(
                modifier = modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    modifier = Modifier.size(width = 28.dp, height = 28.dp),
                    imageVector = if (isFollowed)
                        Icons.Filled.Check
                    else
                        Icons.Filled.Add,
                    contentDescription = "F",
                    tint = if (isFollowed)
                        ColorText1
                    else
                        Blue50
                )
                Spacer(modifier = Modifier.width(width = 3.dp))
                Text(
                    text = if (isFollowed)
                        "F"
                    else
                        "F",
                    color = if (isFollowed)
                        ColorText1
                    else
                        Blue50,
                    fontStyle = FontStyle.Normal,
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}