package com.example.deber03

import android.os.Parcel
import android.os.Parcelable

class BSistemaOperativo(
    var nombreSO: String?,
    var version: String?,
    var distribucion: String?,
    var programasSO: List<BPrograma>?  // Cambiamos de BPrograma a List<BPrograma>
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.createTypedArrayList(BPrograma.CREATOR) // Cambiamos readParcelable a createTypedArrayList
    )

    override fun toString(): String {
        return "${nombreSO} - ${version} - ${distribucion}"
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(nombreSO)
        p0.writeString(version)
        p0.writeString(distribucion)
        p0.writeTypedList(programasSO) // Cambiamos writeParcelable a writeTypedList
    }

    companion object CREATOR : Parcelable.Creator<BSistemaOperativo> {
        override fun createFromParcel(parcel: Parcel): BSistemaOperativo {
            return BSistemaOperativo(parcel)
        }

        override fun newArray(size: Int): Array<BSistemaOperativo?> {
            return arrayOfNulls(size)
        }
    }
}
