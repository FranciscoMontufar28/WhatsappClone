package com.francisco.whatsapptest.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.francisco.whatsapptest.R
import com.francisco.whatsapptest.WhatsApp
import com.francisco.whatsapptest.databinding.FragmentOnBoardingBinding
import com.francisco.whatsapptest.di.CompleteInformationModule
import com.francisco.whatsapptest.di.OnBoardingComponent
import com.francisco.whatsapptest.di.OnBoardingModule
import com.francisco.whatsapptest.presentation.OnBoardingViewModel
import com.francisco.whatsapptest.util.getViewModel
import com.francisco.whatsapptest.util.hideKeyboard
import com.francisco.whatsapptest.util.isValidMobile

class OnBoardingFragment : Fragment() {

    private lateinit var onBoardingComponent: OnBoardingComponent
    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!
    private val onBoardingViewModel: OnBoardingViewModel by lazy {
        getViewModel { onBoardingComponent.onBoardingViewModel }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        onBoardingComponent =
            (requireActivity().application as WhatsApp).getAppComponent().inject(
                OnBoardingModule()
            )
        onBoardingViewModel.getUserLoginAuthStatus(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)
        onBoardingViewModel.event.observe(viewLifecycleOwner, Observer {
            if (it) {
                val navHostFragment =
                    requireActivity().supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
                val navController = navHostFragment.navController
                navController.navigate(R.id.action_onBoardingFragment_to_mainChatFragment)
            }
        })
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