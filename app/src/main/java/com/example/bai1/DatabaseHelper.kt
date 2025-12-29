package com.example.bai1

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "StudentDatabase.db" // Changed DB name

        // Table and column names
        const val TABLE_STUDENT_RECORDS = "student_records" // Changed table name
        const val COL_PRIMARY_KEY = "_id"
        const val COL_STUDENT_ID = "student_id"
        const val COL_STUDENT_NAME = "full_name"
        const val COL_PHONE_NUMBER = "phone_number"
        const val COL_HOME_ADDRESS = "home_address"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreateTable = """
            CREATE TABLE $TABLE_STUDENT_RECORDS (
                $COL_PRIMARY_KEY INTEGER PRIMARY KEY AUTOINCREMENT,
                $COL_STUDENT_ID TEXT UNIQUE,
                $COL_STUDENT_NAME TEXT NOT NULL,
                $COL_PHONE_NUMBER TEXT,
                $COL_HOME_ADDRESS TEXT
            )
        """
        db?.execSQL(sqlCreateTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENT_RECORDS")
        onCreate(db)
    }
}
