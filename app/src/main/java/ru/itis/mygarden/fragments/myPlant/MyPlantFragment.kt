package ru.itis.mygarden.fragments.myPlant


import android.content.Context.MODE_PRIVATE
import android.view.View
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Intent
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import ru.itis.mygarden.R
import ru.itis.mygarden.data.NotificationReceiver
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.databinding.FragmentMyPlantBinding
import ru.itis.mygarden.presentation.PlantViewModel
import ru.itis.mygarden.presentation.PlantViewModelFactory
import java.time.Instant


class MyPlantFragment : Fragment(R.layout.fragment_my_plant) {
    private var binding: FragmentMyPlantBinding? = null
    private var plant: Plant? = null
    private lateinit var viewModel: PlantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = requireContext()
        viewModel =
            ViewModelProvider(this, PlantViewModelFactory(context)).get(PlantViewModel::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPlantBinding.bind(view)
        super.onCreate(savedInstanceState)
        createNotificationChannel()
        val context = requireContext()
        val sharedPreferences = context.getSharedPreferences("PlantColors", MODE_PRIVATE)
        val id = arguments?.getInt("ARG_ID")
        lifecycleScope.launch {
            val plantsList = viewModel.getAllPlants()
            plant = plantsList.find { it.id == id }

            binding?.run {

                namePlantText.text = plant?.name
                description.text = plant?.description
                valueLight.text = plant?.sunlight
                if (plant?.wateringFrequency == -1) {
                    valueWatering.text = "no info"
                } else {
                    valueWatering.text = "${plant?.wateringFrequency}"
                }
                Glide.with(this@MyPlantFragment)
                    .load(plant?.imgSource)
                    .error(R.drawable.not_found_plant)
                    .into(plantImg)


                var currentTime = getCurrentTimeMillis()
                if (currentTime > (plant?.nextWateringTime ?: 0)) {
                    pour.setBackgroundColor(context.getColor(R.color.orange))
                    sharedPreferences.edit()
                        .putBoolean("${plant?.id}_isWateringButtonGreen", false)
                        .apply()
                }

                buttonWatering.setOnClickListener {
                    scheduleNotification(
                        plantImg.context,
                        86400000 * plant?.wateringFrequency as Long
                    )
                    pour.setBackgroundColor(context.getColor(R.color.dark_green))
                    sharedPreferences.edit()
                        .putBoolean("${plant?.id}_isWateringButtonGreen", true)
                        .apply()
                    //viewModel.updateNextWateringTime(plant)
                }



                backButton.setOnClickListener {
                    findNavController().navigate(R.id.action_myPlantFragment_to_plantFragment)
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
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
        val pendingIntent =
            PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

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