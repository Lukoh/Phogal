package com.goforer.base.designsystem.component

import android.content.res.Configuration
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goforer.phogal.R
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@Composable
internal inline fun IconButton(
    modifier: Modifier = Modifier,
    height: Dp,
    colors: ButtonColors = ButtonDefaults.buttonColors(),
    noinline onClick: () -> Unit,
    crossinline icon: @Composable () -> Unit,
    crossinline text: @Composable () -> Unit,
    interactionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    /*
    val isButtonEnabled = remember { mutableStateOf(true) }
    val animatedButtonColor = animateColorAsState(
        targetValue = if (isButtonEnabled.value) Color.White else Color.Gray,
        animationSpec = tween(1000, 0, LinearEasing))

     */

    Button(
        onClick = onClick,
        modifier = modifier
            .wrapContentWidth()
            .heightIn(height),
        shape = MaterialTheme.shapes.small,
        colors = colors,
        interactionSource = interactionSource
    ) {
        AnimatedVisibility(visible = isPressed) {
            if (isPressed) {
                Row(modifier = Modifier.animateContentSize()) {
                    icon()
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                }
            }
        }
        text()
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
fun IconButtonPreview(modifier: Modifier = Modifier) {
    PhogalTheme {
        val interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
        val isPressed by interactionSource.collectIsPressedAsState()
        /*
        val isButtonEnabled = remember { mutableStateOf(true) }
        val animatedButtonColor = animateColorAsState(
            targetValue = if (isButtonEnabled.value) Color.White else Color.Gray,
            animationSpec = tween(1000, 0, LinearEasing))

         */

        Button(
            onClick = {},
            modifier = modifier
                .height(IntrinsicSize.Min)
                .wrapContentHeight()
                .wrapContentWidth()
                .heightIn(48.dp),
            shape = MaterialTheme.shapes.small,
            interactionSource = interactionSource
        ) {
            AnimatedVisibility(visible = isPressed) {
                if (isPressed) {
                    Row(modifier = Modifier.animateContentSize()) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null,
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                    }
                }
            }
            Text(
                stringResource(id = R.string.placeholder_search),
                fontFamily = FontFamily.SansSerif,
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic
            )
        }
    }
}