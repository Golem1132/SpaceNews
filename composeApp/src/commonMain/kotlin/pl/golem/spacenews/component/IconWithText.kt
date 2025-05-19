package pl.golem.spacenews.component

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun IconWithText(
    icon: DrawableResource,
    text: StringResource
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(painter = painterResource(icon), contentDescription = "$text icon")
        Text(text = stringResource(text))
    }
}