package pl.golem.spacenews.screen.blogs

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.compose.viewmodel.koinViewModel
import pl.golem.spacenews.component.ListItem
import pl.golem.spacenews.component.RefreshableList
import pl.golem.spacenews.navigation.WebScreen
import pl.golem.spacenews.screen.articles.ArticleScreenStates

@Composable
fun BlogsScreen(modifier: Modifier, navController: NavController) {
    val viewModel = koinViewModel<BlogsViewModel>()
    val listState = rememberLazyListState()
    val blogsList = viewModel.currentPage.collectAsState()
    val screenState = viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = listState.canScrollForward) {
        if (!listState.canScrollForward && screenState.value == ArticleScreenStates.IDLE)
            viewModel.tryFetchingData(ArticleScreenStates.LOADING_PAGE)
    }

    RefreshableList(
        modifier = modifier,
        isRefreshing = screenState.value == ArticleScreenStates.REFRESH,
        onRefresh = { viewModel.tryFetchingData(ArticleScreenStates.REFRESH) },
        isLoading = blogsList.value.isEmpty() && (screenState.value == ArticleScreenStates.LOADING_PAGE || screenState.value == ArticleScreenStates.INIT),
        listState = listState,
        itemsForList = blogsList.value,
        item = { result ->
            ListItem(
                modifier = Modifier.padding(10.dp).clip(RoundedCornerShape(10))
                    .background(
                        color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
                        shape = RoundedCornerShape(10)
                    )
                    .combinedClickable(
                        onClick = {
                            navController.navigate(WebScreen(result.url ?: ""))
                        },
                        onLongClick = {

                        }
                    ),
                imageUrl = result.imageUrl ?: "",
                title = result.title,
                site = result.newsSite ?: ""
            )
        }

    )
}