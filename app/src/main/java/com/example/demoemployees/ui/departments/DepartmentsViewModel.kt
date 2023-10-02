package com.example.demoemployees.ui.departments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.demoemployees.data.Department

class DepartmentsViewModel() : ViewModel() {


    private val _items = MutableLiveData<List<Department>>(emptyList())
    val items : LiveData<List<Department>> get() = _items


    init {
        // lista a mano
        val departments = ArrayList<Department>(emptyList())
        departments.add(Department(1, "Finanzas", "Bilbao"))
        departments.add(Department(2, "RRHH", "Donosti"))
        departments.add(Department(3, "Compras", "Vitoria"))
        departments.add(Department(4, "Comercial", "Bilbao"))

        // cambiamos el valor de mutableLiveData
        _items.value = departments
    }

    fun onAddDepartment(id: Int, name: String, city: String) {
        // vamos a meter los elementos de la lista que se muestra en una variable
        val departmentsOnList = _items.value?.toList()
        // creo una nueva lista
        val departments = ArrayList<Department>(emptyList())
        if (departmentsOnList != null) {
            // meto los elementos que ya existian en la lista
            departments.addAll(departmentsOnList)
        }
        // anado el nuevo empleado a la lista
        departments.add(Department(id, name, city))

        // cambio la variable que se esta observando desde otros sitios
        _items.value = departments
    }
}

class DepartmentsViewModelFactory(

): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return DepartmentsViewModel() as T
    }
}