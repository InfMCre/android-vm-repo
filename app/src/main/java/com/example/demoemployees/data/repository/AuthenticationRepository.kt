package com.example.demoemployees.data.repository

import com.example.demoemployees.data.AuthenticationResponse
import com.example.demoemployees.utils.Resource


interface AuthenticationRepository {
    suspend fun login(authenticationRequest: AuthenticationRequest) : Resource<AuthenticationResponse>

    // TODO registro
}
