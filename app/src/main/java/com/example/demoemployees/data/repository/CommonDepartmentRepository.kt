package com.example.demoemployees.data.repository

import com.example.demoemployees.data.Department
import com.example.demoemployees.utils.Resource

interface CommonDepartmentRepository {
    suspend fun getDepartments() : Resource<List<Department>>
    suspend fun createDepartment(department: Department) : Resource<Integer>
}