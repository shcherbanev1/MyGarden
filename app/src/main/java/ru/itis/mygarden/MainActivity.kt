package ru.itis.mygarden

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import ru.itis.mygarden.databinding.ActivityMainBinding
import ru.itis.mygarden.util.PermissionRequestHandler

class MainActivity : AppCompatActivity() {
    private var binding: ActivityMainBinding? = null
    private var controller: NavController? = null

    private val permissionRequestHandler = PermissionRequestHandler(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater))
        setContentView(binding?.root)
        controller = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment)
            .navController
        controller?.let { navController ->
            binding?.bottomNavigation?.setupWithNavController(navController)
        }

        // вот тут мы и смотрим разрешения
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissionRequestHandler.requestPermission(android.Manifest.permission.READ_MEDIA_IMAGES)
        } else {
            permissionRequestHandler.requestPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
}
