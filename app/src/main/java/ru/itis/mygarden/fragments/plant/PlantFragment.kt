package ru.itis.mygarden.fragments.plant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentPlantBinding
import ru.itis.mygarden.presentation.PlantViewModel
import ru.itis.mygarden.presentation.PlantViewModelFactory

class PlantFragment : Fragment(R.layout.fragment_plant) {

    private var binding: FragmentPlantBinding? = null
    private var adapter: PlantAdapter? = null
    private lateinit var viewModel: PlantViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val context = requireContext()
        viewModel = ViewModelProvider(this, PlantViewModelFactory(context)).get(PlantViewModel::class.java)
        initAdapter()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        binding = FragmentPlantBinding.bind(view)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initAdapter() {
        adapter = PlantAdapter(
            plants = viewModel.getAllPlants(),
            glide = Glide.with(this),
            onClick = findNavController().navigate(
                resId = R.id.action_plantFragment_to_addFragment,
            )
        )
        binding?.run {
            userPlantsRv.adapter = adapter
        }
    }
}