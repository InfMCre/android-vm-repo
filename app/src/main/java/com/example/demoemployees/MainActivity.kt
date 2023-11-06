package com.example.demoemployees

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.demoemployees.databinding.ActivityMainBinding
import com.example.demoemployees.ui.employees.EmployeeAdapter
import com.example.demoemployees.ui.employees.EmployeesViewModel
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.demoemployees.data.Employee
import com.example.demoemployees.data.repository.remote.RemoteEmployeeDataSource
import com.example.demoemployees.ui.employees.EmployeesViewModelFactory
import com.example.demoemployees.utils.Resource

class MainActivity : ComponentActivity() {

    private lateinit var employeeAdapter: EmployeeAdapter
    // vamos a ir contra el repo remoto
    private val employeeRepository = RemoteEmployeeDataSource();

    private val viewModel: EmployeesViewModel by viewModels { EmployeesViewModelFactory(
        employeeRepository
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJtaWtlbGxAZWxvcnJpZXRhLmNvbSIsImlzcyI6IkFEVF9EQU0iLCJpYXQiOjE2OTg2NzQ0MTIsImV4cCI6MTY5ODc2MDgxMiwidXNlcklkIjo4fQ.d0u7VXwUBvPb-H_kPVeCWKAl9cHFaDYB_dc_9gpEzoIuyAW_O1Ne1TuThIhb_vH_0xj_XBG8fs4bcqEUBfsWBg"

        // donde accessToken es el token que ha respondido el server
        MyApp.userPreferences.saveAuthToken(accessToken)

        // cargamos el XML en la actividad
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        employeeAdapter = EmployeeAdapter(::onEmployeesListClickItem)
        // si llamasemos a una funcion del VM
        // employeeAdapter = EmployeeAdapter(viewModel::onEmployeesListClickItem)

        // a la lista de empleados le incluyo el adapter de empleado
        binding.employeesList.adapter = employeeAdapter

        viewModel.items.observe(this, Observer {
            // esto es lo que se ejecuta cada vez que la lista en el VM cambia de valor
            Log.e("PruebasDia1", "ha ocurrido un cambio en la lista")

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    Log.d("Status", "success")
                    if (!it.data.isNullOrEmpty()) {
                        employeeAdapter.submitList(it.data)
                    }
                }
                Resource.Status.ERROR -> {
                    Log.d("Status", "error")
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    // de momento
                    Log.d("Status", "loading")
                    val toast = Toast.makeText(this, "Cargando..", Toast.LENGTH_LONG)
                    toast.setGravity(Gravity.TOP, 0, 0)
                    toast.show()
                }
            }
        })

        viewModel.items.observe(this, Observer {
            Toast.makeText(this, "ha cambiado el valor de items", Toast.LENGTH_LONG).show()
        })

        viewModel.created.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.updateEmployeeList()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    // de momento
                }
            }
        })

        binding.addEmployee.setOnClickListener() {
            // cuando hacemos click en addEmployee vamos a
            // llamar al VM con
            // los datos de la vista para crear un empleado

            viewModel.onAddEmployee(
                binding.employeeInputName.text.toString(),
                binding.employeeInputPosition.text.toString(),
                binding.employeeInputSalary.text.toString().toInt()
            )
        }
    }

    private fun onEmployeesListClickItem(employee: Employee) {
        Log.i("PRUEBA1", "va2")
        Log.i("PRUEBA1", employee.name)
    }




}
