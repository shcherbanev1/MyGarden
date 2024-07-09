package ru.itis.mygarden.fragments.info

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentInfoBinding

class InfoFragment : Fragment(R.layout.fragment_info) {

    private var binding: FragmentInfoBinding? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)


        val uri = arguments?.getString("Uri_string")
        val name = arguments?.getString("Edit_text")
        val bundle = Bundle()
        bundle.putString("Uri_string",uri)
        bundle.putString("Edit_text",name)


        binding?.run {
            tvDev1.movementMethod = LinkMovementMethod.getInstance()
            tvDev2.movementMethod = LinkMovementMethod.getInstance()
            tvDev3.movementMethod = LinkMovementMethod.getInstance()
            tvDev4.movementMethod = LinkMovementMethod.getInstance()
            tvDev5.movementMethod = LinkMovementMethod.getInstance()


            exitToProfile.setOnClickListener{
               findNavController().navigate(resId = R.id.action_infoFragment_to_profileFragment,bundle)
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}