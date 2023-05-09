package com.goforer.base.designsystem.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.goforer.phogal.presentation.ui.theme.ColorText2

@Composable
fun CardSnackBar(modifier: Modifier = Modifier, snackbarData: SnackbarData) {
    Card(
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color.Black),
        elevation = CardDefaults.cardElevation(defaultElevation =  2.dp),
        modifier = modifier
            .height(IntrinsicSize.Min)
            .padding(8.dp)
            .fillMaxWidth()
            .heightIn(48.dp)
    ) {
        Row(
            modifier = Modifier.paddingFromBaseline(28.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(Icons.Default.Notifications, contentDescription = "", modifier = Modifier.weight(1.5f))
            Spacer(Modifier.weight(1f))
            Text(modifier = Modifier.weight(8f).align(Alignment.CenterVertically)
                .padding(6.dp, 2.dp, 6.dp, 2.dp), text = snackbarData.visuals.message,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.Normal,
                fontSize = 15.sp,
                color = ColorText2,
                fontStyle = FontStyle.Normal)
        }
    }
}