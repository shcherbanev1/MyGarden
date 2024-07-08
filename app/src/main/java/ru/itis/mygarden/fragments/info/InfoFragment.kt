package ru.itis.mygarden.fragments.info

import android.os.Bundle
import android.text.method.LinkMovementMethod
import androidx.fragment.app.Fragment
import android.view.View
import androidx.navigation.fragment.findNavController
import ru.itis.mygarden.R
import ru.itis.mygarden.databinding.FragmentInfoBinding
import ru.itis.mygarden.fragments.profile.SharedViewModel

@Suppress("DEPRECATION")
class InfoFragment : Fragment(R.layout.fragment_info) {

    private var binding: FragmentInfoBinding? = null
    private lateinit var viewModelTransition: SharedViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentInfoBinding.bind(view)

        val data = Bundle().apply { putString("ARGUMENT_MESSAGE", "") }


        binding?.run {
            tvDev1.movementMethod = LinkMovementMethod.getInstance()
            tvDev2.movementMethod = LinkMovementMethod.getInstance()
            tvDev3.movementMethod = LinkMovementMethod.getInstance()
            tvDev4.movementMethod = LinkMovementMethod.getInstance()
            tvDev5.movementMethod = LinkMovementMethod.getInstance()


            exitToProfile.setOnClickListener{
//               findNavController().navigate(resId = R.id.action_infoFragment_to_profileFragment)
             viewModelTransition.bundleFromFragmentBToFragmentA.value = data
                requireActivity().onBackPressed()
            }
//       viewModelTransition.bundleFromFragmentBToFragmentA.value = data потыкав, я нашел что вылетает, когда вот эта строчка появляется
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}