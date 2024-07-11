package ru.itis.mygarden.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentAddBinding
import ru.itis.mygarden.presentation.PlantViewModel
import ru.itis.mygarden.presentation.PlantViewModelFactory
import ru.itis.mygarden.util.TranslatorRUtoEN

class AddFragment : Fragment(R.layout.fragment_add) {

    private var binding: FragmentAddBinding? = null
    private val translatorRUtoEN: TranslatorRUtoEN = TranslatorRUtoEN(lifecycleScope)
    private lateinit var viewModel: PlantViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        val context = requireContext()
        viewModel = ViewModelProvider(this, PlantViewModelFactory(context))[PlantViewModel::class.java]
        binding?.run {
            searchButton.setOnClickListener {
                Toast.makeText(context, "Подключение к API", Toast.LENGTH_LONG).show()
                translatorRUtoEN.translate(enteringText.text.toString()) { translatedText ->
                    lifecycleScope.launch {
                        Toast.makeText(context, "Растение добавляется, пожалуйста подождите", Toast.LENGTH_LONG).show()
                        if (!viewModel.addPlantFromApi(translatedText))
                            Toast.makeText(context, "Такого растения нет( Попробуйте добавить самостоятельно", Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(context, "Растение успешно добавлено", Toast.LENGTH_LONG).show()
                    }
                }

            }
            buttonAdding.setOnClickListener{
                findNavController().navigate(resId = R.id.action_addFragment_to_createFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}