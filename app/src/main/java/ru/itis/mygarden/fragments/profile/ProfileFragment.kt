package ru.itis.mygarden.fragments.profile

import android.R.attr.name
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.database.Cursor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import ru.itis.mygarden.R
import ru.itis.mygarden.data.User
import ru.itis.mygarden.databinding.FragmentProfileBinding
import ru.itis.mygarden.presentation.PlantViewModel
import ru.itis.mygarden.presentation.PlantViewModelFactory


@Suppress("DEPRECATION")
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private var binding: FragmentProfileBinding? = null
    var SELECT_PICTURE: Int = 1234
    private lateinit var viewModel: PlantViewModel
    var selectedImageUri : Uri? = null

    var uri : String? = null
    var name : String? = null
    val bundle = Bundle()

    var flag = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProfileBinding.bind(view)


        uri = arguments?.getString("Uri_string")
        name = arguments?.getString("Edit_text")


        val context = requireContext()
        viewModel = ViewModelProvider(this, PlantViewModelFactory(context)).get(PlantViewModel::class.java)
        viewModel.getUser()
        receiveData()
        flag = savedInstanceState?.getBoolean("Uri_boolean") == true


        if (uri != null) binding?.imageView?.setImageURI(Uri.parse(uri))


        if ((savedInstanceState?.getString("Uri_string") != null) && (flag)) {
            uri = savedInstanceState.getString("Uri_string")
        }

        if (flag) {
            selectedImageUri = Uri.parse(savedInstanceState?.getString("Uri_string"))
            binding?.imageView?.setImageURI(selectedImageUri)
        } else {
            binding?.imageView?.setImageURI(Uri.parse((viewModel.userStateFlow.value ?: "").toString()))
        }


        if (name != null) binding?.nameUser?.setText(name)


        binding?.run {
            lifecycleScope.launch{
                countPlants.text = viewModel.getAllPlants().size.toString()
            }

            var counter = 0
            writeName.setOnClickListener {
                counter++
                if (counter % 2 == 0) {
                    nameUser.isEnabled = false
                    viewModel.updateUser(nameUser.text.toString(), viewModel.userStateFlow.value?.imagePath ?: "")
                    Snackbar.make(
                        root,
                        "Имя пользователя подтверждено, чтобы поменять нажмите на кнопку ещё раз ",
                        Snackbar.LENGTH_LONG
                    ).show()
                } else {
                    nameUser.isEnabled = true
                    Snackbar.make(
                        root,
                        "Вы можете изменить имя пользователя ",
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }

            makeAvatar.setOnClickListener {
                imageChooser()
            }


            infoButton.setOnClickListener{
                bundle.putString("Uri_string",uri)
                bundle.putString("Edit_text",binding?.nameUser?.text.toString())
                findNavController().navigate(resId = R.id.action_profileFragment_to_infoFragment,bundle)
            }
        }
    }


    fun imageChooser() {
        val i = Intent()
        i.setAction(Intent.ACTION_PICK)
        val pictureDirectory = Environment.getExternalStorageDirectory()
        val data = Uri.parse(pictureDirectory.path)
        val type = "image/*"
        i.setDataAndType(data, type)
        startActivityForResult(i, SELECT_PICTURE)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                selectedImageUri = data?.data
                selectedImageUri?.let {
                    binding?.imageView?.setImageURI(it)
                    flag = true
                    /* User у нас один-единственный,
                    так что предлагаю не париться и захардкодить эту историю
                     */
                    val name = binding?.nameUser?.text.toString()
                    val fullPath = fetchFullPath(it).getOrDefault("")
                    println(fullPath)
                    viewModel.updateUser(name = name, imagePath = fullPath!!)
                    uri = selectedImageUri.toString()
                }
            }
        }
    }

    private fun fetchFullPath(uri : Uri) : Result<String?> {
        var cursor: Cursor? = null
        return runCatching {
            val proj = arrayOf(MediaStore.Images.Media.DATA)
            cursor = requireContext().contentResolver.query(uri, proj, null, null, null)
            cursor?.let {
                val columnIndex = it.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                it.moveToFirst()
                return@let it.getString(columnIndex)
            }
        }.onSuccess {
            cursor?.close()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("Uri_string",selectedImageUri.toString())
        outState.putBoolean("Uri_boolean",flag)
    }

    private fun receiveData() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.userStateFlow.collect { user ->
                    binding?.nameUser?.setText(user?.name)
                    val bitmap = BitmapFactory.decodeFile(user?.imagePath)
                    binding?.imageView?.setImageBitmap(bitmap)
                }
            }
        }
    }
}