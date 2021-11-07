package com.example.appadoskotlin2.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.appadoskotlin2.ui.utils.Constanst

@Entity(tableName = Constanst.dabasename, indices = [Index(value = ["uid"], unique = true)])
class RoomEntity {

    @PrimaryKey(autoGenerate = true) var id: Int? = null
    var uid: String  = id.toString()
    @ColumnInfo(name = "type") var type: String? = null
    @ColumnInfo(name="status") var status: String?  = null
    @ColumnInfo(name="price") var price: Double? = 0.0
    @ColumnInfo(name = "description") var description: String? = null

}