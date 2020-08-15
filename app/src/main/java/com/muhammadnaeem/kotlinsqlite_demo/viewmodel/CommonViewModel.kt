package com.muhammadnaeem.kotlinsqlite_demo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.muhammadnaeem.kotlinsqlite_demo.model.EmployeeModel
import com.muhammadnaeem.kotlinsqlite_demo.repo.DbRepository

class CommonViewModel (application: Application) : AndroidViewModel(application) {
    private val mRepository: DbRepository = DbRepository(application.applicationContext)

    //get List of employee from DB
    fun getAll_employee(): MutableLiveData<List<EmployeeModel>>? {
        return mRepository.getAll_employee()
    }

    // get Employee Detail
    fun getemployeeById(id:Int): MutableLiveData<EmployeeModel>? {
        return mRepository.getemployeeById(id)
    }



}