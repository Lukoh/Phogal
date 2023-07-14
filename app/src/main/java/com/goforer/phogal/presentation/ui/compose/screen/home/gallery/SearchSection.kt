package com.goforer.phogal.presentation.ui.compose.screen.home.gallery

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.goforer.base.designsystem.component.IconButton
import com.goforer.phogal.R
import com.goforer.phogal.presentation.stateholder.uistate.EditableInputState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.SearchSectionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.rememberSearchSectionState
import com.goforer.phogal.presentation.stateholder.uistate.rememberEditableInputState
import com.goforer.phogal.presentation.ui.theme.Black
import com.goforer.phogal.presentation.ui.theme.ColorSnowWhite
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray9
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@Composable
fun SearchSection(
    modifier: Modifier = Modifier,
    state: SearchSectionState = rememberSearchSectionState(),
    onSearched: (word: String) -> Unit
) {
    val isFocused by state.interactionSource.collectIsFocusedAsState()
    val indicatorColor = if (isFocused) Color.Black else Color.Gray

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .height(50.dp)
            .background(Color.Transparent)
            .semantics { isTraversalGroup = true }
            .zIndex(1f)
            .wrapContentHeight(Alignment.Top)
            .border(
                width = 1.dp,
                color = ColorSystemGray9,
                shape = RoundedCornerShape(size = 8.dp)
            )
            .fillMaxWidth()
    ) {
        TextField(
            value = if (state.editableInputState.isHint)
                ""
            else
                state.editableInputState.textState,
            onValueChange = {
                state.wordChangedState.value = true
                state.editableInputState.textState = it
            },
            enabled = state.enabledState.value,
            leadingIcon = {
                Icon(
                    modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp),
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            colors = TextFieldDefaults.colors(
                focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = Color.Gray,
                disabledTextColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                errorContainerColor = Color.Transparent
            ),
            shape = MaterialTheme.shapes.small,
            placeholder = {
                Text(stringResource(R.string.placeholder_search),  style = MaterialTheme.typography.titleMedium.copy(color = Black))
            },
            textStyle = TextStyle.Default.copy(
                fontSize = 16.sp,
                fontFamily = FontFamily.SansSerif,
                fontStyle = FontStyle.Normal,
                fontWeight = FontWeight.Medium
            ),
            modifier = modifier
                .weight(4f)
                .background(Color.Transparent)
                .drawBehind {
                    val strokeWidth =  0.5.dp.value * density
                    val y = size.height - strokeWidth / 2
                    drawLine(
                        indicatorColor,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                }
        )
        IconButton(
            modifier = modifier.padding(horizontal = 2.dp),
            height = 42.dp,
            onClick = {
                if (state.wordChangedState.value)
                    onSearched(state.editableInputState.textState)
            },
            icon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                )
            },
            text = {
                Text(
                    stringResource(id = R.string.placeholder_search),
                    fontFamily = FontFamily.SansSerif,
                    fontSize = 15.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        )
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
fun SearchSectionPreview(modifier: Modifier = Modifier) {
    PhogalTheme {
        val interactionSource = remember { MutableInteractionSource() }
        val isFocused by interactionSource.collectIsFocusedAsState()
        val indicatorColor = if (isFocused) Color.Black else Color.Gray
        val state: EditableInputState = rememberEditableInputState("")

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .height(50.dp)
                .background(Color.Transparent)
                .wrapContentHeight(Alignment.Top)
                .border(
                    width = 1.dp,
                    color = ColorSystemGray9,
                    shape = RoundedCornerShape(size = 8.dp)
                )
                .fillMaxWidth()
        ) {
            TextField(
                value = if (state.isHint)
                    ""
                else
                    state.textState,
                onValueChange = {
                    state.textState = it
                },
                leadingIcon = {
                    Icon(
                        modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp),
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                    unfocusedPlaceholderColor = MaterialTheme.colorScheme.primary,
                    focusedTextColor = Color.Gray,
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    errorContainerColor = Color.Transparent
                ),
                shape = MaterialTheme.shapes.small,
                placeholder = {
                    Text(stringResource(R.string.placeholder_search),  style = MaterialTheme.typography.titleMedium.copy(color = Black))
                },
                textStyle = TextStyle.Default.copy(
                    fontSize = 16.sp,
                    fontFamily = FontFamily.SansSerif,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Medium
                ),
                modifier = modifier
                    .weight(4f)
                    .background(ColorSnowWhite)
                    .drawBehind {
                        val strokeWidth =  0.5.dp.value * density
                        val y = size.height - strokeWidth / 2
                        drawLine(
                            indicatorColor,
                            Offset(0f, y),
                            Offset(size.width, y),
                            strokeWidth
                        )
                    }
            )
            IconButton(
                modifier = modifier.padding(horizontal = 2.dp),
                height = 42.dp,
                onClick = {},
                icon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null,
                    )
                },
                text = {
                    Text(
                        stringResource(id = R.string.placeholder_search),
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 15.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            )
        }
    }
}