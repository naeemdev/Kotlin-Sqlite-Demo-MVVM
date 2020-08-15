package com.muhammadnaeem.kotlinsqlite_demo.repo

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.muhammadnaeem.kotlinsqlite_demo.dbhelper.DbHelper
import com.muhammadnaeem.kotlinsqlite_demo.model.EmployeeModel
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


}