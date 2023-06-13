package com.goforer.base.designsystem.component

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chips(
    modifier: Modifier = Modifier,
    items: List<String>,
    textColor: Color,
    textFontSize : TextUnit,
    leadingIconTint: Color,
    onClicked: (String) -> Unit
) {
    var visible by remember { mutableStateOf(false) }
    val animationScale by animateFloatAsState(
        targetValue = if (visible) 1F else 0.4F,
        animationSpec = spring(stiffness = Spring.StiffnessMediumLow)
    )

    LaunchedEffect(Unit) {
        delay(10)
        visible = true
    }

    Column {
        LazyRow(
            modifier = modifier.padding(horizontal = 8.dp)
        ) {
            items(items) {
                InputChip(
                    modifier = Modifier.scale(animationScale),
                    selected = true,
                    onClick = {
                        onClicked(it)
                    },
                    enabled = true,
                    label = {
                        Text(
                            text = it,
                            color = textColor,
                            fontFamily = FontFamily.SansSerif,
                            fontWeight = FontWeight.Normal,
                            fontSize = textFontSize,
                            fontStyle = FontStyle.Normal,
                            style = MaterialTheme.typography.titleMedium
                        )
                     },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.ImageSearch,
                            contentDescription = "Keyword",
                            modifier = Modifier.size(24.dp),
                            tint = leadingIconTint
                        )
                    }
                )
                if (items.size > 1)
                    Spacer(modifier = Modifier.width(8.dp))
            }
        }
    }
}