package ru.itis.mygarden.fragments.guide

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentGuideBinding
import ru.itis.mygarden.fragments.disease.Disease

class GuideFragment : Fragment(R.layout.fragment_guide) {

    private var binding: FragmentGuideBinding? = null
    private var adapter: GuideAdapter? = null
    private var diseases: List<Disease>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentGuideBinding.bind(view)
        initAdapter()
    }

    private fun initAdapter() {
        adapter = diseases?.let {
            GuideAdapter(
                diseases = it,
                glide = Glide.with(this),
                onClick = {
                    findNavController().navigate(
                        resId = R.id.action_guideFragment_to_diseaseFragment
                    )
                }
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}