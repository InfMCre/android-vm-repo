package com.example.demoemployees.data.repository.remote

import com.example.demoemployees.data.Department
import com.example.demoemployees.data.repository.CommonDepartmentRepository
import com.example.demoemployees.utils.Resource

class RemoteDepartmentDataSource: BaseDataSource(), CommonDepartmentRepository {
    override suspend fun getDepartments() = getResult {
        RetrofitClient.apiInterface.getDepartments()
    }

    override suspend fun createDepartment(department: Department) = getResult {
        RetrofitClient.apiInterface.createDepartment(department)
    }

}