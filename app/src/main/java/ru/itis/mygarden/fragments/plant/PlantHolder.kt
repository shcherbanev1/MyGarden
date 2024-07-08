package ru.itis.mygarden.fragments.plant

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import ru.itis.mygarden.R
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.databinding.UserPlantsItemBinding

class PlantHolder(
    private val glide: RequestManager,
    private val binding: UserPlantsItemBinding,
    private val onClick: Unit
    ) : ViewHolder(binding.root)
    {
        fun onBind(plant: Plant) {
            binding.run {
                plantName.text = "  ${plant.name}  "
                glide
                    .load(plant.imgSource)
                    .error(R.drawable.not_found_plant)
                    .into(plantImg)
            }
        }
}