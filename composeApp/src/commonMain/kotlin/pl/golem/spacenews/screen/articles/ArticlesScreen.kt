package pl.golem.spacenews.screen.articles

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
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
fun ArticlesScreen(navController: NavController) {
    val viewModel = koinViewModel<ArticlesViewModel>()
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    val searchQuery = viewModel.searchQuery.collectAsState()
    val isSearchExpanded = remember { mutableStateOf(false) }
    val filterMenuState = rememberFilterMenuState()
    val datePickerState = rememberDatePickerState()
    val filterPeriod = viewModel.period.collectAsState()
    val filterSort = viewModel.ordering.collectAsState()
    val listState = rememberLazyListState()
    val articlesList = viewModel.currentPage.collectAsState()
    val screenState = viewModel.screenState.collectAsState()

    LaunchedEffect(key1 = listState.canScrollForward) {
        if (!listState.canScrollForward && screenState.value == ArticleScreenStates.IDLE)
            viewModel.tryFetchingData(ArticleScreenStates.LOADING_PAGE)
    }

    LaunchedEffect(key1 = screenState) {
        println(screenState.value.name)
    }

    Scaffold(
        topBar = {
            SearchBar(
                modifier = Modifier.fillMaxWidth()
                    .padding(horizontal = if (isSearchExpanded.value) 0.dp else 16.dp),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = searchQuery.value,
                        onQueryChange = {
                            viewModel.updateSearch(it)
                        },
                        onSearch = {
                            isSearchExpanded.value = false
                        },
                        onExpandedChange = {
                            isSearchExpanded.value = it
                        },
                        expanded = isSearchExpanded.value,
                        leadingIcon = {
                            Icon(
                                modifier = Modifier.clickable {
                                    if (isSearchExpanded.value) {
                                        isSearchExpanded.value = false
                                    } else {
                                        scope.launch {
                                            if (scaffoldState.drawerState.isOpen)
                                                scaffoldState.drawerState.close()
                                            else scaffoldState.drawerState.open()
                                        }
                                    }
                                },
                                imageVector = if (isSearchExpanded.value)
                                    Icons.AutoMirrored.Default.ArrowBack
                                else Icons.Default.Menu,
                                contentDescription = ""
                            )
                        },
                        trailingIcon = {
                            if (isSearchExpanded.value)
                                Icon(
                                    modifier = Modifier.clickable {
                                        scope.launch(Dispatchers.IO) {
                                            filterMenuState.expanded = !filterMenuState.expanded
                                        }
                                    },
                                    imageVector = Icons.Default.Info, contentDescription = "Filters"
                                )
                        }
                    )
                },
                expanded = isSearchExpanded.value,
                onExpandedChange = {
                    isSearchExpanded.value = it
                }, content = {

                }
            )
        },
        scaffoldState = scaffoldState,
        drawerContent = {},
        modifier = Modifier.systemBarsPadding()
    ) {
        if (filterMenuState.expanded) {
            BasicAlertDialog(onDismissRequest = {
                filterMenuState.expanded = false
            }) {
                Surface(
                    modifier = Modifier.wrapContentWidth().wrapContentHeight()
                        .clip(RoundedCornerShape(20))
                ) {
                    Column(Modifier.fillMaxWidth().padding(horizontal = 5.dp, vertical = 10.dp)) {
                        Row(modifier = Modifier.fillMaxWidth()) {
                            ExposedDropdownMenuBox(
                                expanded = filterMenuState.isDialogVisible && filterMenuState.filterType == FilterType.SORT,
                                onExpandedChange = {
                                    filterMenuState.isDialogVisible =
                                        !filterMenuState.isDialogVisible
                                    filterMenuState.filterType = FilterType.SORT
                                },
                                content = {
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(5.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text("Sort")
                                        Text(
                                            text = if (filterSort.value != null) {
                                                stringResource(filterSort.value!!.value)
                                            } else {
                                                ""
                                            }
                                        )
                                    }
                                    ExposedDropdownMenu(
                                        modifier = Modifier.fillMaxWidth(),
                                        expanded = filterMenuState.isDialogVisible && filterMenuState.filterType == FilterType.SORT,
                                        onDismissRequest = {
                                            filterMenuState.isDialogVisible = false
                                        }) {
                                        DropdownMenuItem(
                                            text = { Text("Published at") },
                                            onClick = {
                                                viewModel.updateOrdering(Ordering.PublishedAt)
                                                filterMenuState.isDialogVisible = false
                                            })
                                        DropdownMenuItem(
                                            text = { Text("Published at (descending)") },
                                            onClick = {
                                                viewModel.updateOrdering(Ordering.PublishedAtDesc)
                                                filterMenuState.isDialogVisible = false
                                            })
                                        DropdownMenuItem(text = { Text("Updated at") }, onClick = {
                                            viewModel.updateOrdering(Ordering.UpdatedAt)
                                            filterMenuState.isDialogVisible = false
                                        })
                                        DropdownMenuItem(
                                            text = { Text("Updated at (descending)") }, onClick = {
                                                viewModel.updateOrdering(Ordering.UpdatedAtDesc)
                                                filterMenuState.isDialogVisible = false
                                            })
                                    }
                                }
                            )
                        }
                        Row(modifier = Modifier.fillMaxWidth().padding(5.dp).clickable {
                            filterMenuState.filterType = FilterType.FROM
//                            filterMenuState.expanded = false
                            filterMenuState.isDialogVisible = true
                        }) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Since")
                                Text(
                                    text = if (filterPeriod.value.from != null) {
                                        filterPeriod.value.from!!.date.toString()
                                    } else {
                                        ""
                                    }
                                )
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth().padding(5.dp).clickable {
                            filterMenuState.filterType = FilterType.TO
//                            filterMenuState.expanded = false
                            filterMenuState.isDialogVisible = true
                        }) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("To")
                                Text(
                                    text = if (filterPeriod.value.to != null) {
                                        filterPeriod.value.to!!.date.toString()
                                    } else {
                                        ""
                                    }
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(5.dp),
                            horizontalArrangement =
                                if (filterSort.value != null || filterPeriod.value.to != null || filterPeriod.value.from != null)
                                    Arrangement.SpaceBetween
                                else Arrangement.End
                        ) {
                            if (filterSort.value != null || filterPeriod.value.to != null || filterPeriod.value.from != null) {
                                Button(
                                    onClick = {
                                        viewModel.updatePeriod(PublishDate(null, null))
                                        viewModel.updateOrdering(null)
                                    }
                                ) {
                                    Text("Clear")
                                }
                            }

                            Button(
                                onClick = {

                                }
                            ) {
                                Text("Filter")
                            }
                        }
                    }
                }
            }
        }

        PullToRefreshBox(
            screenState.value == ArticleScreenStates.REFRESH,
            onRefresh = {
                scope.launch(Dispatchers.IO) {
                    viewModel.tryFetchingData(ArticleScreenStates.REFRESH)
                }
            },
            modifier = Modifier.fillMaxSize().padding(it),
        ) {
            LazyColumn(state = listState, modifier = Modifier.fillMaxSize()) {
                itemsIndexed(items = articlesList.value) { index, result ->
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        ListItem(
                            modifier = Modifier.padding(10.dp).clip(RoundedCornerShape(10))
                                .background(color = MaterialTheme.colorScheme.surfaceColorAtElevation(1.dp),
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

        if (filterMenuState.isDialogVisible && filterMenuState.filterType != FilterType.SORT) {
            DatePickerDialog(
                onDismissRequest = {
                    when (filterMenuState.filterType) {
                        FilterType.FROM -> {
                            viewModel.updatePeriod(
                                filterPeriod.value.copy(
                                    from = if (datePickerState.selectedDateMillis != null)
                                        Instant.fromEpochMilliseconds(
                                            datePickerState.selectedDateMillis ?: 0
                                        ).toLocalDateTime(
                                            TimeZone.UTC
                                        ) else null
                                )
                            )
                        }

                        FilterType.TO -> {
                            viewModel.updatePeriod(
                                filterPeriod.value.copy(
                                    to = if (datePickerState.selectedDateMillis != null)
                                        Instant.fromEpochMilliseconds(
                                            datePickerState.selectedDateMillis ?: 0
                                        ).toLocalDateTime(
                                            TimeZone.UTC
                                        ) else null
                                )
                            )
                        }

                        else -> {}
                    }
                    filterMenuState.isDialogVisible = false
                    datePickerState.selectedDateMillis = null
                    filterMenuState.filterType = FilterType.NONE
                },
                confirmButton = {
                    Text(modifier = Modifier.padding(10.dp).clickable {
                        when (filterMenuState.filterType) {
                            FilterType.FROM -> {
                                viewModel.updatePeriod(
                                    filterPeriod.value.copy(
                                        from = if (datePickerState.selectedDateMillis != null)
                                            Instant.fromEpochMilliseconds(
                                                datePickerState.selectedDateMillis ?: 0
                                            ).toLocalDateTime(
                                                TimeZone.UTC
                                            ) else null
                                    )
                                )
                            }

                            FilterType.TO -> {
                                viewModel.updatePeriod(
                                    filterPeriod.value.copy(
                                        to = if (datePickerState.selectedDateMillis != null)
                                            Instant.fromEpochMilliseconds(
                                                datePickerState.selectedDateMillis ?: 0
                                            ).toLocalDateTime(
                                                TimeZone.UTC
                                            ) else null
                                    )
                                )
                            }

                            else -> {}
                        }
                        filterMenuState.isDialogVisible = false
                        datePickerState.selectedDateMillis = null
                        filterMenuState.filterType = FilterType.NONE
                    }, text = "OK")
                },
            ) {
                DatePicker(datePickerState)
            }
        }
    }
}