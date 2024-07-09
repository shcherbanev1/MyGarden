package ru.itis.mygarden.fragments.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.lifecycleScope
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentAddBinding
import ru.itis.mygarden.util.Translator

class AddFragment : Fragment(R.layout.fragment_add) {

    private var binding: FragmentAddBinding? = null
    private val translator: Translator = Translator(lifecycleScope)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)
        translator.translate("Алоэ") { translatedText ->
            println("Translated Text: $translatedText")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}