package ru.itis.mygarden.fragments.guide

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.databinding.GuideItemBinding
import ru.itis.mygarden.databinding.UserPlantsItemBinding
import ru.itis.mygarden.fragments.disease.Disease
import ru.itis.mygarden.fragments.plant.PlantHolder

class GuideAdapter(
    private var diseases: List<Disease>,
    private val glide: RequestManager,
    private val onClick:(Disease) -> Unit
    ): RecyclerView.Adapter<GuideHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuideHolder {
        return GuideHolder(
            binding = GuideItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            glide = glide,
            onClick = onClick
        )
    }

    override fun getItemCount(): Int = diseases.size

    override fun onBindViewHolder(holder: GuideHolder, position: Int) {
        holder.onBind(diseases[position])
    }
}