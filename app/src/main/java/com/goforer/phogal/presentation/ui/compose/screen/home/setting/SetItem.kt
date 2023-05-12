package com.goforer.phogal.presentation.ui.compose.screen.home.setting

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
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
import coil.decode.SvgDecoder
import coil.size.Size
import com.goforer.base.designsystem.component.loadImagePainter
import com.goforer.phogal.R
import com.goforer.phogal.presentation.ui.theme.ColorBgSecondary
import com.goforer.phogal.presentation.ui.theme.DarkGreen10

@Composable
fun SetItem(
    modifier: Modifier = Modifier,
    index: Int,
    text: String,
    drawable: Any,
    onItemClicked: (index: Int) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .background(ColorBgSecondary)
            .wrapContentHeight(Alignment.CenterVertically)
            .fillMaxWidth()
            .heightIn(68.dp)
            .clickable {
                onItemClicked(index)
            }
    ) {
        Image(
            painter = loadImagePainter(
                data = drawable,
                factory = SvgDecoder.Factory(),
                size = Size.ORIGINAL
            ),
            contentDescription = "item",
            modifier = Modifier
                .align(Alignment.CenterVertically)
                .wrapContentSize()
                .clickable { },
            Alignment.Center
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = text,
            modifier = Modifier.align(Alignment.CenterVertically),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            color = DarkGreen10,
            fontStyle = FontStyle.Normal
        )
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
fun SetItemPreview(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .background(ColorBgSecondary)
            .wrapContentHeight(Alignment.CenterVertically)
            .fillMaxWidth()
            .heightIn(48.dp, 56.dp)
            .background(ColorBgSecondary)
            .clickable {}
    ) {
        val painter = loadImagePainter(
            data = R.drawable.ic_setting,
            factory = SvgDecoder.Factory(),
            size = Size.ORIGINAL
        )

        Image(
            painter = painter,
            contentDescription = "item_image",
            modifier = Modifier
                .padding(4.dp)
                .fillMaxSize()
                .clip(CircleShape),
            Alignment.CenterStart,
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            modifier = Modifier.align(Alignment.CenterVertically),
            text = stringResource(id = R.string.setting_system_permission),
            fontFamily = FontFamily.SansSerif,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            color = DarkGreen10,
            fontStyle = FontStyle.Normal
        )
    }
}