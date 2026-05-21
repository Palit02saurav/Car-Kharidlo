package com.example.carkharidlo.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.carkharidlo.model.Car

class CarDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "cars_db"
        private const val DATABASE_VERSION = 1

        private const val TABLE_CARS = "cars"

        private const val COLUMN_ID = "id"
        private const val COLUMN_NAME = "name"
        private const val COLUMN_PRICE = "price"
        private const val COLUMN_DRIVEN = "driven"
        private const val COLUMN_FUEL = "fuel"
        private const val COLUMN_TRANSMISSION = "transmission"
        private const val COLUMN_CATEGORY = "category"
        private const val COLUMN_IMAGE = "image"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_CARS (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PRICE TEXT,
                $COLUMN_DRIVEN TEXT,
                $COLUMN_FUEL TEXT,
                $COLUMN_TRANSMISSION TEXT,
                $COLUMN_CATEGORY TEXT,
                $COLUMN_IMAGE TEXT
            )
        """.trimIndent()

        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_CARS")
        onCreate(db)
    }

    fun insertCar(car: Car): Boolean {
        val db = writableDatabase

        val values = ContentValues().apply {
            put(COLUMN_NAME, car.name)
            put(COLUMN_PRICE, car.price)
            put(COLUMN_DRIVEN, car.drivenKm)
            put(COLUMN_FUEL, car.fuelType)
            put(COLUMN_TRANSMISSION, car.transmission)
            put(COLUMN_CATEGORY, car.category)
            put(COLUMN_IMAGE, car.imagePath)
        }

        val result = db.insert(TABLE_CARS, null, values)
        db.close()

        return result != -1L
    }

    fun getCarsByCategory(category: String): List<Car> {
        val carList = mutableListOf<Car>()
        val db = readableDatabase

        val cursor = db.rawQuery(
            "SELECT * FROM $TABLE_CARS WHERE $COLUMN_CATEGORY = ?",
            arrayOf(category)
        )

        if (cursor.moveToFirst()) {
            do {
                val car = Car(
                    id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                    name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME)),
                    price = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PRICE)),
                    drivenKm = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DRIVEN)),
                    fuelType = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FUEL)),
                    transmission = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRANSMISSION)),
                    category = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CATEGORY)),
                    imagePath = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
                )

                carList.add(car)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return carList
    }
}