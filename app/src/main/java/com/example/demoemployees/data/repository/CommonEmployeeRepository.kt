package com.example.demoemployees.data.repository

import com.example.demoemployees.data.Employee
import com.example.demoemployees.utils.Resource

interface CommonEmployeeRepository {
    suspend fun getEmployees() : Resource<List<Employee>>
    suspend fun createEmployee(employee: Employee) : Resource<Int>
}