package com.example.appadoskotlin2.remoteDatabase

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.appadoskotlin2.data.ServiceItemResponse
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_INDEX = 1

class RemotePagingSource(
    private val remoteApi: RemoteAPI,
    private val query: String
): PagingSource<Int, ServiceItemResponse>(){
    override fun getRefreshKey(state: PagingState<Int, ServiceItemResponse>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ServiceItemResponse> {

        val position = params.key ?: STARTING_INDEX
        var result: LoadResult<Int, ServiceItemResponse>? = null
        try {
            val resp = remoteApi.searchService(query, position, params.loadSize)
            val services = resp.results
            result = LoadResult.Page(
                data = services,
                prevKey = if(position == STARTING_INDEX) null else position - 1,
                nextKey = if(services.isEmpty()) null else position + 1
            )
        }catch (e:IOException){
            Log.d("error", "RemotePagingSource error")
        }catch (e:HttpException){
            Log.d("error", "RemotePagingSource error")
        }
        return result!!
    }

}