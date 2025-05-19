package pl.golem.spacenews.screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeHostViewModel(): ViewModel() {

    private val _currentScreen = MutableStateFlow(0)
    val currentScreen = _currentScreen.asStateFlow()

    fun switchScreen(index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _currentScreen.emit(index)
        }
    }

}