package ru.itis.mygarden.fragments.myPlant

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import java.util.Calendar
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import ru.itis.mygarden.MainActivity
import ru.itis.mygarden.R
import ru.itis.mygarden.data.NotificationReceiver
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.databinding.FragmentInfoBinding
import ru.itis.mygarden.databinding.FragmentMyPlantBinding

class MyPlantFragment: Fragment(R.layout.fragment_my_plant) {
    private var binding: FragmentMyPlantBinding? = null
    private val plant: Plant? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPlantBinding.bind(view)
        createNotificationChannel()
        binding?.run {
            buttonWatering.setOnClickListener{
                scheduleNotification(plantImg.context, 86400000*plant?.wateringFrequency as Long)
            }
        }
        }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "MyGarden"
            val descriptionText = "Пора полить ${plant?.name}"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("channel_id", name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    fun scheduleNotification(context: Context, delayMillis: Long) {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("notification_text", plant?.name)
        }
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Установите время срабатывания уведомления через delayMillis миллисекунд
        val triggerAtMillis = System.currentTimeMillis() + delayMillis
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, triggerAtMillis, pendingIntent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        private const val ARG_ID = "ARG_ID"
        fun bundle(id: Int): Bundle = Bundle().apply {
            putInt(ARG_ID, id)
        }
    }
}