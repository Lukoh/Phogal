package com.goforer.phogal.presentation.ui.compose.screen.home.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.goforer.phogal.R
import com.goforer.phogal.data.model.local.SettingItem
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary

@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    onItemClicked: (index: Int) -> Unit
) {
    val items = listOf(
        SettingItem(stringResource(id = R.string.setting_system_permission),  R.drawable.ic_setting),
        SettingItem(stringResource(id = R.string.setting_bookmark), R.drawable.ic_setting),
        SettingItem(stringResource(id = R.string.setting_follower), R.drawable.ic_setting),
        SettingItem(stringResource(id = R.string.setting_alarm), R.drawable.ic_setting),
        SettingItem(stringResource(id = R.string.setting_privacy_policy), R.drawable.ic_setting),
        SettingItem(stringResource(id = R.string.setting_app_info), R.drawable.ic_setting)
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBgSecondary)
            //.wrapContentSize(Alignment.Center)
            .padding(0.dp, contentPadding.calculateTopPadding(), 0.dp, 0.dp)
    ) {
        items.forEachIndexed { index, item ->
            SetItem(
                modifier = Modifier,
                index = index,
                text = item.text,
                drawable = item.drawable,
                onItemClicked = onItemClicked
            )
        }
    }
}