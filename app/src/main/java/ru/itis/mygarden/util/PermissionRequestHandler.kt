package ru.itis.mygarden.util

import androidx.activity.result.contract.ActivityResultContracts
import ru.itis.mygarden.MainActivity


// вот тут лучше вообще не копать
// а вообще, юзается, чтобы получать разрешение на чтение картиночек
class PermissionRequestHandler (
    private val activity: MainActivity,
    private val showRationaleCallback: (() -> Unit)? = null,
    private val deniedCallback: (() -> Unit)? = null
) {

    private var currentPermission : String? = null

    private val singlePermissionRequest = activity
        .registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                println("Done!")
            } else {
                currentPermission?.let { currentPermission ->
                    if (currentPermission.isNotEmpty() && activity.shouldShowRequestPermissionRationale(currentPermission)) {
                        showRationaleCallback?.invoke()
                    } else {
                        deniedCallback?.invoke()
                    }
                }
            }
        }

    fun requestPermission(permission : String) {
        currentPermission = permission
        singlePermissionRequest.launch(permission)
    }
}