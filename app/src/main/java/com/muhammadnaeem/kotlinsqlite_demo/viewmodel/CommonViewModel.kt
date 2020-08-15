package com.muhammadnaeem.kotlinsqlite_demo.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.muhammadnaeem.kotlinsqlite_demo.model.EmployeeModel
import com.muhammadnaeem.kotlinsqlite_demo.model.RolesModel
import com.muhammadnaeem.kotlinsqlite_demo.repo.DbRepository
import java.util.ArrayList

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

    //get employee Roles List
    fun getAll_employeeroles(id:Int): MutableLiveData<List<RolesModel>>? {
        return mRepository.getAll_employeeroles(id)
    }

    //insert employee and Roles
    fun insertemployee(mEmployeeModel: EmployeeModel, mselectlist : ArrayList<RolesModel>): MutableLiveData<Long>? {
        return mRepository.insertemployee(mEmployeeModel,mselectlist)
    }


    //Update employee  detail and Roles
    fun update_employee(mEmployeeModel: EmployeeModel, mselectlist : ArrayList<RolesModel>): MutableLiveData<Long>? {
        return mRepository.update_employee(mEmployeeModel,mselectlist)
    }

    //delete employee
    fun deleteemployeeById(id:Int): MutableLiveData<Int> {
        return mRepository.deleteemployeeById(id)
    }



}