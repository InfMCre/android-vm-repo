package com.example.demoemployees.data.repository

import com.example.demoemployees.data.Employee

class EmployeeRepository(private val repository : CommonEmployeeRepository) {
    suspend fun getEmployees() = repository.getEmployees()
    suspend fun createEmployee(employee: Employee) = repository.createEmployee(employee)
}