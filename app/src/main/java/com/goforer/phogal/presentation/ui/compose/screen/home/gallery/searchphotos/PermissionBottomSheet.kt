package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.searchphotos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goforer.phogal.R
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.PermissionState
import com.goforer.phogal.presentation.stateholder.uistate.home.gallery.searchphotos.rememberPermissionState
import com.goforer.phogal.presentation.ui.theme.Blue20
import com.goforer.phogal.presentation.ui.theme.ColorSystemGray8
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionBottomSheet(
    state: PermissionState = rememberPermissionState(),
    onDismissedRequest: () -> Unit,
    onClicked: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = {
            state.scope.launch {
                state.bottomSheetState.hide()
            }.invokeOnCompletion {
                if (!state.bottomSheetState.isVisible) {
                    state.openBottomSheetState.value = false
                }
            }

            onDismissedRequest()
        },
        sheetState = state.bottomSheetState,
    ) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = ParagraphStyle(
                            lineHeight = 30.sp,
                            textAlign = TextAlign.Center
                        )
                    ) {
                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 22.sp,
                                color = Blue20,
                                baselineShift = BaselineShift.Superscript
                            )
                        ) {
                            append(
                                state.rationaleTextState.value.substring(
                                    0, state.rationaleTextState.value.indexOf("Setting")
                                )
                            )
                        }

                        withStyle(
                            style = SpanStyle(
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp,
                                color = ColorSystemGray8
                            )
                        ) {
                            append(
                                state.rationaleTextState.value.substring(
                                    state.rationaleTextState.value.indexOf("Setting"),
                                    state.rationaleTextState.value.length
                                )
                            )
                        }
                    }
                },
                modifier = Modifier.padding(16.dp),
                fontFamily = FontFamily.SansSerif
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                // you must additionally handle intended state cleanup, if any.
                onClick = {
                    state.scope.launch { state.bottomSheetState.hide() }.invokeOnCompletion {
                        if (!state.bottomSheetState.isVisible) {
                            state.openBottomSheetState.value = false
                        }
                    }

                    onClicked()
                }
            ) {
                Text(text = stringResource(id = R.string.permission_request))
            }
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}