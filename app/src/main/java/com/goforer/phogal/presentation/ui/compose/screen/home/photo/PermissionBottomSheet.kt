package com.goforer.phogal.presentation.ui.compose.screen.home.photo

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.goforer.phogal.R
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.PermissionState
import com.goforer.phogal.presentation.stateholder.uistate.home.photos.rememberPermissionState
import com.goforer.phogal.presentation.ui.theme.DarkGreen30
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PermissionBottomSheet(
    permissionState: PermissionState = rememberPermissionState(),
    onClicked: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = { permissionState.openBottomSheetState.value = false },
        sheetState = permissionState.bottomSheetState,
    ) {
        Column(
            modifier = Modifier.wrapContentHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = permissionState.deniedTextState.value,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium.copy(color = DarkGreen30),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Medium

            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                // Note: If you provide logic outside of onDismissRequest to remove the sheet,
                // you must additionally handle intended state cleanup, if any.
                onClick = {
                    permissionState.scope.launch { permissionState.bottomSheetState.hide() }.invokeOnCompletion {
                        if (!permissionState.bottomSheetState.isVisible) {
                            permissionState.openBottomSheetState.value = false
                        }
                    }

                    onClicked()
                }
            ) {
                Text(text = stringResource(id = R.string.permission_request))
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}