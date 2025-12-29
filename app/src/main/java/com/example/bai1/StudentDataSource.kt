package com.example.bai1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.util.Log

class StudentDataSource(context: Context) {

    private val dbHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    fun open() {
        Log.d("DataSource", "Database connection opened.")
        database = dbHelper.writableDatabase
    }

    fun close() {
        Log.d("DataSource", "Database connection closed.")
        dbHelper.close()
    }

    fun createStudent(student: SinhVien) {
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_STUDENT_ID, student.id)
            put(DatabaseHelper.COL_STUDENT_NAME, student.name)
            put(DatabaseHelper.COL_PHONE_NUMBER, student.phone)
            put(DatabaseHelper.COL_HOME_ADDRESS, student.address)
        }
        database.insert(DatabaseHelper.TABLE_STUDENT_RECORDS, null, values)
    }

    fun retrieveAllStudents(): List<SinhVien> {
        val students = mutableListOf<SinhVien>()
        val cursor = database.query(DatabaseHelper.TABLE_STUDENT_RECORDS, null, null, null, null, null, DatabaseHelper.COL_STUDENT_NAME)
        
        cursor.use { 
            while (it.moveToNext()) {
                students.add(mapCursorToStudent(it))
            }
        }
        return students
    }

    fun updateStudent(studentId: String, updatedStudent: SinhVien) {
        val values = ContentValues().apply {
            put(DatabaseHelper.COL_STUDENT_ID, updatedStudent.id)
            put(DatabaseHelper.COL_STUDENT_NAME, updatedStudent.name)
            put(DatabaseHelper.COL_PHONE_NUMBER, updatedStudent.phone)
            put(DatabaseHelper.COL_HOME_ADDRESS, updatedStudent.address)
        }
        database.update(DatabaseHelper.TABLE_STUDENT_RECORDS, values, "${DatabaseHelper.COL_STUDENT_ID} = ?", arrayOf(studentId))
    }

    fun removeStudent(studentId: String) {
        database.delete(DatabaseHelper.TABLE_STUDENT_RECORDS, "${DatabaseHelper.COL_STUDENT_ID} = ?", arrayOf(studentId))
    }

    fun isIdTaken(studentId: String): Boolean {
        val cursor = database.query(
            DatabaseHelper.TABLE_STUDENT_RECORDS,
            arrayOf(DatabaseHelper.COL_STUDENT_ID),
            "${DatabaseHelper.COL_STUDENT_ID} = ?",
            arrayOf(studentId), null, null, null
        )
        val exists = cursor.use { it.count > 0 }
        return exists
    }

    private fun mapCursorToStudent(cursor: Cursor): SinhVien {
        return SinhVien(
            id = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_STUDENT_ID)),
            name = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_STUDENT_NAME)),
            phone = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_PHONE_NUMBER)),
            address = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_HOME_ADDRESS))
        )
    }
}
