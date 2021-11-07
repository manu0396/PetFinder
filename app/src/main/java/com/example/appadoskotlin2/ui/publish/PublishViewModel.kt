package com.example.appadoskotlin2.ui.publish

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.example.appadoskotlin2.remoteDatabase.RemoteRepository

class PublishViewModel @ViewModelInject constructor (
    private val repository : RemoteRepository,
    @Assisted state : SavedStateHandle
) : ViewModel(){
    companion object{
        private const val CURRENT_QUERY = "current_query"
        private const val DEFAULT_QUERY = "todos"
    }
    private val currentQuery = state.getLiveData(CURRENT_QUERY, DEFAULT_QUERY)

    fun searchServices(query : String){
        currentQuery.value=query
    }
    val services = currentQuery.switchMap { queryString ->
        repository.getSearchResult(queryString).cachedIn(viewModelScope)
    }
}