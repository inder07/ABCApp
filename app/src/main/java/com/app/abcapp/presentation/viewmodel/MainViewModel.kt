package com.app.abcapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _data = listOf(
        listOf("Apple", "Banana", "Orange", "Blueberry", "Avocado", "Item 6", "Item 7", "Item 8", "Item 9"),
        listOf("Grape", "Peach", "Pear", "Kiwi", "Melon", "Item 6", "Item 7", "Item 8", "Item 9"),
        listOf("Strawberry", "Cherry", "Pineapple", "Mango", "Papaya", "Item 6", "Item 7", "Item 8", "Item 9")
    )

    private val _filteredList = MutableLiveData<List<String>>()
    val filteredList: LiveData<List<String>> = _filteredList

    private var currentPage = 0

    init {
        updateList("")
    }

    fun updatePage(index: Int) {
        currentPage = index
        updateList("")
    }

    fun updateList(query: String?) {
        val original = _data[currentPage]
        _filteredList.value = if (query.isNullOrBlank()) {
            original
        } else {
            original.filter { it.contains(query, ignoreCase = true) }
        }
    }

    fun getCurrentIndex() = currentPage
}
