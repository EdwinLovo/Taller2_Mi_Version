package com.example.monedas.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.monedas.models.Moneda

class DatabaseHandler(context: Context): SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DELETE_TABLE)
        onCreate(db)
    }

    fun addCoin(moneda: Moneda):Boolean{
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(ID2, moneda.id)
        values.put(NOMBRE,moneda.nombre)
        values.put(AMBITO,moneda.ambito)
        values.put(DISPONIBILIDAD,moneda.disponibilidad)
        values.put(ANIO,moneda.año)
        values.put(LOGO,moneda.logo)
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    fun getCoins():ArrayList<Moneda>{
        var monedas:ArrayList<Moneda> = ArrayList()
        val db = readableDatabase
        val selectALLQuery = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(selectALLQuery,null)
        if (cursor!=null){
            if (cursor.moveToFirst()){
                do {
                    var id = cursor.getString(cursor.getColumnIndex(ID2))
                    var nombre = cursor.getString(cursor.getColumnIndex(NOMBRE))
                    var ambito = cursor.getString(cursor.getColumnIndex(AMBITO))
                    var disponibilidad = cursor.getString(cursor.getColumnIndex(DISPONIBILIDAD))
                    var año = cursor.getString(cursor.getColumnIndex(ANIO))
                    var logo = cursor.getString(cursor.getColumnIndex(LOGO))

                    var moneda:Moneda = Moneda()
                    moneda.id = id
                    moneda.nombre = nombre
                    moneda.ambito = ambito
                    moneda.año = año
                    moneda.disponibilidad = disponibilidad
                    moneda.logo = logo
                    monedas.add(moneda)
                } while (cursor.moveToNext())
            }
        }
        return monedas
    }

    companion object{
        private val DB_NAME = "monedasDB"
        private val DB_VERSION = 1;
        private val TABLE_NAME = "monedas"
        private val  ID = "id"
        private val ID2 = "idMoneda"
        private val AMBITO = "ambito"
        private val ANIO = "año"
        private val DISPONIBILIDAD = "disponibilidad"
        private val LOGO = "logo"
        private val NOMBRE ="nombre"
        private val CREATE_TABLE = "CREATE TABLE $TABLE_NAME"+"($ID Integer PRIMARY KEY, $ID2 TEXT, $NOMBRE TEXT, $AMBITO TEXT, $DISPONIBILIDAD TEXT, $ANIO TEXT, $LOGO TEXT)"
        private val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}