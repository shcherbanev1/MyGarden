package ru.itis.mygarden.fragments.plant

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import ru.itis.mygarden.R
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.databinding.FragmentPlantBinding
import ru.itis.mygarden.fragments.add.AddFragment
import ru.itis.mygarden.fragments.myPlant.MyPlantFragment
import ru.itis.mygarden.presentation.PlantViewModel
import ru.itis.mygarden.presentation.PlantViewModelFactory

class PlantFragment : Fragment(R.layout.fragment_plant) {

    private var binding: FragmentPlantBinding? = null
    private var adapter: PlantAdapter? = null
    private lateinit var viewModel: PlantViewModel
    private var plantsList: List<Plant>? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val context = requireContext()
        binding = FragmentPlantBinding.bind(view)
        viewModel = ViewModelProvider(this, PlantViewModelFactory(context)).get(PlantViewModel::class.java)
        lifecycleScope.launch {
            plantsList = viewModel.getAllPlants()
            initAdapter()
        }
        initViews()
    }

    private fun initViews() {
        binding?.run {
            addButton.setOnClickListener {
                findNavController().navigate(resId = R.id.action_plantFragment_to_addFragment)
            }
            searchEt.addTextChangedListener {
                val filteredPlantsList: List<Plant>? = plantsList?.filter {
                    it.name.uppercase().contains(searchEt.text.toString().uppercase())
                }
                filteredPlantsList?.let { it1 -> adapter?.setPlantsList(it1) }
                adapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    private fun initAdapter() {
        adapter = plantsList?.let {
            PlantAdapter(
                plants = it,
                glide = Glide.with(this),
                onClick = {
                    findNavController().navigate(
                        resId = R.id.action_plantFragment_to_myPlantFragment,
                        args = MyPlantFragment.bundle(id = it.id)
                    )
                }
            )
        }
        binding?.run {
            userPlantsRv.adapter = adapter
        }
    }
}