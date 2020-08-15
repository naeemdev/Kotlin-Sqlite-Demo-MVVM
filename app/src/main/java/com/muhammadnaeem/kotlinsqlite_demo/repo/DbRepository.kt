package com.muhammadnaeem.kotlinsqlite_demo.repo

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteException
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.muhammadnaeem.kotlinsqlite_demo.dbhelper.DbHelper
import com.muhammadnaeem.kotlinsqlite_demo.model.EmployeeModel
import com.muhammadnaeem.kotlinsqlite_demo.model.RolesModel
import com.muhammadnaeem.kotlinsqlite_demo.utils.*
import java.util.ArrayList

class DbRepository(private val context: Context)  {

    fun getAll_employee(): MutableLiveData<List<EmployeeModel>> {

        val appDbHelper = DbHelper.getInstance(context)
        val employeelist = MutableLiveData<List<EmployeeModel>>()
        try {
             appDbHelper.readableDatabase.use { db ->
                db.query(
                    TABLE_EMPLOYEE, null, null,
                    null, null, null, null, null).use { cursor ->

                    if (cursor != null)
                        if (cursor.moveToFirst()) {
                            val mlist = ArrayList<EmployeeModel>()
                            do {
                                var mEmployeeModel=EmployeeModel()
                                mEmployeeModel.id= cursor.getInt(cursor.getColumnIndex(
                                    COLUMN_ID
                                ))
                                mEmployeeModel.firstname= cursor.getString(cursor.getColumnIndex(
                                    COLUMN_EMP_FIRSTNAME
                                ))
                                mEmployeeModel.lastnmae= cursor.getString(cursor.getColumnIndex(
                                    COLUMN_EMP_LASTNAME
                                ))
                                mEmployeeModel.email= cursor.getString(cursor.getColumnIndex(
                                    COLUMN_EMP_EMAIL
                                ))
                                mEmployeeModel.desg= cursor.getString(cursor.getColumnIndex(
                                    COLUMN_EMP_DESG
                                ))
                                mEmployeeModel.password= cursor.getString(cursor.getColumnIndex(
                                    COLUMN_EMP_PASSWORD
                                ))
                                mEmployeeModel.emp_code= cursor.getString(cursor.getColumnIndex(
                                    COLUMN_EMP_CODE
                                ))

                                mlist.add(mEmployeeModel)


                            } while (cursor.moveToNext())

                            employeelist.postValue(mlist)
                            return employeelist
                        }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }

        return employeelist
    }


    //get Single Employee Detail
    fun getemployeeById(id: Int): MutableLiveData<EmployeeModel>  {

        val appDbHelper = DbHelper.getInstance(context)
        val mEmployeeModel_live = MutableLiveData<EmployeeModel>()

        try {
            appDbHelper.readableDatabase.use { db ->
                db.query(
                    TABLE_EMPLOYEE, null,
                    "$COLUMN_ID = ? ", arrayOf(id.toString()), null, null, null
                ).use { cursor ->
                    if (cursor.moveToFirst()) {
                        var mEmployeeModel=EmployeeModel()
                        mEmployeeModel.id= cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
                        mEmployeeModel.firstname= cursor.getString(cursor.getColumnIndex(
                            COLUMN_EMP_FIRSTNAME
                        ))
                        mEmployeeModel.lastnmae= cursor.getString(cursor.getColumnIndex(
                            COLUMN_EMP_LASTNAME
                        ))
                        mEmployeeModel.email= cursor.getString(cursor.getColumnIndex(
                            COLUMN_EMP_EMAIL
                        ))
                        mEmployeeModel.desg= cursor.getString(cursor.getColumnIndex(
                            COLUMN_EMP_DESG
                        ))
                        mEmployeeModel.password= cursor.getString(cursor.getColumnIndex(
                            COLUMN_EMP_PASSWORD
                        ))
                        mEmployeeModel.emp_code= cursor.getString(cursor.getColumnIndex(
                            COLUMN_EMP_CODE
                        ))
                        mEmployeeModel_live.postValue(mEmployeeModel)
                    }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, "Operation failed", Toast.LENGTH_SHORT).show()
        }

        return mEmployeeModel_live
    }

    //get Employee Roles
    fun getAll_employeeroles(emp_id:Int):  MutableLiveData<List<RolesModel>> {

        val appDbHelper = DbHelper.getInstance(context)
        val employeerolelist = MutableLiveData<List<RolesModel>>()
        try {

            appDbHelper.readableDatabase.use { db ->
                db.query(
                    TABLE_ROLES, null,
                    "$COLUMN_EMP_ID = ? ", arrayOf(emp_id.toString()), null, null, null
                ).use { cursor ->

                    if (cursor != null)
                        if (cursor.moveToFirst()) {
                            val mRolesModellist = ArrayList<RolesModel>()
                            do {
                                var mRolesModel= RolesModel()

                                mRolesModel.name= cursor.getString(cursor.getColumnIndex(
                                    COLUMN_ROLES_NAME
                                ))

                                mRolesModellist.add(mRolesModel)

                            } while (cursor.moveToNext())
                            employeerolelist.postValue(mRolesModellist)
                            return employeerolelist
                        }
                }
            }
        } catch (e: Exception) {
            Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
        }

        return employeerolelist
    }



    //insert employee
    fun insertemployee(mEmployeeModel: EmployeeModel,mselectlist : ArrayList<RolesModel>):  MutableLiveData<Long>  {
        val id = MutableLiveData<Long>()
//        var id: Long = -1
        val appDbHelper = DbHelper.getInstance(context)


        try {
            appDbHelper.writableDatabase.use { db ->
                val contentValues = ContentValues()
                contentValues.put(COLUMN_EMP_FIRSTNAME, mEmployeeModel.firstname)
                contentValues.put(COLUMN_EMP_LASTNAME, mEmployeeModel.lastnmae)
                contentValues.put(COLUMN_EMP_EMAIL, mEmployeeModel.email)
                contentValues.put(COLUMN_EMP_PASSWORD, mEmployeeModel.password)
                contentValues.put(COLUMN_EMP_DESG, mEmployeeModel.desg)
                contentValues.put(COLUMN_EMP_EMAIL, mEmployeeModel.emp_code)
                var id_inser= db.insertOrThrow(TABLE_EMPLOYEE, null, contentValues)
                id.postValue(id_inser)
                if (mselectlist.size>0){
                    for(i in  0..(mselectlist.size-1)){

                        insertemp_roles(id_inser.toInt(),mselectlist.get(i).name.toString())
                    }

                }

            }
        } catch (e: SQLiteException) {
            Toast.makeText(context, "Operation failed: " + e.message, Toast.LENGTH_LONG).show()
        }

        return id
    }

    //insert roles
    fun insertemp_roles(emp_id:Int, name: String) {

        val appDbHelper = DbHelper.getInstance(context)


        try {
            appDbHelper.writableDatabase.use { db ->
                val contentValues = ContentValues()
                contentValues.put(COLUMN_EMP_ID, emp_id)
                contentValues.put(COLUMN_ROLES_NAME, name)
                db.insertOrThrow(TABLE_ROLES, null, contentValues)

            }
        } catch (e: SQLiteException) {
            Toast.makeText(context, "Operation failed: " + e.message, Toast.LENGTH_LONG).show()
        }


    }

}