package com.example.demoemployees.ui.departments

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import com.example.demoemployees.data.Department
import com.example.demoemployees.data.repository.CommonDepartmentRepository
import com.example.demoemployees.data.repository.CommonEmployeeRepository
import com.example.demoemployees.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DepartmentsViewModel(
    private val departmentRepository: CommonDepartmentRepository
) : ViewModel() {


    private val _items = MutableLiveData<Resource<List<Department>>>()
    val items : LiveData<Resource<List<Department>>> get() = _items

    private val _created = MutableLiveData<Resource<Void>>()
    val created : LiveData<Resource<Void>> get() = _created


    init {
        updateDepartmentList()
    }

    fun updateDepartmentList() {
        viewModelScope.launch {
            val repoResponse = getDepartmentsFromRepository();
            _items.value = repoResponse
        }
    }
    private suspend fun getDepartmentsFromRepository(): Resource<List<Department>> {
         return withContext(Dispatchers.IO) {
             departmentRepository.getDepartments()
         }
    }

    fun onAddDepartment(id: Int, name: String, city: String) {
        val newDepartment = Department(id, name, city)
        viewModelScope.launch {
            _created.value = createNewDepartmentRepository(newDepartment)
        }
    }

    private suspend fun createNewDepartmentRepository(department: Department): Resource<Void>{
        return withContext(Dispatchers.IO) {
            departmentRepository.createDepartment(department)
        }
    }
}

class DepartmentsViewModelFactory(
    private val departmentRepository: CommonDepartmentRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
        return DepartmentsViewModel(departmentRepository) as T
    }
}