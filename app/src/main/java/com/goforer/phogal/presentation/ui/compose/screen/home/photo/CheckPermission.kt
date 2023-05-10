package com.goforer.phogal.presentation.ui.compose.screen.home.photo

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
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    ),
    onPermissionGranted: () -> Unit,
    onPermissionDenied: (text: String) -> Unit
) {
    if (multiplePermissionsState.allPermissionsGranted) {
        // If all permissions are granted, then show screen with the feature enabled
        onPermissionGranted()
    } else {
        onPermissionDenied(
            getGivenPermissionsText(
                multiplePermissionsState.revokedPermissions,
                multiplePermissionsState.shouldShowRationale,
                stringResource(id = R.string.permission_rationale),
                stringResource(id = R.string.permission_denied)
            )
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
private fun getGivenPermissionsText(
    permissions: List<PermissionState>,
    shouldShowRationale: Boolean,
    rationaleText: String,
    deniedText: String
): String {
    val revokedPermissionsSize = permissions.size
    if (revokedPermissionsSize == 0) return ""

    val textToShow = StringBuilder().apply {
        append("The ")
    }

    for (i in permissions.indices) {
        textToShow.append(permissions[i].permission)
        when {
            revokedPermissionsSize > 1 && i == revokedPermissionsSize - 2 -> {
                textToShow.append(", and ")
            }
            i == revokedPermissionsSize - 1 -> {
                textToShow.append(" ")
            }
            else -> {
                textToShow.append(", ")
            }
        }
    }
    textToShow.append(if (revokedPermissionsSize == 1) "Permission is " else "Permissions are ")
    textToShow.append(
        if (shouldShowRationale) {
            rationaleText
        } else {
            deniedText
        }
    )

    return textToShow.toString()
}