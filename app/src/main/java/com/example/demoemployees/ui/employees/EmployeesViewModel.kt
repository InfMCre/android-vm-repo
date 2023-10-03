package com.example.demoemployees.ui.employees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.demoemployees.data.Employee
import com.example.demoemployees.data.repository.CommonEmployeeRepository
import com.example.demoemployees.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmployeesViewModel(
    private val employeeRepository: CommonEmployeeRepository
) : ViewModel() {


    private val _items = MutableLiveData<Resource<List<Employee>>>()
    val items : LiveData<Resource<List<Employee>>> get() = _items

    private val _created = MutableLiveData<Resource<Integer>>();
    val created : LiveData<Resource<Integer>> get() = _created;

    init {
        updateEmployeeList()
    }

    fun updateEmployeeList() {
        viewModelScope.launch {
            // lista a mano
            // voy a llamar a la funcion
            // que va a solicitar los empleados del repositorio
            val repoResponse = getEmployeesFromRepository();
            // cambiamos el valor de mutableLiveData
            _items.value = repoResponse
        }
    }

    suspend fun getEmployeesFromRepository() : Resource<List<Employee>> {
        return withContext(Dispatchers.IO) {
            // aqui unicamente llamamos a la funcion del repositorio
            employeeRepository.getEmployees()
        }
    }
    // amañado


    fun onAddEmployee(name: String, position: String, salary: Int) {

        // vamos a meter los elementos de la lista que se muestra en una variable

        val newEmployee = Employee(name, position, salary)
        viewModelScope.launch {
            _created.value = createNewEmployee(newEmployee)
        }

        // cambio la variable que se esta observando desde otros sitios
    }

    suspend fun createNewEmployee(employee: Employee): Resource<Integer>{
        return withContext(Dispatchers.IO) {
            employeeRepository.createEmployee(employee)
        }
    }
}

class EmployeesViewModelFactory(
    private val employeeRepository: CommonEmployeeRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return EmployeesViewModel(employeeRepository) as T
    }
}