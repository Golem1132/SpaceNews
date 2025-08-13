package pl.golem.spacenews.screen.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import pl.golem.spacenews.component.ListItem
import pl.golem.spacenews.model.Ordering
import pl.golem.spacenews.model.PublishDate
import pl.golem.spacenews.navigation.WebScreen

import pl.golem.spacenews.state.FilterType
import pl.golem.spacenews.state.rememberFilterMenuState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun ArticlesScreen(modifier: Modifier, navController: NavController) {
    val viewModel = koinViewModel<ArticlesViewModel>()
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    val articlesList = viewModel.currentPage.collectAsState()
    val screenState = viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = listState.canScrollForward) {
        if (!listState.canScrollForward && screenState.value == ArticleScreenStates.IDLE)
            viewModel.tryFetchingData(ArticleScreenStates.LOADING_PAGE)
    }

    if (articlesList.value.isEmpty() && (screenState.value == ArticleScreenStates.LOADING_PAGE || screenState.value == ArticleScreenStates.INIT)) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        PullToRefreshBox(
            screenState.value == ArticleScreenStates.REFRESH,
            onRefresh = {
                scope.launch(Dispatchers.IO) {
                    viewModel.tryFetchingData(ArticleScreenStates.REFRESH)
                }
            },
            modifier = modifier,
        ) {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                itemsIndexed(items = articlesList.value) { index, result ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
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
                        if (index == articlesList.value.lastIndex) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}