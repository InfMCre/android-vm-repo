package com.example.demoemployees.data.repository.remote

import com.example.demoemployees.data.Employee
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {

    @GET("employees")
    suspend fun getEmployees(): Response<List<Employee>>

    @POST("employees")
    suspend fun createEmployee(@Body employee: Employee) : Response<Integer>
}