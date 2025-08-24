package pl.golem.spacenews.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> RefreshableList(
    modifier: Modifier,
    isRefreshing: Boolean,
    onRefresh: suspend () -> Unit,
    isLoading: Boolean,
    listState: LazyListState,
    itemsForList: List<T>,
    item: @Composable (T) -> Unit
) {
    val scope = rememberCoroutineScope()
    if (isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator()
        }
    } else {
        PullToRefreshBox(
            isRefreshing,
            onRefresh = {
                scope.launch(Dispatchers.IO) {
                    onRefresh()
                }
            },
            modifier = modifier,
        ) {
            LazyColumn(
                state = listState, modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(items = itemsForList) { index, result ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        item(result)
                        if (index == itemsForList.lastIndex) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}