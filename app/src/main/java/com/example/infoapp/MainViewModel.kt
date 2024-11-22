package com.example.infoapp

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.infoapp.db.MainDb
import com.example.infoapp.utils.ListItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltViewModel
class MainViewModel @Inject constructor(
    val mainDb: MainDb
) : ViewModel() {
    val mainList = mutableStateOf(emptyList<ListItem>())

//        fun getAllItemsByCategory(cat: String) = viewModelScope.launch {////
//            mainList.value = mainDb.dao.getAllItemsByCategory(cat)/////
//        }

    private var job: Job? = null
    fun getAllItemsByCategory(cat: String) {
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getAllItemsByCategory(cat).collect { list -> mainList.value = list }
        }
    }

    fun getFavorites() {
        job?.cancel()
        job = viewModelScope.launch {
            mainDb.dao.getFavorites().collect { list -> mainList.value = list }
        }
    }

    fun insertItem(item: ListItem) = viewModelScope.launch {
        mainDb.dao.insertItem(item)
    }

    fun deleteItem(item: ListItem) = viewModelScope.launch {
        mainDb.dao.deleteItem(item)
    }
}