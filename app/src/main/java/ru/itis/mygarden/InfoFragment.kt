package ru.itis.mygarden

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.mygarden.databinding.FragmentInfoBinding
import ru.itis.mygarden.databinding.FragmentPlantBinding

class InfoFragment : Fragment(R.layout.fragment_info) {

    private var binding: FragmentInfoBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}