package com.example.appadoskotlin2.localDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.appadoskotlin2.data.RoomEntity
import com.example.appadoskotlin2.ui.utils.Constanst

@Database(entities = [RoomEntity::class], version = 1, exportSchema = false)
abstract class ServiceDatabase: RoomDatabase() {
    abstract fun serviceDao():RoomDAO

    companion object{
        private var INSTANCE: ServiceDatabase?= null
        open fun getDatabase(context: Context):ServiceDatabase?{

            if(INSTANCE == null){
                synchronized(ServiceDatabase::class.java){
                    if(null== INSTANCE){
                        INSTANCE = Room.databaseBuilder(
                            context.applicationContext,
                            ServiceDatabase::class.java,
                            Constanst.dabasename
                        ).build()
                    }
                }
            }

            return INSTANCE
        }
    }
}