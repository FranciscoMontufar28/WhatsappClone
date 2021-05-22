package com.francisco.whatsapptest.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.francisco.whatsapptest.R
import com.francisco.whatsapptest.WhatsApp
import com.francisco.whatsapptest.databinding.FragmentCompleteInformationBinding
import com.francisco.whatsapptest.di.CompleteInformationComponent
import com.francisco.whatsapptest.di.CompleteInformationModule
import com.francisco.whatsapptest.presentation.CodeVerificationViewModel
import com.francisco.whatsapptest.presentation.CompleteInformationViewModel
import com.francisco.whatsapptest.util.Event
import com.francisco.whatsapptest.util.SuccessCode
import com.francisco.whatsapptest.util.getViewModel

class CompleteInformationFragment : Fragment() {

    private lateinit var completeInformationComponent: CompleteInformationComponent
    private var _binding: FragmentCompleteInformationBinding? = null
    private val binding get() = _binding!!
    private val completeInformationViewModel: CompleteInformationViewModel by lazy {
        getViewModel { completeInformationComponent.completeInformationViewModel }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        completeInformationComponent =
            (requireActivity().application as WhatsApp).getAppComponent().inject(
                CompleteInformationModule()
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCompleteInformationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onSaveActionButton()
    }

    private fun onEventListener(event: Event<CompleteInformationViewModel.CompleteInformationNavigation>) {
        event.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is CompleteInformationViewModel.CompleteInformationNavigation.VerificationSuccess -> toGoMessages()
                is CompleteInformationViewModel.CompleteInformationNavigation.VerificationError -> navigation.run {
                    errorMessage(this.error)
                }
                is CompleteInformationViewModel.CompleteInformationNavigation.ShowLoading -> showLoading()
                is CompleteInformationViewModel.CompleteInformationNavigation.HideLoading -> hideLoader()
            }
        }
    }

    private fun toGoMessages() {

    }

    private fun onSaveActionButton() {
        binding.btCompleteInformation.setOnClickListener {
            val username = binding.etCompleteUsername.text.toString()
            updateAuthUser(username, null)
        }
    }

    private fun updateAuthUser(userName: String, image: String?) {
        completeInformationViewModel.updateAuthCurrentUser(userName, image)
    }

    private fun hideLoader() {
        binding.pbCompleteInformation.visibility = View.GONE
    }

    private fun showLoading() {
        binding.pbCompleteInformation.visibility = View.VISIBLE
    }

    private fun errorMessage(message: String) {
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}