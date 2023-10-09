package com.example.demoemployees.data.repository.remote

import com.example.demoemployees.data.Department
import com.example.demoemployees.data.Employee
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface APIInterface {

    @GET("employees")
    suspend fun getEmployees(): Response<List<Employee>>

    @POST("employees")
    suspend fun createEmployee(@Body employee: Employee) : Response<Int>
    @GET("departments")
    suspend fun getDepartments(): Response<List<Department>>
    @POST("departments")
    suspend fun createDepartment(@Body department: Department) : Response<Void>

    @GET("departments/{id}")
    suspend fun getDepartments(@Path("id") id: Integer): Response<List<Department>>
}