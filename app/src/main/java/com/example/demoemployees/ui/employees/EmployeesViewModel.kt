package com.example.demoemployees.ui.employees

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.demoemployees.data.Employee

class EmployeesViewModel() : ViewModel() {


    private val _items = MutableLiveData<List<Employee>>(emptyList())
    val items : LiveData<List<Employee>> get() = _items


    init {
        // lista a mano
        val employees = ArrayList<Employee>(emptyList())
        employees.add(Employee("mikel", "jefe", 15000))
        employees.add(Employee("mikel2", "empleado", 16000))
        employees.add(Employee("mikel3", "empleado", 17000))
        employees.add(Employee("mikel4", "empleado", 18000))

        // cambiamos el valor de mutableLiveData
        _items.value = employees
    }

    fun onAddEmployee(name: String, position: String, salary: Int) {
        // vamos a meter los elementos de la lista que se muestra en una variable
        val employeesOnList = _items.value?.toList()
        // creo una nueva lista
        val employees = ArrayList<Employee>(emptyList())
        if (employeesOnList != null) {
            // meto los elementos que ya existian en la lista
            employees.addAll(employeesOnList)
        }
        // anado el nuevo empleado a la lista
        employees.add(Employee(name, position, salary))

        // cambio la variable que se esta observando desde otros sitios
        _items.value = employees
    }
}

class EmployeesViewModelFactory(

): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return EmployeesViewModel() as T
    }
}