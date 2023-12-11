package com.example.demoemployees.data.repository.local

import androidx.room.Dao
import androidx.room.Database
import androidx.room.Insert
import androidx.room.Query
import androidx.room.RoomDatabase
import com.example.demoemployees.MyApp
import com.example.demoemployees.data.DbDepartment
import com.example.demoemployees.data.DbEmployee
import com.example.demoemployees.data.Department
import com.example.demoemployees.data.Employee
import com.example.demoemployees.data.repository.CommonDepartmentRepository
import com.example.demoemployees.data.repository.CommonEmployeeRepository
import com.example.demoemployees.utils.Resource

class RoomDepartmentDataSource: CommonDepartmentRepository {
    private val departmentDao: DepartmentDao = MyApp.db.departmentDao()
    override suspend fun getDepartments(): Resource<List<Department>> {
        val response = departmentDao.getDepartments().map{ it.toDepartment() }
        return Resource.success(response)
    }

    override suspend fun createDepartment(department: Department): Resource<Void> {
        departmentDao.addDepartment(department.toDbDepartment())
        return Resource.success()
    }
}

fun Department.toDbDepartment() = DbDepartment(id, name, city)
fun DbDepartment.toDepartment() = Department(id, name, city)


@Dao
interface DepartmentDao {

    @Query("SELECT * FROM employees ORDER BY id ASC")
    suspend fun getDepartments(): List<DbDepartment>

    @Insert
    suspend fun addDepartment(department: DbDepartment)

}