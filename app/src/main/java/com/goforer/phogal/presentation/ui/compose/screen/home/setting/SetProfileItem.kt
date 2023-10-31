package com.goforer.phogal.presentation.ui.compose.screen.home.setting

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.size.Size
import com.goforer.base.designsystem.component.IconContainer
import com.goforer.base.designsystem.component.ImageCrossFade
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.setting.ProfileUiState
import com.goforer.phogal.presentation.ui.theme.Black
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray1

@Composable
fun SetProfileItem(
    modifier: Modifier = Modifier,
    profileUiState: ProfileUiState,
    onItemClicked: (profileUiState: ProfileUiState) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(8.dp)
            .background(ColorBgSecondary)
            .wrapContentHeight(Alignment.CenterVertically)
            .fillMaxWidth()
            .height(68.dp)
            .clickable {
                onItemClicked(profileUiState)
            }
    ) {
        IconContainer(64.dp) {
            Box {
                val painter = loadImagePainter(
                    data = profileUiState.profileImage,
                    size = Size.ORIGINAL
                )

                ImageCrossFade(painter = painter, durationMillis = null)
                Image(
                    painter = painter,
                    contentDescription = "Profile",
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.secondary,
                            CircleShape
                        ),
                    Alignment.CenterStart,
                    contentScale = ContentScale.Crop
                )

                if (painter.state is AsyncImagePainter.State.Loading) {
                    val preloadPainter = loadImagePainter(
                        data = R.drawable.ic_profile_logo,
                        size = Size.ORIGINAL
                    )

                    Image(
                        painter = preloadPainter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.Center),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier
            .height(IntrinsicSize.Min)
            .widthIn(180.dp)
        ) {
            Text(
                profileUiState.name,
                modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp),
                color = Black,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                profileUiState.email,
                modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp),
                color = ColorSystemGray1,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                fontStyle = FontStyle.Normal,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        Button(
            modifier = Modifier.padding(horizontal = 24.dp),
            onClick = {}
        ) {
            Text(text = stringResource(id = R.string.setting_profile_edit))
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
fun SetProfileItemPreview(modifier: Modifier = Modifier) {
    val profile = ProfileUiState(0,"Lukoh", "남성",
        favor = true,
        followed = true,
        email = "lukoh.nam@gmail.com",
        profileImage = "https://avatars.githubusercontent.com/u/18302717?v=4",
        personality = "sociable & gregarious",
        cellphone = "+820101111-1111",
        address = "",
        birthday = "Mar, 04, 1999",
        reputation = "Lukoh is a tremendously capable and dedicated mobile SW professional. He has strong analytical and innovative skills which are further boosted by his solid technical background and his enthusiasm for technology. Lukoh works extremely well with colleagues, associates, and executives, adapting the analysis and communication techniques in order to accomplish the business objective. He is proficient in managing projects with consistent and successful results.\n" +
                "I am confident that his leadership experience and expertise in SW development will make him a good SW engineer who works with many colleagues, and should come up with creative awesome ideas.\n" +
                "He is an expert and architect in Android application development which has resulted in excellent reviews from all collegue. Lukoh is an honest and hardworking team lead, always willing to pitch in to help the team. He is efficient in planning projects, punctual in meeting deadlines, and conscientiously adheres to company standards and guidelines. On the other he understands the technical design and development, techniques and constraints. Lukoh has a true talent for communicating and negotiating where the outcome is beneficial for all involved. He is absolutely a valuable strength to any team as team lead!",
        deleted = false
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .background(ColorBgSecondary)
            .wrapContentHeight(Alignment.CenterVertically)
            .fillMaxWidth()
            .height(86.dp)
            .clickable {}
    ) {
        IconContainer(64.dp) {
            Box {
                val painter = loadImagePainter(
                    data = profile.profileImage,
                    size = Size.ORIGINAL
                )

                ImageCrossFade(painter = painter, durationMillis = null)
                Image(
                    painter = painter,
                    contentDescription = "ComposeTest",
                    modifier = Modifier
                        .padding(4.dp)
                        .fillMaxSize()
                        .clip(CircleShape)
                        .border(
                            1.dp,
                            MaterialTheme.colorScheme.secondary,
                            CircleShape
                        ),
                    Alignment.CenterStart,
                    contentScale = ContentScale.Crop
                )

                if (painter.state is AsyncImagePainter.State.Loading) {
                    val preloadPainter = loadImagePainter(
                        data = R.drawable.ic_profile_logo,
                        size = Size.ORIGINAL
                    )

                    Image(
                        painter = preloadPainter,
                        contentDescription = null,
                        modifier = Modifier
                            .size(64.dp)
                            .align(Alignment.Center),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier
            .height(IntrinsicSize.Min)
            .widthIn(180.dp)
        ) {
            Text(
                "Lukoh",
                modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp),
                color = Black,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontStyle = FontStyle.Normal,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                profile.email,
                modifier = Modifier.padding(0.dp, 4.dp, 0.dp, 0.dp),
                color = ColorSystemGray1,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 13.sp,
                fontStyle = FontStyle.Normal,
                style = MaterialTheme.typography.titleMedium
            )
        }
        Spacer(modifier = Modifier.weight(1F))
        Button(
            modifier = Modifier.padding(horizontal = 24.dp),
            onClick = {}
        ) {
            Text(text = stringResource(id = R.string.setting_profile_edit))
        }
    }
}