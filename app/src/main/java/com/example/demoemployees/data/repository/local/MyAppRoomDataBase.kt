package com.example.demoemployees.data.repository.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demoemployees.data.DbDepartment
import com.example.demoemployees.data.DbEmployee

@Database(
    entities = [DbEmployee::class, DbDepartment::class],
    version = 1,
    exportSchema = false
)
abstract class MyAppRoomDataBase: RoomDatabase() {
    abstract fun employeeDao(): EmployeeDao

    abstract fun departmentDao(): DepartmentDao
}
