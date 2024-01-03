package com.example.demoemployees.firebase

import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.demoemployees.R
import com.example.demoemployees.data.Employee
import com.example.demoemployees.data.repository.local.RoomEmployeeDataSource
import com.example.demoemployees.utils.Resource
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // el repositorio, unicamente para el ejemplo en cuestion
    private val employeeRepository = RoomEmployeeDataSource();

    // tag para debug
    private val TAG = "MyFirebaseMsgService"

    // identificador unico asociado a la notificacion que voy a mostrar en el panel de notificaciones de android
    // al recibir una notificacion push
    private val CHANNEL_ID = "MyNotificationChannel"


    // funcion que se ejecuta automaticamente cuando se genera un token nuevo para este dispositivo en esta aplicacion
    override fun onNewToken(token: String) {
        // Este método se llama automáticamente cuando se genera un nuevo token.
        Log.d(TAG, "Nuevo token: $token")
        // Puedes enviar el token al servidor en este punto si es necesario.
        // o guardarlo en UserPreferences para hacerlo mas adelante
    }

    // porcion de codigo que se ejecutara cuando recibamos una notificacion de firebase
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "Mensaje recibido de: ${remoteMessage.from}")

        remoteMessage.notification?.let {
            Log.d(TAG, "notificacion: ${it.title}")
            Log.d(TAG, "notificacion: ${it.body}")
            showNotification(it.title ?: "Title", it.body ?: "body")
        }

        remoteMessage.data.isNotEmpty().let {
            Log.d(TAG, "data: ${remoteMessage.data}")

            remoteMessage.data["data_type"]?.let { dataType ->
                remoteMessage.data["json"]?.let { json ->
                    when (ReceivingMessageTypes.valueOf(dataType)) {
                        ReceivingMessageTypes.EMPLOYEE -> {
                            val employee: Employee = convertDataToCustomObject2(json, Employee::class.java)
                            Log.d(TAG, "employeeData:  ${employee.name}")

                            // vamos a anadir el empleado al repository de room de empleados
                            GlobalScope.launch(Dispatchers.Main) {
                                Log.d(TAG, "vamos a guardar el empleado")
                                createNewEmployee(employee)
                                Log.d(TAG, "vamos a actualizar la vista con el empleado")
                                EventBus.getDefault().post(employee)
                            }
                        }
                        ReceivingMessageTypes.DEPARTMENT -> {
                            // TODO
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestroy")
        super.onDestroy()
    }

    private fun convertDataToCustomObject(data: Map<String, String>): NotificationData {
        // Utilizar Gson para convertir el mapa a un objeto de CustomData
        return Gson().fromJson(Gson().toJsonTree(data), NotificationData::class.java)
    }
    private inline fun <reified T> convertDataToCustomObject2(data: String, classOfT: Class<T>): T {
        // Utilizar Gson para convertir el mapa a un objeto de CustomData
        return Gson().fromJson(data, classOfT)
    }

    // misma funcion que tenia en el viewModel
    private suspend fun createNewEmployee(employee: Employee): Resource<Int> {
        return withContext(Dispatchers.IO) {
            employeeRepository.createEmployee(employee)
        }
    }

    private fun showNotification(title: String, body: String) {
        Log.d(TAG, "showNotification")
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        // Crear un canal de notificación (requerido en Android Oreo y versiones posteriores)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Canal de notificación",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        // Crear el objeto de notificación
        val builder = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        // Mostrar la notificación
        notificationManager.notify(1, builder.build())
    }

    // esta funcion comprueba si la aplicacion esta en background o no, podemos llamarla para mostrar notificacion o no
    private fun isAppInBackground(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses ?: return false

        return appProcesses.none {
            it.processName == packageName && it.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
        }
    }
}
