package ru.itis.mygarden.fragments.create

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.itis.mygarden.R
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.databinding.FragmentCreateBinding
import ru.itis.mygarden.presentation.PlantViewModel
import ru.itis.mygarden.presentation.PlantViewModelFactory


class CreateFragment : Fragment(R.layout.fragment_create) {

    private var binding: FragmentCreateBinding? = null
    private var flag = false
    private var uri: String? = null
    private lateinit var viewModel: PlantViewModel

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) {
        val galleryUri = it
        try {

            uri = galleryUri.toString()

            binding!!.ivPlant.setImageURI(galleryUri)

            if (binding!!.ivPlant.drawable != null) {
                binding!!.ivPlant.visibility = View.VISIBLE
                flag = true
            } else {
                binding!!.ivPlant.visibility = View.GONE
                flag = false
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateBinding.bind(view)

        val arraySpinnerSun = arrayOf(
            "выбрать", "полная тень", "полутень", "полное солнце"
        )


        val adapterSun: ArrayAdapter<String> =
            ArrayAdapter<String>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                arraySpinnerSun
            )

        adapterSun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding!!.spinnerSun.adapter = adapterSun

        binding?.run {
            btnAddPlantImg.setOnClickListener {
                galleryLauncher.launch("image/*")
            }
            btnAddPlant.setOnClickListener {
                check()
            }
        }
    }

    private fun check() {
        if (flag && binding!!.etName.text.toString() != "" && binding!!.evDescription.text.toString() != "" && binding!!.etFrequency.text.toString() != "" && binding!!.etFrequency.text.toString()
                .toInt() >= 0 && binding!!.spinnerSun.selectedItem.toString() != "выбрать"
        ) {
            makeNewPlant()
            findNavController().navigate(resId = R.id.action_createFragment_to_plantFragment)
        } else {
            binding?.let {
                Snackbar.make(
                    it.root,
                    "Требуется заполнить все поля",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun makeNewPlant() {
        viewModel = ViewModelProvider(
            this,
            PlantViewModelFactory(requireContext())
        )[PlantViewModel::class.java]


        val name = binding!!.etName.text.toString()
        val description = binding!!.evDescription.text.toString()
        var sunlight = ""
        if (binding!!.spinnerSun.selectedItem.toString() == "полная тень") {
            sunlight = "full_shade"
        } else if (binding!!.spinnerSun.selectedItem.toString() == "полутень") {
            sunlight = "part_shade"
        } else if (binding!!.spinnerSun.selectedItem.toString() == "полное солнце") {
            sunlight = "full_sun"
        }
        val wateringFrequency = binding!!.etFrequency.text.toString().toInt()
        val imgSource = uri
        val curUnixTime = System.currentTimeMillis()
        val nextWateringTime = curUnixTime + wateringFrequency * 86400 * 1000

        val newPlant = Plant(
            id = 0,
            name = name,
            description = description,
            sunlight = sunlight,
            wateringFrequency = wateringFrequency,
            imgSource = imgSource,
            nextWateringTime = nextWateringTime
        )
        viewModel.addPlant(newPlant)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}