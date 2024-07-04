package ru.itis.mygarden

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.mygarden.databinding.FragmentAddBinding
import ru.itis.mygarden.databinding.FragmentPlantBinding

class AddFragment : Fragment(R.layout.fragment_add) {

    private var binding: FragmentAddBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}