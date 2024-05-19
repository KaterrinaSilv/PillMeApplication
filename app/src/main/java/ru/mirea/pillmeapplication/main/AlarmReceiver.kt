package ru.mirea.pillmeapplication.main

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.mirea.pillmeapplication.R

class AlarmReceiver : BroadcastReceiver() {

    val TAG = this::class.java.simpleName


    companion object {
        const val CHANNEL_ID = "channelID"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        Log.d(TAG, "Message = $name")

        if(name != null && id != null) {
            // Создаём уведомление
            val builder = NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.status)
                .setContentTitle("PILLME")
                .setContentText(name)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = NotificationManagerCompat.from(context)
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
            notificationManager.notify(id.toInt(), builder.build())
            Log.d(TAG, "Notify?")
        }
        else{
            Log.d(TAG, "nullable values")
        }

    }


}