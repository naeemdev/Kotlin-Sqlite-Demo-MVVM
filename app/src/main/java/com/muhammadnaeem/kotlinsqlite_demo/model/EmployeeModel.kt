package com.muhammadnaeem.kotlinsqlite_demo.model

data class EmployeeModel(
    var id:Int?=0,
    var firstname: String? =null,
    var lastnmae: String? =null,
    var email: String? =null,
    var password: String? =null,
    var desg: String? =null,
    var emp_code: String? =null

) {
}