package ru.itis.mygarden.fragments.disease

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentDiseaseBinding
import ru.itis.mygarden.databinding.FragmentGuideBinding
import ru.itis.mygarden.fragments.guide.GuideAdapter

class DiseaseFragment: Fragment(R.layout.fragment_disease) {

    private var binding: FragmentDiseaseBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiseaseBinding.bind(view)
    }

    companion object {
        private const val ARG_ID = "ARG_ID"
        fun bundle(id: Int): Bundle = Bundle().apply {
            putInt(ARG_ID, id)
        }
    }
}