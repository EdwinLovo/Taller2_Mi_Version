package com.example.monedas.models

import android.os.Parcel
import android.os.Parcelable

class Moneda() : Parcelable {
    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(ambito)
        dest?.writeString(año)
        dest?.writeString(disponibilidad)
        dest?.writeString(id)
        dest?.writeString(logo)
        dest?.writeString(nombre)
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    var ambito:String? = null
    var año:String? = null
    var disponibilidad:String? = null
    var id:String? = null
    var logo:String? = null
    var nombre:String? = null

    constructor(parcel: Parcel) : this() {
        ambito = parcel.readString()
        año = parcel.readString()
        disponibilidad = parcel.readString()
        id = parcel.readString()
        logo = parcel.readString()
        nombre = parcel.readString()
    }

    companion object CREATOR : Parcelable.Creator<Moneda> {
        override fun createFromParcel(parcel: Parcel): Moneda {
            return Moneda(parcel)
        }

        override fun newArray(size: Int): Array<Moneda?> {
            return arrayOfNulls(size)
        }
    }
}