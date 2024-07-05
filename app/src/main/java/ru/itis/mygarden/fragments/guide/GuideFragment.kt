package ru.itis.mygarden.fragments.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentGuideBinding

class GuideFragment : Fragment(R.layout.fragment_guide) {

    private var binding: FragmentGuideBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGuideBinding.bind(view)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}