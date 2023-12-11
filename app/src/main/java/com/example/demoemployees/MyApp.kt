package com.example.demoemployees

import android.app.Application
import android.content.Context
import android.content.res.Configuration
import androidx.room.Room
import com.example.demoemployees.data.repository.local.MyAppRoomDataBase

class MyApp : Application() {

    companion object {
        lateinit var context: Context;
        lateinit var userPreferences: UserPreferences
        lateinit var db: MyAppRoomDataBase
    }




    // Called when the application is starting, before any other application objects have been created.
    // Overriding this method is totally optional!
    override fun onCreate() {
        super.onCreate()
        context = this;
        userPreferences = UserPreferences()

        db = Room
            .databaseBuilder(this, MyAppRoomDataBase::class.java, "company-db")
            .build()
        // Required initialization logic here!
    }

    // Called by the system when the device configuration changes while your component is running.
    // Overriding this method is totally optional!
    override fun onConfigurationChanged ( newConfig : Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    // This is called when the overall system is running low on memory,
    // and would like actively running processes to tighten their belts.
    // Overriding this method is totally optional!
    override fun onLowMemory() {
        super.onLowMemory()
    }
}