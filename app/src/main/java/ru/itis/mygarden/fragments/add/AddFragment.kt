package ru.itis.mygarden.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentAddBinding
import ru.itis.mygarden.presentation.PlantViewModel
import ru.itis.mygarden.presentation.PlantViewModelFactory
import ru.itis.mygarden.util.Translator

class AddFragment : Fragment(R.layout.fragment_add) {

    private var binding: FragmentAddBinding? = null
    private val translator: Translator = Translator(lifecycleScope)
    private lateinit var viewModel: PlantViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        val context = requireContext()
        viewModel = ViewModelProvider(this, PlantViewModelFactory(context))[PlantViewModel::class.java]
        binding?.run {
            searchButton.setOnClickListener {

                translator.translate(enteringText.text.toString()) { translatedText ->
                    lifecycleScope.launch {
                        Toast.makeText(context, "Растение добавляется, пожалуйста подождите", Toast.LENGTH_LONG).show()
                        if (!viewModel.addPlantFromApi(translatedText))
                            Toast.makeText(context, "Такого растения нет( Попробуйте добавить самостоятельно", Toast.LENGTH_LONG).show()
                        else
                            Toast.makeText(context, "Рстение успешно добавлено", Toast.LENGTH_LONG).show()
                    }
                }

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}