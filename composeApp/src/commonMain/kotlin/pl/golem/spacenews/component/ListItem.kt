package pl.golem.spacenews.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import spacenews.composeapp.generated.resources.Res
import spacenews.composeapp.generated.resources.spacenewsnophoto

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    site: String
) {
    val asyncPainter = rememberAsyncImagePainter(model = imageUrl)
    val painterState = asyncPainter.state.collectAsState()

    when (painterState.value) {
        is AsyncImagePainter.State.Success -> {
            Box(modifier = modifier, contentAlignment = Alignment.BottomStart) {
                Image(
                    modifier = Modifier.fillMaxWidth()
                        .aspectRatio(asyncPainter.intrinsicSize.width / asyncPainter.intrinsicSize.height),
                    painter = asyncPainter,
                    contentDescription = "Main image"
                )
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0f, 0f, 0f, 0.7f), Color.Black
                                ),
                            )
                        )
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                    Text(modifier = Modifier.padding(horizontal = 10.dp), text = site, color = Color.White)
                }
            }
        }

        is AsyncImagePainter.State.Error -> {
            val painter = painterResource(Res.drawable.spacenewsnophoto)
            Column(
                modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    modifier = Modifier.fillMaxWidth(0.5f)
                        .height(painter.intrinsicSize.height.dp / 2),
                    painter = painter,
                    contentDescription = ""
                )
                Text("Error occurred while loading image")
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0f, 0f, 0f, 0.7f), Color.Black
                                ),
                            )
                        )
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                    Text(modifier = Modifier.padding(horizontal = 10.dp), text = site, color = Color.White)
                }
            }
        }

        else -> {
            Column(
                modifier = modifier,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(0.5f).aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
                Column(
                    modifier = Modifier.fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0f, 0f, 0f, 0.7f), Color.Black
                                ),
                            )
                        )
                        .padding(10.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ) {
                    Text(
                        text = title,
                        color = Color.White,
                        style = MaterialTheme.typography.h6
                    )
                    Text(
                        modifier = Modifier.padding(horizontal = 10.dp),
                        text = site, color = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewListItem() {
    ListItem(
        modifier = Modifier.padding(10.dp).clip(RoundedCornerShape(10))
            .background(
                color = androidx.compose.material3.MaterialTheme.colorScheme.surfaceColorAtElevation(
                    1.dp
                ),
                shape = RoundedCornerShape(10)
            ),
        imageUrl = "https://www.esa.int/var/esa/storage/images/esa_multimedia/videos/2025/08/ariane_6_preparing_flight_va264_for_liftoff/26837507-2-eng-GB/Ariane_6_preparing_flight_VA264_for_liftoff_card_full.jpg",
        title = "Test Title",
        site = "Test site"
    )
}
