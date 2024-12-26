package com.example.task_054

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.task_054.db.FilmItemDao
import com.example.task_054.pagination.MainPagingSource

class FaoMainViewModel(
    private val dao: FilmItemDao
) : ViewModel() {
    val data = Pager(
        PagingConfig(pageSize = 5, enablePlaceholders = false, initialLoadSize = 5)
    ) {
        MainPagingSource(dao)
    }.flow.cachedIn(viewModelScope)
}

class MainViewModelFactory(
    private val dao: FilmItemDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FaoMainViewModel::class.java)) {
            return FaoMainViewModel(dao) as T
        }
        throw IllegalArgumentException("Неизвестный класс ViewModel")
    }
}