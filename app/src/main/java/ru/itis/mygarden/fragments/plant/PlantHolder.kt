package ru.itis.mygarden.fragments.plant

import android.content.Context.MODE_PRIVATE
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.RequestManager
import ru.itis.mygarden.R
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.databinding.UserPlantsItemBinding
import java.time.Instant

class PlantHolder(
    private val glide: RequestManager,
    private val binding: UserPlantsItemBinding,
    private val onClick: (Plant) -> Unit
    ) : ViewHolder(binding.root)
    {
        @RequiresApi(Build.VERSION_CODES.O)
        fun onBind(plant: Plant) {
            binding.run {
                plantName.text = plant.name
                glide
                    .load(plant.imgSource)
                    .error(R.drawable.not_found_plant)
                    .into(plantImg)
                root.setOnClickListener{
                    onClick(plant)
                }
                val sharedPreferences = itemView.context.getSharedPreferences("PlantColors", MODE_PRIVATE)
                val isWateringButtonGreen = sharedPreferences.getBoolean("${plant.id}_isWateringButtonGreen", false)
                pour.setBackgroundColor(
                    if (isWateringButtonGreen) itemView.context.getColor(R.color.green)
                    else itemView.context.getColor(R.color.orange)
                )
                if (Instant.now().toEpochMilli() > plant.nextWateringTime!!){
                    pour.setBackgroundColor(itemView.context.getColor(R.color.orange))
                    sharedPreferences.edit()
                        .putBoolean("${plant.id}_isWateringButtonGreen", false)
                        .apply()
                }
            }
        }
}