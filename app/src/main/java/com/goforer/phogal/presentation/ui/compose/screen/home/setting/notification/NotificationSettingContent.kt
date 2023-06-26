package com.goforer.phogal.presentation.ui.compose.screen.home.setting.notification

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.goforer.phogal.presentation.stateholder.business.home.setting.notification.NotificationSettingViewModel
import com.goforer.phogal.presentation.stateholder.business.home.setting.notification.NotificationSettingViewModel.Companion.COMMUNITY_NOTIFICATION_SETTING
import com.goforer.phogal.presentation.stateholder.business.home.setting.notification.NotificationSettingViewModel.Companion.FOLLOWING_NOTIFICATION_SETTING
import com.goforer.phogal.presentation.stateholder.business.home.setting.notification.NotificationSettingViewModel.Companion.LATEST_NOTIFICATION_SETTING
import com.goforer.phogal.presentation.ui.theme.Black
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary
import com.goforer.phogal.presentation.ui.theme.DarkGreen60
import com.goforer.phogal.presentation.ui.theme.DarkGreenGray90
import com.goforer.phogal.presentation.ui.theme.PhogalTheme
import com.goforer.phogal.presentation.ui.theme.Red60
import com.goforer.phogal.presentation.ui.theme.Red80

@Composable
fun NotificationSettingContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    notificationSettingViewModel: NotificationSettingViewModel = hiltViewModel()
) {
    val names = listOf("Following Notification", "Latest Notification", "Community Notification")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBgSecondary)
            //.wrapContentSize(Alignment.Center)
            .padding(0.dp, contentPadding.calculateTopPadding(), 0.dp, 0.dp)
            .verticalScroll(rememberScrollState())
    ) {
        NotificationToggleItem(
            modifier = Modifier,
            name = names[0],
            isToggled = notificationSettingViewModel.getNotificationSetting(FOLLOWING_NOTIFICATION_SETTING)
        ) { toggled ->
            notificationSettingViewModel.setNotificationEnabled(FOLLOWING_NOTIFICATION_SETTING, toggled)
        }

        Divider(modifier = Modifier.height(0.5.dp))
        NotificationToggleItem(
            modifier = Modifier,
            name = names[1],
            isToggled = notificationSettingViewModel.getNotificationSetting(LATEST_NOTIFICATION_SETTING)
        ) { toggled ->
            notificationSettingViewModel.setNotificationEnabled(LATEST_NOTIFICATION_SETTING, toggled)
        }

        Divider(modifier = Modifier.height(0.5.dp))
        NotificationToggleItem(
            modifier = Modifier,
            name = names[2],
            isToggled = notificationSettingViewModel.getNotificationSetting(COMMUNITY_NOTIFICATION_SETTING)
        ) { toggled ->
            notificationSettingViewModel.setNotificationEnabled(COMMUNITY_NOTIFICATION_SETTING, toggled)
        }

        Divider(modifier = Modifier.height(0.5.dp))
    }
}

@Composable
fun NotificationToggleItem(
    modifier: Modifier = Modifier,
    name: String,
    isToggled: Boolean,
    onToggled: (toggled: Boolean) -> Unit
) {
    var toggled by remember { mutableStateOf(isToggled) }

    Row(
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = name,
            modifier = modifier.align(Alignment.CenterVertically),
            color = Black,
            fontFamily = FontFamily.SansSerif,
            fontSize = 16.sp,
            fontStyle = FontStyle.Normal,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.weight(1F))
        Switch(
            checked = toggled,
            onCheckedChange = {
                toggled = it
                onToggled(it)
            },
            modifier = Modifier.size(60.dp),
            enabled = true,
            colors = SwitchDefaults.colors(
                checkedThumbColor = Red80,
                uncheckedThumbColor = DarkGreen60,
                checkedTrackColor = Red60,
                uncheckedTrackColor = DarkGreenGray90
            )
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
fun NotificationSettingContentPreview(modifier: Modifier = Modifier) = PhogalTheme {
    val names = listOf("Following Notification", "Latest Notification", "Community Notification")

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBgSecondary)
            //.wrapContentSize(Alignment.Center)
            .verticalScroll(rememberScrollState())
    ) {
        NotificationToggleItem(
            modifier = Modifier,
            name = names[0],
            isToggled = true
        ) {}

        Divider(modifier = Modifier.height(0.5.dp))
        NotificationToggleItem(
            modifier = Modifier,
            name = names[1],
            isToggled = false
        ) {}

        Divider(modifier = Modifier.height(0.5.dp))
        NotificationToggleItem(
            modifier = Modifier,
            name = names[2],
            isToggled = true
        ) {}

        Divider(modifier = Modifier.height(0.5.dp))
    }
}