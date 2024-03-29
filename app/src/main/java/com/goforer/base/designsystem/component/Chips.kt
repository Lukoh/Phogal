package com.goforer.base.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ImageSearch
import androidx.compose.material.icons.twotone.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.InputChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goforer.phogal.presentation.ui.theme.Black
import com.goforer.phogal.presentation.ui.theme.Blue40
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Chips(
    modifier: Modifier = Modifier,
    items: List<String>,
    textColor: Color,
    leadingIconTint: Color,
    trailingIconTint: Color,
    onClicked: (word: String) -> Unit,
    onDeleted: (word: String) -> Unit
) {
    LazyRow(
        modifier = modifier
            .padding(start = 8.dp, end = 8.dp, bottom = 4.dp)
            .fillMaxWidth()
    ) {
        items(items) {
            InputChip(
                selected = true,
                onClick = {
                    onClicked(it)
                },
                enabled = true,
                label = {
                    Text(
                        text = it,
                        color = textColor,
                        fontStyle = FontStyle.Normal,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.ImageSearch,
                        contentDescription = "Word",
                        modifier = Modifier.size(24.dp),
                        tint = leadingIconTint
                    )
                },
                trailingIcon = {
                    Icon(
                        imageVector = Icons.TwoTone.Close ,
                        contentDescription = "Remove",
                        modifier = Modifier
                            .size(20.dp)
                            .clickable {
                                onDeleted(it)
                            },
                        tint = trailingIconTint
                    )
                }
            )
            if (items.size > 1)
                Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode",
    showSystemUi = true
)
@Composable
fun ChipsPreview(modifier: Modifier = Modifier) {
    val items = listOf("Mountain", "Train", "Seoul", "San Diego", "Sea", "Cook")
    val textColor = Black
    val textFontSize = 13.sp
    val leadingIconTint = Blue40

    PhogalTheme {
        Column {
            LazyRow(
                modifier = modifier.padding(horizontal = 8.dp)
            ) {
                items(items) {
                    InputChip(
                        selected = true,
                        onClick = {},
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
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }
        }
    }
}