package com.goforer.base.designsystem.component

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.goforer.phogal.presentation.ui.compose.screen.home.setting.SettingContent
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@Composable
fun ScaffoldContent(
    topInterval: Dp,
    content: @Composable (paddingValues: PaddingValues) -> Unit
) {
    Column {
        Spacer(modifier = Modifier.height(topInterval))
        content(paddingValues = PaddingValues())
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
fun ScaffoldContentPreview(
    modifier: Modifier = Modifier
) {
    PhogalTheme {
        Column {
            Spacer(modifier = Modifier.height(8.dp))
            SettingContent(
                modifier = modifier,
                contentPadding = PaddingValues(4.dp),
                onItemClicked = {}
            )
        }
    }
}