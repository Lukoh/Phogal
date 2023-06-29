package com.goforer.phogal.presentation.ui.compose.screen.home.common.user

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.goforer.base.designsystem.animation.GenericCubicAnimationShape
import com.goforer.base.designsystem.animation.animateIconScale
import com.goforer.phogal.R
import com.goforer.phogal.data.model.remote.response.gallery.common.User
import com.goforer.phogal.presentation.analytics.TrackScreenViewEvent
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.UserInfoState
import com.goforer.phogal.presentation.stateholder.uistate.home.common.user.rememberUserInfoState
import com.goforer.phogal.presentation.ui.theme.DarkGreenGray10
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserInfoBottomSheet(
    userInfoState: UserInfoState = rememberUserInfoState(),
    user: User,
    showUserInfoBottomSheet: Boolean,
    onDismissedRequest: (Boolean) -> Unit
) {
    TrackScreenViewEvent(screenName = "View_User_Profile_BottomSheet")
    GenericCubicAnimationShape(
        visible = showUserInfoBottomSheet,
        duration = 400
    ) { animatedShape, visible ->
        if (visible) {
            ModalBottomSheet(
                onDismissRequest = {
                    userInfoState.scope.launch {
                        userInfoState.bottomSheetState.hide()
                    }.invokeOnCompletion {
                        if (!userInfoState.bottomSheetState.isVisible) {
                            userInfoState.openBottomSheetState.value = false
                        }
                    }

                    onDismissedRequest(false)
                },
                sheetState = userInfoState.bottomSheetState,
                shape = animatedShape,
                tonalElevation = 8.dp
            ) {
                Column(
                    modifier = Modifier.wrapContentHeight(),
                    horizontalAlignment = Alignment.Start,
                ) {
                    ProfileItem(
                        image = user.profile_image.medium,
                        name = user.name,
                        nameColor = DarkGreenGray10,
                        position = 9,
                        onClicked = {}
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    getProfileInfoItems(user).forEachIndexed { _, item ->
                        UserInfoItem(
                            text = item.text,
                            textColor = DarkGreenGray10,
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
                            scope = userInfoState.scope,
                            bottomSheetState = userInfoState.bottomSheetState,
                            openBottomSheetState = userInfoState.openBottomSheetState,
                            firstName = user.first_name,
                            onDismissedRequest = onDismissedRequest
                        )
                    }

                    Spacer(modifier = Modifier.height(36.dp))
                }
            }
        }
    }
}