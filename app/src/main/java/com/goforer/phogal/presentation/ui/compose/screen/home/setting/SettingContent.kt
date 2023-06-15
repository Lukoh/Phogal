package com.goforer.phogal.presentation.ui.compose.screen.home.setting

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goforer.phogal.R
import com.goforer.phogal.data.model.local.home.setting.SettingItem
import com.goforer.phogal.data.model.remote.response.setting.Profile
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray1
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray9
import com.goforer.phogal.presentation.ui.theme.DarkGreen10

@Composable
fun SettingContent(
    modifier: Modifier = Modifier,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    onItemClicked: (index: Int) -> Unit
) {
    val topItems = listOf(
        SettingItem(stringResource(id = R.string.setting_bookmark), R.drawable.ic_bookmark),
        SettingItem(stringResource(id = R.string.setting_follower), R.drawable.ic_followers),
        SettingItem(stringResource(id = R.string.setting_alarm), R.drawable.ic_notification),
        SettingItem(stringResource(id = R.string.setting_privacy_policy), R.drawable.ic_privacy),
        SettingItem(stringResource(id = R.string.setting_app_info), R.drawable.ic_information)
    )
    val middleItems = listOf(
        SettingItem(stringResource(id = R.string.setting_send_feedback), R.drawable.ic_bookmark),
        SettingItem(stringResource(id = R.string.setting_give_start), R.drawable.ic_rating_start),
        SettingItem(stringResource(id = R.string.setting_homepage), R.drawable.ic_homepage)
    )
    val profile = Profile(0,"Lukoh", "남성", true,true, "lukoh.nam@gmail.com","https://avatars.githubusercontent.com/u/18302717?v=4", "sociable & gregarious", "+820101111-1111","", "Mar, 04, 1999","Lukoh is a tremendously capable and dedicated mobile SW professional. He has strong analytical and innovative skills which are further boosted by his solid technical background and his enthusiasm for technology. Lukoh works extremely well with colleagues, associates, and executives, adapting the analysis and communication techniques in order to accomplish the business objective. He is proficient in managing projects with consistent and successful results.\n" +
            "I am confident that his leadership experience and expertise in SW development will make him a good SW engineer who works with many colleagues, and should come up with creative awesome ideas.\n" +
            "He is an expert and architect in Android application development which has resulted in excellent reviews from all collegue. Lukoh is an honest and hardworking team lead, always willing to pitch in to help the team. He is efficient in planning projects, punctual in meeting deadlines, and conscientiously adheres to company standards and guidelines. On the other he understands the technical design and development, techniques and constraints. Lukoh has a true talent for communicating and negotiating where the outcome is beneficial for all involved. He is absolutely a valuable strength to any team as team lead!", false)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(ColorBgSecondary)
            //.wrapContentSize(Alignment.Center)
            .padding(0.dp, contentPadding.calculateTopPadding(), 0.dp, 0.dp)
            .verticalScroll(rememberScrollState())
    ) {
        SetProfileItem(
            modifier = Modifier, 
            profile = profile,
            onItemClicked = {}
        )
        Divider(thickness = 4.dp, color = ColorSystemGray9)
        Spacer(modifier = Modifier.height(4.dp))
        topItems.forEachIndexed { index, item ->
            SetItem(
                modifier = Modifier,
                index = index,
                text = item.text,
                drawable = item.drawable,
                onItemClicked = onItemClicked
            )
            if (index < topItems.size - 1)
                Divider()
        }
        Divider(thickness = 4.dp, color = ColorSystemGray9)
        middleItems.forEachIndexed { index, item ->
            SetItem(
                modifier = Modifier,
                index = index,
                text = item.text,
                drawable = item.drawable,
                onItemClicked = onItemClicked
            )
            if (index < middleItems.size - 1)
                Divider()
        }
        Divider(thickness = 4.dp, color = ColorSystemGray9)
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = stringResource(id = R.string.setting_terms_and_conditions),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = DarkGreen10,
            fontStyle = FontStyle.Normal,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp)
                .drawBehind {
                val strokeWidth =  0.5.dp.value * density
                val y = size.height.plus(16F)
                drawLine(
                    ColorSystemGray1,
                    Offset(0f, y),
                    Offset(size.width, y),
                    strokeWidth
                )
            }
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = stringResource(id = R.string.setting_view_contact),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = DarkGreen10,
            fontStyle = FontStyle.Normal,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp)
                .drawBehind {
                    val strokeWidth =  0.5.dp.value * density
                    val y = size.height.plus(16F)
                    drawLine(
                        ColorSystemGray1,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                }
        )
        Spacer(modifier = Modifier.height(28.dp))
        Text(
            text = stringResource(id = R.string.app_name) + " 0.2.0",
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Medium,
            fontSize = 13.sp,
            color = DarkGreen10,
            fontStyle = FontStyle.Normal,
            modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 16.dp)
                .drawBehind {
                    val strokeWidth =  0.5.dp.value * density
                    val y = size.height.plus(16F)
                    drawLine(
                        ColorSystemGray1,
                        Offset(0f, y),
                        Offset(size.width, y),
                        strokeWidth
                    )
                }
        )
        Spacer(modifier = Modifier.height(36.dp))
    }
}