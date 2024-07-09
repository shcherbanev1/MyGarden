package ru.itis.mygarden.fragments.myPlant

import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import ru.itis.mygarden.R
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.databinding.FragmentMyPlantBinding
import ru.itis.mygarden.presentation.PlantViewModel
import ru.itis.mygarden.presentation.PlantViewModelFactory
import java.time.Instant


class MyPlantFragment: Fragment(R.layout.fragment_my_plant) {
     private var binding: FragmentMyPlantBinding? = null
    private var plantsList: List<Plant>? = null
    private lateinit var viewModel: PlantViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPlantBinding.bind(view)
        super.onCreate(savedInstanceState)
        val context = requireContext()
        val sharedPreferences = context.getSharedPreferences("PlantColors", MODE_PRIVATE)
        val id = arguments?.getInt("ARG_ID")
        ViewModelProvider(this, PlantViewModelFactory(context)).get(PlantViewModel::class.java)
        lifecycleScope.launch {
           plantsList = viewModel.getAllPlants()
        }
        val plant = plantsList?.find{it.id == id}
        binding?.run {

            var currentTime = getCurrentTimeMillis()
            if ( currentTime > plant?.nextWateringTime!!){
                pour.setBackgroundColor(context.getColor(R.color.orange))
                sharedPreferences.edit()
                    .putBoolean("${plant.id}_isWateringButtonGreen", false)
                    .apply()
            }

            buttonWatering.setOnClickListener {
                pour.setBackgroundColor(context.getColor(R.color.dark_green))
                sharedPreferences.edit()
                    .putBoolean("${plant?.id}_isWateringButtonGreen", true)
                   .apply()
                //viewModel.updateNextWateringTime(plant)
            }
            namePlantText.text = plant?.name
            description.text = plant?.description
            valueLight.text = plant?.sunlight
            valueWatering.text = "${plant?.wateringFrequency}"
            Glide.with(this@MyPlantFragment)
                .load(plant?.imgSource)
                .error(R.drawable.not_found_plant)
                .into(plantImg)


            backButton.setOnClickListener {
                findNavController().navigate(R.id.action_myPlantFragment_to_plantFragment)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getCurrentTimeMillis(): Long {
        return Instant.now().toEpochMilli()
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