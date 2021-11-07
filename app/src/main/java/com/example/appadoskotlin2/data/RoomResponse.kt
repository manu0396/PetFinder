package com.example.appadoskotlin2.data

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable


//Respuesta del tipo de objeto devuelto por Room

class RoomResponse() :Parcelable {
    var id: Int? = 0
    var type: String? = null
    var status: String? = null
    var description: String? = null
    var email: String? = null
    var price: Double? = 0.0

    constructor(parcel: Parcel) : this() {
        id = parcel.readValue(Int::class.java.classLoader) as? Int
        type = parcel.readString()
        status = parcel.readString()
        description = parcel.readString()
        email = parcel.readString()
        price = parcel.readValue(Double::class.java.classLoader) as? Double
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(type)
        parcel.writeString(status)
        parcel.writeString(description)
        parcel.writeString(email)
        parcel.writeValue(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RoomResponse> {
        override fun createFromParcel(parcel: Parcel): RoomResponse {
            return RoomResponse(parcel)
        }

        override fun newArray(size: Int): Array<RoomResponse?> {
            return arrayOfNulls(size)
        }
    }


}
