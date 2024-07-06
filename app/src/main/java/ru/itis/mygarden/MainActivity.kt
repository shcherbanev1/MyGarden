package ru.itis.mygarden

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.itis.mygarden.data.PlantViewModel
import ru.itis.mygarden.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  
    private var binding: ActivityMainBinding? = null
    private var controller: NavController? = null
    private var plantViewModel: PlantViewModel? = null
  
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding?.root)
        controller = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
            .navController
        controller?.let { navController ->
            binding?.bottomNavigation?.setupWithNavController(navController)
        }
        
        val application = application as PlantApplication
        plantViewModel = application.viewModel

    }
}
