package com.example.demoemployees.data.repository

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class AuthenticationRequest (
    val email: String,
    val password: String
): Parcelable