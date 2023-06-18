package com.goforer.phogal.presentation.ui.compose.screen.home.setting.following

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandIn
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.shrinkOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goforer.base.designsystem.animation.animateIconScale
import com.goforer.base.designsystem.component.IconButton
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.presentation.stateholder.uistate.home.setting.following.FollowingUserItemState
import com.goforer.phogal.presentation.stateholder.uistate.home.setting.following.rememberFollowingUserItemState
import com.goforer.phogal.presentation.ui.compose.screen.home.common.user.ProfileItem
import com.goforer.phogal.presentation.ui.compose.screen.home.common.user.UserInfoItem
import com.goforer.phogal.presentation.ui.compose.screen.home.common.user.getProfileInfoItems
import com.goforer.phogal.presentation.ui.theme.Blue70
import com.goforer.phogal.presentation.ui.theme.Blue75
import com.goforer.phogal.presentation.ui.theme.DarkGreenGray99

@Composable
fun FollowingUsersItem(
    modifier: Modifier = Modifier,
    state: FollowingUserItemState = rememberFollowingUserItemState(),
    onViewPhotos: (name: String, firstName: String, lastName: String, username: String) -> Unit,
    onOpenWebView: (firstName: String, url: String?) -> Unit
) {
    val user = state.user.value as User
    val verticalPadding = if (state.index.value == 0)
        2.dp
    else
        4.dp

    AnimatedVisibility(
        visible = true,
        modifier = modifier,
        enter = scaleIn(transformOrigin = TransformOrigin(0f, 0f)) +
                fadeIn() + expandIn(expandFrom = Alignment.TopStart),
        exit = scaleOut(transformOrigin = TransformOrigin(0f, 0f)) +
                fadeOut() + shrinkOut(shrinkTowards = Alignment.TopStart)
    ) {
        Card(
            modifier = modifier.padding(vertical = verticalPadding),
            colors = CardDefaults.cardColors(
                contentColor = Blue70,
                containerColor =  if (state.isClicked.value)
                    Blue75
                else
                    Blue70,
                disabledContentColor = Blue70,
                disabledContainerColor = Blue70
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 8.dp,
                pressedElevation = 2.dp,
                focusedElevation = 4.dp
            )
        ) {
            Column(
                modifier = Modifier.wrapContentHeight(),
                horizontalAlignment = Alignment.Start,
            ) {
                Spacer(modifier = Modifier.height(8.dp))
                ProfileItem(
                    image = user.profile_image.medium,
                    name = user.name,
                    nameColor = Color.White,
                    position = 9,
                    onClicked = {
                        onViewPhotos(
                            user.username,
                            user.first_name,
                            user.last_name!!,
                            user.username
                        )
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                getProfileInfoItems(user).forEachIndexed { _, item ->
                    UserInfoItem(
                        text = item.text,
                        textColor = Color.White,
                        painter = item.painter,
                        position = item.position
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }

                Row(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val animationIconScale = animateIconScale(inputScale = 0.6F, position = 1, delay = 150L)

                    Image(
                        painter = painterResource(id = R.drawable.ic_portfolio),
                        contentDescription = "Following",
                        modifier = Modifier
                            .size(22.dp)
                            .padding(horizontal = 4.dp)
                            .graphicsLayer {
                                scaleX = animationIconScale
                                scaleY = animationIconScale
                            }
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    ShowPortfolioButton(
                        firstName = user.first_name,
                        onOpenWebView = {
                            onOpenWebView(user.first_name, user.portfolio_url)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(36.dp))
            }
        }
    }
}

@Composable
fun ShowPortfolioButton(
    firstName: String,
    onOpenWebView: () -> Unit
) {
    IconButton(
        modifier = Modifier.padding(horizontal = 2.dp),
        height = 32.dp,
        colors = ButtonDefaults.buttonColors(
            containerColor = Blue75,
            contentColor = Blue75
        ),
        onClick = {
            onOpenWebView()
        },
        icon = {
            Icon(
                imageVector = Icons.Default.OpenInBrowser,
                contentDescription = null,
            )
        },
        text = {
            Text(
                text = stringResource(id = R.string.user_info_portfolio, firstName),
                modifier = Modifier
                    .padding(horizontal = 8.dp)
                    .clickable {},
                color = DarkGreenGray99,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                fontStyle = FontStyle.Normal,
                style = MaterialTheme.typography.titleMedium
            )
        }
    )
}