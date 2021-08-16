package com.francisco.whatsapptest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.francisco.domain.UserDomain
import com.francisco.imagemanager.loadUrlImage
import com.francisco.whatsapptest.R
import com.francisco.whatsapptest.WhatsApp
import com.francisco.whatsapptest.adapter.BottomSheetImageSelector
import com.francisco.whatsapptest.databinding.FragmentUserProfileBinding
import com.francisco.whatsapptest.di.UserProfileComponent
import com.francisco.whatsapptest.di.UserProfileModule
import com.francisco.whatsapptest.presentation.CompleteInformationViewModel
import com.francisco.whatsapptest.presentation.UserProfileViewModel
import com.francisco.whatsapptest.util.Event
import com.francisco.whatsapptest.util.ImageCode
import com.francisco.whatsapptest.util.ToolbarUtils
import com.francisco.whatsapptest.util.getViewModel


class UserProfileFragment : Fragment() {

    private lateinit var userProfileComponent: UserProfileComponent
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    private val userProfileViewModel: UserProfileViewModel by lazy {
        getViewModel { userProfileComponent.userProfileViewModel }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userProfileComponent = (requireActivity().application as WhatsApp).getAppComponent().inject(
            UserProfileModule()
        )
        onBackButtonPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        userProfileViewModel.event.observe(viewLifecycleOwner, Observer(::onEventListener))
        onProfileImageButtonClick()
        getUserInformation()
    }

    private fun getUserInformation() {
        userProfileViewModel.getUserInformation()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ToolbarUtils.show(requireActivity() as AppCompatActivity, "Profile") { view ->
            view.findNavController().navigate(R.id.action_userProfileFragment_to_mainChatFragment)
        }
    }

    private fun onBackButtonPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(
            this,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (isEnabled) {
                        isEnabled = false
                        findNavController().navigate(R.id.action_userProfileFragment_to_mainChatFragment)
                    }
                }
            })
    }

    private fun onProfileImageButtonClick() {
        binding.btUpdateProfileImage.setOnClickListener {
            val bottomSheet = BottomSheetImageSelector.getInstance()
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }
    }

    private fun onEventListener(event: Event<UserProfileViewModel.UserProfileNavigation>) {
        event.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is UserProfileViewModel.UserProfileNavigation.UserData -> navigation.run {
                    updateUserData(this.userDomain)
                }
                is UserProfileViewModel.UserProfileNavigation.Error -> navigation.run {
                    errorMessage(this.message)
                }
                is UserProfileViewModel.UserProfileNavigation.ShowLoading -> showLoading()
                is UserProfileViewModel.UserProfileNavigation.HideLoading -> hideLoader()
            }
        }
    }

    private fun hideLoader() {

    }

    private fun showLoading() {

    }

    private fun errorMessage(error: String) {
        showToast(error)
    }

    private fun updateUserData(userDomain: UserDomain) {
        binding.userProfileName.text = userDomain.nickname
        binding.userProfileAbout.text = userDomain.about
        binding.userProfilePhone.text = userDomain.phone
        userDomain.image?.let { binding.circleImageView.loadUrlImage(requireContext(), it) }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}