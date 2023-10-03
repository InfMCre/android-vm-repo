package com.example.demoemployees.data.repository.remote

import com.example.demoemployees.data.Employee
import com.example.demoemployees.data.repository.CommonEmployeeRepository

class RemoteEmployeeDataSource: BaseDataSource(), CommonEmployeeRepository {

    override suspend fun getEmployees() = getResult {
        RetrofitClient.apiInterface.getEmployees()
    }

    override suspend fun createEmployee(employee: Employee) = getResult {
        RetrofitClient.apiInterface.createEmployee(employee)
    }

}