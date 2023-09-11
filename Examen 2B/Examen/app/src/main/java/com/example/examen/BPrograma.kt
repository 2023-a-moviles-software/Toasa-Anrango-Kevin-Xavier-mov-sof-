package com.example.deber03

import android.os.Parcel
import android.os.Parcelable

class BPrograma (
    var nombrePrograma:String?,
    var almacenamiento:String?,
    var version:String?,
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun toString(): String {
        return "${nombrePrograma} - ${version} - ${almacenamiento}"
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nombrePrograma)
        parcel.writeString(almacenamiento)
        parcel.writeString(version)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BPrograma> {
        override fun createFromParcel(parcel: Parcel): BPrograma {
            return BPrograma(parcel)
        }

        override fun newArray(size: Int): Array<BPrograma?> {
            return arrayOfNulls(size)
        }
    }
}