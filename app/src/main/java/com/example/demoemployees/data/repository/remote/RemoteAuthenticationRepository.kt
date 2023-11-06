package com.example.demoemployees.data.repository.remote

import com.example.demoemployees.data.AuthenticationResponse
import com.example.demoemployees.data.repository.AuthenticationRepository
import com.example.demoemployees.data.repository.AuthenticationRequest
import com.example.demoemployees.data.repository.CommonDepartmentRepository
import com.example.demoemployees.utils.Resource

class RemoteAuthenticationRepository :  BaseDataSource(), AuthenticationRepository {

    override suspend fun login(authenticationRequest : AuthenticationRequest): Resource<AuthenticationResponse> = getResult {
        RetrofitClient.apiInterface.login(authenticationRequest)
    }
}