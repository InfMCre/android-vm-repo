package com.example.demoemployees

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.demoemployees.databinding.ActivityMainBinding
import com.example.demoemployees.ui.employees.EmployeeAdapter
import com.example.demoemployees.ui.employees.EmployeesViewModel
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.demoemployees.ui.employees.EmployeesViewModelFactory

class MainActivity : ComponentActivity() {

    private lateinit var employeeAdapter: EmployeeAdapter

    private val viewModel: EmployeesViewModel by viewModels { EmployeesViewModelFactory() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // cargamos el XML en la actividad
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // creo el adapter de empleados
        employeeAdapter = EmployeeAdapter()
        // a la lista de empleados le incluyo el adapter de empleado
        binding.employeesList.adapter = employeeAdapter

        viewModel.items.observe(this, Observer {
            // esto es lo que se ejecuta cada vez que la lista en el VM cambia de valor
            Log.i("PruebasDia1", "ha ocurrido un cambio en la lista")
            employeeAdapter.submitList(it)
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


}
