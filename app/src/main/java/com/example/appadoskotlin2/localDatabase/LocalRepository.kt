package com.example.appadoskotlin2.localDatabase

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.appadoskotlin2.data.RoomResponse
import javax.inject.Inject

class LocalRepository @Inject constructor(private val context: Context) {

    private lateinit var serviceDAO: RoomDAO
    private lateinit var allService: LiveData<List<RoomResponse>>
    private val db: ServiceDatabase
    init {
        //Setup the db and dao
        db = ServiceDatabase.getDatabase(context)!!
        serviceDAO = db.serviceDao()
        allService = serviceDAO.loadAll()
    }


}