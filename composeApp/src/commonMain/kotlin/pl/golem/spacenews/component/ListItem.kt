package pl.golem.spacenews.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import org.jetbrains.compose.resources.painterResource
import spacenews.composeapp.generated.resources.Res
import spacenews.composeapp.generated.resources.compose_multiplatform
import spacenews.composeapp.generated.resources.spacenewsnophoto

@Composable
fun ListItem(
    modifier: Modifier = Modifier,
    imageUrl: String,
    title: String,
    site: String,

) {
    val asyncPainter = rememberAsyncImagePainter(model = imageUrl)
    val painterState = asyncPainter.state.collectAsState()
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (painterState.value) {
            is AsyncImagePainter.State.Success -> Image(
                modifier = Modifier.fillMaxWidth()
                    .aspectRatio(asyncPainter.intrinsicSize.width / asyncPainter.intrinsicSize.height),
                painter = asyncPainter,
                contentDescription = "Main image"
            )

            is AsyncImagePainter.State.Error -> Icon(
                painterResource(Res.drawable.spacenewsnophoto),
                ""
            )

            else ->
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
        }
        Column(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h6
            )
            Text(text = site)
        }
    }

}
