package com.example.demoemployees.ui.login

import LoginViewModel
import LoginViewModelFactory
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.demoemployees.MyApp
import com.example.demoemployees.data.Employee
import com.example.demoemployees.data.repository.remote.RemoteAuthenticationRepository
import com.example.demoemployees.databinding.LoginActivityBinding
import com.example.demoemployees.ui.employees.EmployeeAdapter
import com.example.demoemployees.ui.employees.EmployeesActivity
import com.example.demoemployees.utils.Resource

class LoginActivity : ComponentActivity() {

    private val authenticationRepository = RemoteAuthenticationRepository();

    private val viewModel: LoginViewModel by viewModels { LoginViewModelFactory(
        authenticationRepository
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // cargamos el XML en la actividad
        val binding = LoginActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // el listener del boton
        binding.loginButton.setOnClickListener() {
            viewModel.onLoginClick(
                binding.loginUsernameInput.text.toString(),
                binding.loginPasswordInput.text.toString()
            )
        }

        // el cambio en login del VM cuando el server nos devuelva su respuesta
        viewModel.login.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    it.data?.let { data ->
                        MyApp.userPreferences.saveAuthToken(data.accessToken)
                        // TODO podriamos guardar el nombre del usuario tambien e incluso la pass en el sharedPreferences... hacer sus funciones...
                        // TODO recordad que no esta cifrado esto es solo a modo prueba. Tampoco se recomienda guardar contraseñas...

                        // TODO hacer lo que sea necesario en este caso cambiamos de actividad
                        val intent = Intent(this, EmployeesActivity::class.java).apply {
                            // putExtra(EXTRA_MESSAGE, message)
                        }
                        startActivity(intent)
                        // si queremos quitar esta actividad...
                        // finish()
                    }
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    // de momento
                }
            }
        })

    }

}