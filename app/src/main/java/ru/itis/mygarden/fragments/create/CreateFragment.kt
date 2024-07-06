package ru.itis.mygarden.fragments.create

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentCreateBinding

class CreateFragment : Fragment(R.layout.fragment_create) {

    private var binding: FragmentCreateBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCreateBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}