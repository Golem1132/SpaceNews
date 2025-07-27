package pl.golem.spacenews.screen.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.golem.spacenews.api.ArticlesService
import pl.golem.spacenews.api.Request
import pl.golem.spacenews.data.remote.Result
import pl.golem.spacenews.model.Ordering
import pl.golem.spacenews.model.PublishDate

enum class ArticleScreenStates {
    INIT,
    LOADING_PAGE,
    REFRESH,
    IDLE
}


class ArticlesViewModel(
    private val repository: ArticlesRepository,
    private val service: ArticlesService
) : ViewModel() {

    private val maxPageSize = 30
    private var currentOffset = 0

    private val _screenState = MutableStateFlow<ArticleScreenStates>(ArticleScreenStates.INIT)
    val screenState = _screenState.asStateFlow()
    private val _endOfPaging = MutableStateFlow(false)
    val endOfPaging = _endOfPaging.asStateFlow()

    private val _currentPage = MutableStateFlow<List<Result>>(emptyList())
    val currentPage = _currentPage.asStateFlow()

    init {
        viewModelScope.launch {
            tryFetchingData(ArticleScreenStates.INIT)
        }
    }


    suspend fun tryFetchingData(state: ArticleScreenStates) {
        if (_screenState.value == ArticleScreenStates.IDLE || _screenState.value == ArticleScreenStates.INIT) {
            _screenState.emit(state)
            when (state) {
                ArticleScreenStates.INIT, ArticleScreenStates.REFRESH -> {
                    currentOffset = 0
                    when (val call = repository.fetch(0)) {
                        is Request.Success -> {
                            repository.deleteOldResults()
                            repository.deleteOldKeys()
                            call.data.results.forEach {
                                repository.insertNewArticles(it, 0)
                            }

                        }

                        is Request.Failure -> {
                            println(call.description)
                        }
                    }
                    _currentPage.emit(repository.getResult(currentOffset.toLong()))
                }

                ArticleScreenStates.LOADING_PAGE -> {
                    when (val call = repository.fetch(currentOffset + maxPageSize)) {
                        is Request.Success -> {
                            call.data.results.forEach {
                                repository.insertNewArticles(
                                    it,
                                    (currentOffset + maxPageSize).toLong()
                                )
                            }
                        }

                        is Request.Failure -> {
                            println(call.description)
                        }
                    }
                    _currentPage.emit(_currentPage.value + repository.getResult((currentOffset + maxPageSize).toLong()))
                    currentOffset += maxPageSize
                }

                else -> {
                    //DO NOTHING CUZ IT IS IN IDLE STATE
                }
            }
            delay(500L)
            _screenState.emit(ArticleScreenStates.IDLE)
        }
    }

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _ordering = MutableStateFlow<Ordering?>(null)
    val ordering = _ordering.asStateFlow()

    private val _period = MutableStateFlow(PublishDate(null, null))
    val period = _period.asStateFlow()

    fun updateSearch(newQuery: String) {
        viewModelScope.launch {
            _searchQuery.emit(newQuery)
        }
    }

    fun updateOrdering(ordering: Ordering?) {
        viewModelScope.launch {
            _ordering.emit(ordering)
        }
    }

    fun updatePeriod(newPeriod: PublishDate) {
        viewModelScope.launch {
            _period.emit(newPeriod)
        }
    }
}