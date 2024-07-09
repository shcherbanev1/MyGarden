package ru.itis.mygarden.fragments.guide

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.GuideItemBinding
import ru.itis.mygarden.fragments.disease.Disease

class GuideHolder(
    private val glide: RequestManager,
    private val binding: GuideItemBinding,
    private val onClick: (Disease) -> Unit
    ) : RecyclerView.ViewHolder(binding.root)
    {
        fun onBind(disease: Disease) {
            binding.run {
                plantDiseaseName.text = disease.name
                glide
                    .load(disease.imgSource)
                    .error(R.drawable.not_found_plant)
                    .into(plantDiseaseImg)
                root.setOnClickListener{
                    onClick(disease)
                }
            }
        }
}