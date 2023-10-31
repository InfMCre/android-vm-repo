package com.example.demoemployees.ui.departments

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.example.demoemployees.data.repository.remote.RemoteDepartmentDataSource
import com.example.demoemployees.databinding.DepartmentsActivityBinding
import com.example.demoemployees.utils.Resource

class DepartmentActivity : ComponentActivity() {

    private lateinit var departmentAdapter: DepartmentAdapter
    private val departmentRepository = RemoteDepartmentDataSource();

    private val viewModel: DepartmentsViewModel by viewModels { DepartmentsViewModelFactory(departmentRepository) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // cargamos el XML en la actividad
        val binding = DepartmentsActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // creo el adapter de empleados
        departmentAdapter = DepartmentAdapter()
        // a la lista de empleados le incluyo el adapter de empleado
        binding.departmentsList.adapter = departmentAdapter

        viewModel.items.observe(this, Observer {
            // este es el codigo o comportamiento que va a ocurrir cuando la variable
            // LiveData "items" de viewModel sufra una modificacion.
            // al devolvernos un Resource<List<Department>> comprobaremos la respuesta de la llamada
            // y segun si ha ido bien, mal, o esta cargando, ejecutaremos un codigo u otro
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    // en caso de que haya sido OK
                    if (!it.data.isNullOrEmpty()) {
                        departmentAdapter.submitList(it.data)
                    }
                    // TODO  con un ELSE aqui podriamos querer hacer algo distinto
                    //  ha ido bien pero no hay departamentos... decirle algo al usuario no?
                }
                Resource.Status.ERROR -> {
                    // en caso de que haya sido KO
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    Log.d("Status", "loading")
                    Toast.makeText(this, "Cargando..", Toast.LENGTH_LONG).show()
                }
            }
        })


        viewModel.created.observe(this, Observer {
            when (it.status) {
                Resource.Status.SUCCESS -> {
                    viewModel.updateDepartmentList()
                }
                Resource.Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    // de momento
                }
            }
        })

        binding.addDepartment.setOnClickListener() {
            // cuando hacemos click en addEmployee vamos a
            // llamar al VM con
            // los datos de la vista para crear un empleado

            viewModel.onAddDepartment(
                binding.departmentInputId.text.toString().toInt(),
                binding.departmentInputName.text.toString(),
                binding.departmentInputCity.text.toString()
            )
        }
    }

}