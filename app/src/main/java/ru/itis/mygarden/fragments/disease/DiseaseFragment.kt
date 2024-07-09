package ru.itis.mygarden.fragments.disease

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentDiseaseBinding


class DiseaseFragment: Fragment(R.layout.fragment_disease) {

    private var binding: FragmentDiseaseBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDiseaseBinding.bind(view)
        val disease = DiseasesRepository.diseases.find {
            it.id == (arguments?.getInt(ARG_ID) ?: -1)
        }
        binding?.run {
            if (disease != null){
                nameDiseaseTv.text = disease.name
                description.text = disease.description
                Glide.with(diseaseImg.context)
                    .load(disease.imgSource)
                    .error(R.drawable.not_found_plant)
                    .into(diseaseImg)
            } else nameDiseaseTv.text = "ERROR"
        }
    }

    companion object {
        private const val ARG_ID = "ARG_ID"
        fun bundle(id: Int): Bundle = Bundle().apply {
            putInt(ARG_ID, id)
        }
    }
}