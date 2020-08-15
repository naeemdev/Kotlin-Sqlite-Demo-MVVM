package com.muhammadnaeem.kotlinsqlite_demo.dbhelper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.muhammadnaeem.kotlinsqlite_demo.utils.*

class DbHelper
private constructor(context: Context) : SQLiteOpenHelper(context,
    DATABASE_NAME, null,
    DATABASE_VERSION
) {

    override fun onCreate(db: SQLiteDatabase) {

        // Create Employee tables
        val createEmployeeTable = ("CREATE TABLE " + TABLE_EMPLOYEE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMP_FIRSTNAME + " TEXT NOT NULL, "
                + COLUMN_EMP_LASTNAME + " TEXT NOT NULL, "
                + COLUMN_EMP_EMAIL + " TEXT, "
                + COLUMN_EMP_DESG + " TEXT, " //nullable
                + COLUMN_EMP_PASSWORD + " TEXT NOT NULL, "

                + COLUMN_EMP_CODE + " TEXT " //nullable
                + ")")


        // Create tables
        val createRolesTable = ("CREATE TABLE " + TABLE_ROLES + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_EMP_ID + " INTEGER NOT NULL, "
                + COLUMN_ROLES_NAME + " TEXT  NOT NULL"
                + ")")


        //SQL execution
        db.execSQL(createEmployeeTable)
        db.execSQL(createRolesTable)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS $TABLE_EMPLOYEE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ROLES")

        // Create tables again
        onCreate(db)
    }

    companion object {
        private var databaseHelper: DbHelper? = null

        // All Static variables
        private const val DATABASE_VERSION = 1

        // Database Name
        private const val DATABASE_NAME = "employee_db"

        @Synchronized
        fun getInstance(context: Context): DbHelper {
            if (databaseHelper == null) {
                databaseHelper =
                    DbHelper(context)
            }
            return databaseHelper as DbHelper
        }
    }


}