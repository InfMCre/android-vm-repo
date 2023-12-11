package com.example.demoemployees.data.repository.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.demoemployees.MyApp
import com.example.demoemployees.data.DbEmployee
import com.example.demoemployees.data.Employee
import com.example.demoemployees.data.repository.CommonEmployeeRepository
import com.example.demoemployees.utils.Resource

class RoomEmployeeDataSource : CommonEmployeeRepository {
    // TODO esto deberia entrar por parametro
    private val employeeDao: EmployeeDao = MyApp.db.employeeDao()
    override suspend fun getEmployees(): Resource<List<Employee>> {
        val response = employeeDao.getEmployees().map{ it.toEmployee() }
        return Resource.success(response)
    }

    override suspend fun createEmployee(employee: Employee): Resource<Int> {
        val response = employeeDao.addEmployee(employee.toDbEmployee())
        return Resource.success(response)
    }

}

fun Employee.toDbEmployee() = DbEmployee(id, name, position, salary)
fun DbEmployee.toEmployee() = Employee(id, name, position, salary)

@Dao
interface EmployeeDao {

    @Query("SELECT * FROM employees ORDER BY id ASC")
    suspend fun getEmployees(): List<DbEmployee>

    @Insert
    suspend fun addEmployee(employee: DbEmployee): Int

    //@Update
    //suspend fun updateEmployee(employee: DbEmployee)
}