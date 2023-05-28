package com.goforer.phogal.presentation.ui.compose.screen.home.gallery.searchphotos

import android.Manifest
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.goforer.phogal.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CheckPermission(
    multiplePermissionsState: MultiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.CAMERA
        )
    ),
    onPermissionGranted: () -> Unit,
    onPermissionNotGranted: (text: String) -> Unit
) {
    if (multiplePermissionsState.allPermissionsGranted) {
        // If all permissions are granted, then show screen with the feature enabled
        onPermissionGranted()
    } else {
        onPermissionNotGranted(
            getGivenPermissionsText(
                multiplePermissionsState.revokedPermissions,
                stringResource(id = R.string.permission_rationale),
                stringResource(id = R.string.permission_location_request),
                stringResource(id = R.string.permission_camera_request)
            )
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun getGivenPermissionsText(
    permissions: List<PermissionState>,
    rationaleText: String,
    requestLocationText: String,
    requestCameraText: String
): String {
    val revokedPermissionsSize = permissions.size
    val textToShow = StringBuilder().apply {
        append("")
    }

    if (revokedPermissionsSize == 0) return ""

    for (i in permissions.indices) {
        when(permissions[i].permission) {
            Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION -> {
                textToShow.append(requestLocationText)
                textToShow.append("\n")
            }
            Manifest.permission.CAMERA -> {
                textToShow.append(requestCameraText)
                textToShow.append("\n")
            }
        }
    }

    textToShow.append("\n")
    textToShow.append(if (revokedPermissionsSize == 1) "Setting Permission is " else "Setting Permissions are ")
    textToShow.append(rationaleText)

    return textToShow.toString()
}