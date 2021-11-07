package com.example.appadoskotlin2.remoteDatabase

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteRepository @Inject constructor(private val remoteAPI: RemoteAPI) {

    fun getSearchResult(query:String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { RemotePagingSource(remoteAPI, query) }
        ).liveData
}