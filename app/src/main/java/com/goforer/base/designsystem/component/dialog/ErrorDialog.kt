package com.goforer.base.designsystem.component.dialog

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.goforer.phogal.R
import com.goforer.phogal.presentation.ui.theme.Blue60
import com.goforer.phogal.presentation.ui.theme.PhogalTheme

@Composable
fun ErrorDialog(
    title: String,
    text: String,
    onDismiss: () -> Unit
) {
    Dialog(
        onDismissRequest = {
            onDismiss()
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shadowElevation = 4.dp,
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .background(color = Blue60),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        modifier = Modifier
                            .padding(top = 16.dp, bottom = 16.dp),
                        painter = painterResource(id = R.drawable.ic_error_dialog),
                        contentDescription = "Error",
                        alignment = Alignment.Center
                    )
                }

                Text(
                    modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                    text = title,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )

                Text(
                    modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                    text = text,
                    textAlign = TextAlign.Center,
                    style = TextStyle(
                        fontFamily = FontFamily.SansSerif,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Normal
                    )
                )

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 36.dp, start = 36.dp, end = 36.dp, bottom = 36.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35898f)),
                    onClick = {
                        onDismiss()
                    }) {
                    Text(
                        text = stringResource(id = R.string.confirm),
                        color = Color.White,
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    )
                }
            }
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
fun ErrorDialogPreview(modifier: Modifier = Modifier) {
    PhogalTheme {
        Dialog(
            onDismissRequest = {
            }
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(),
                shadowElevation = 4.dp,
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .background(color = Color(0xFF35898f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            modifier = Modifier
                                .padding(top = 16.dp, bottom = 16.dp),
                            painter = painterResource(id = R.drawable.ic_error_dialog),
                            contentDescription = "Error",
                            alignment = Alignment.Center
                        )
                    }

                    Text(
                        modifier = Modifier.padding(top = 16.dp, bottom = 16.dp),
                        text = "Network Error",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    Text(
                        modifier = Modifier.padding(start = 12.dp, end = 12.dp),
                        text = "OAuth Token : Token is invalid",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            fontFamily = FontFamily.SansSerif,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Normal
                        )
                    )

                    Button(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 36.dp, start = 36.dp, end = 36.dp, bottom = 36.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF35898f)),
                        onClick = {
                        }) {
                        Text(
                            text = stringResource(id = R.string.confirm),
                            color = Color.White,
                            style = TextStyle(
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            }
        }
    }
}