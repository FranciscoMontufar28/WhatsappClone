package com.francisco.whatsapptest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.francisco.whatsapptest.databinding.FragmentOnBoardingBinding
import com.francisco.whatsapptest.util.hideKeyboard
import com.francisco.whatsapptest.util.isValidMobile

class OnBoardingFragment : Fragment() {

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        goToCodeVerification()
    }

    private fun verifyPhoneNumber(): Boolean {
        return getPhoneNumber().isValidMobile()
    }

    private fun goToCodeVerification() {
        binding.btValidateCodeOnboarding.setOnClickListener { view ->
            view.hideKeyboard()
            if (verifyPhoneNumber()) {
                val action =
                    OnBoardingFragmentDirections.actionOnBoardingFragmentToCodeVerificationFragment(
                        getPhoneNumber()
                    )
                Navigation.findNavController(view).navigate(action)
            } else {
                showToast("Please type a valid number")
            }
        }
    }

    private fun getPhoneNumber(): String {
        val code = binding.cpCodeArea.selectedCountryCodeWithPlus
        val phone = binding.etPhone.text.toString()
        return "$code$phone"
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}