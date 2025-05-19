package pl.golem.spacenews.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue

class FilterMenuState(
    expanded: Boolean,
    isDialogVisible: Boolean,
    filterType: FilterType
) {

    var expanded by mutableStateOf(expanded)
    var isDialogVisible by mutableStateOf(isDialogVisible)
    var filterType by mutableStateOf(filterType)

    companion object {
        fun Saver() = listSaver(
            save = {
                listOf(it.expanded, it.isDialogVisible, it.filterType)
            },
            restore = {
                FilterMenuState(it[0] as Boolean, it[1] as Boolean, it[2] as FilterType)
            }
        )
    }
}

@Composable
fun rememberFilterMenuState(): FilterMenuState =
    rememberSaveable(saver = FilterMenuState.Saver()) { FilterMenuState(false, false, FilterType.NONE) }


enum class FilterType {
    NONE,
    SORT,
    FROM,
    TO
}