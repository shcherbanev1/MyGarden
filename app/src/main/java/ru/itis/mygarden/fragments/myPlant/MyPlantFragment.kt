package ru.itis.mygarden.fragments.myPlant

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentInfoBinding
import ru.itis.mygarden.databinding.FragmentMyPlantBinding

class MyPlantFragment: Fragment(R.layout.fragment_my_plant) {
     private var binding: FragmentMyPlantBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMyPlantBinding.bind(view)
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