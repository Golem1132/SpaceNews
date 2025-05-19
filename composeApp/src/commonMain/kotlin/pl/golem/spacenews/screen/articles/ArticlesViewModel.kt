package pl.golem.spacenews.screen.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import pl.golem.spacenews.model.Ordering
import pl.golem.spacenews.model.PublishDate


class ArticlesViewModel(): ViewModel() {

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