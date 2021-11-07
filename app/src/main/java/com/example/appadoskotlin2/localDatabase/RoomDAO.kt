package com.example.appadoskotlin2.localDatabase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.appadoskotlin2.data.RoomEntity
import com.example.appadoskotlin2.data.RoomResponse
import com.example.appadoskotlin2.ui.utils.Constanst

@Dao
interface RoomDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertService(service: RoomEntity): Long
    @Query("SELECT * FROM ${Constanst.dabasename}")
    fun loadAll(): LiveData<List<RoomResponse>>
    @Query("SELECT * FROM ${Constanst.dabasename} WHERE type=:type")
    fun selectServiceByType(type:String): LiveData<List<RoomResponse>>
}