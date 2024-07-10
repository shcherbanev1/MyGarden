package ru.itis.mygarden.data

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import ru.itis.mygarden.R

class NotificationReceiver : BroadcastReceiver() {
    @SuppressLint("MissingPermission")
    override fun onReceive(context: Context, intent: Intent) {
        val notificationText = intent.getStringExtra("notification_text")
        // Создание уведомления
        val builder = NotificationCompat.Builder(context, "channel_id")
            .setSmallIcon(R.drawable.plant)
            .setContentTitle("MyGarden")
            .setContentText(
                if (notificationText != null)
                    "Время полить $notificationText"
                else
                    "Время полить цветы"
            )
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1, builder.build())
    }
}