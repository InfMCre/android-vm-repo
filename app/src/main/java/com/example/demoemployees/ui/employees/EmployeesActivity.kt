package com.example.demoemployees.ui.employees

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.demoemployees.databinding.ActivityMainBinding
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.demoemployees.MyApp
import com.example.demoemployees.data.Employee
import com.example.demoemployees.data.repository.remote.RemoteEmployeeDataSource
import com.example.demoemployees.utils.Resource

class EmployeesActivity : ComponentActivity() {

    private lateinit var employeeAdapter: EmployeeAdapter
    // vamos a ir contra el repo remoto
    private val employeeRepository = RemoteEmployeeDataSource();

    private val viewModel: EmployeesViewModel by viewModels { EmployeesViewModelFactory(
        employeeRepository
    ) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

            when (it.status) {
                Resource.Status.SUCCESS -> {
                    if (!it.data.isNullOrEmpty()) {
                        employeeAdapter.submitList(it.data)
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
