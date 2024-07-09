package ru.itis.mygarden.fragments.plant

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.itis.mygarden.data.Plant
import ru.itis.mygarden.databinding.UserPlantsItemBinding

class PlantAdapter(
    private var plants: List<Plant>,
    private val glide: RequestManager,
    private val onClick:(Plant) -> Unit
    ): RecyclerView.Adapter<PlantHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantHolder {
            return PlantHolder(
                binding = UserPlantsItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ),
                glide = glide,
                onClick = onClick
            )
        }

        override fun getItemCount(): Int = plants.size

        override fun onBindViewHolder(holder: PlantHolder, position: Int) {
            holder.onBind(plants[position])
        }

    fun setPlantsList(newPlants:List<Plant>) {
        plants = newPlants
    }
}