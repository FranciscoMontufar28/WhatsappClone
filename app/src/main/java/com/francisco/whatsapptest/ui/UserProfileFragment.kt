package com.francisco.whatsapptest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.francisco.whatsapptest.R
import com.francisco.whatsapptest.databinding.FragmentUserProfileBinding
import com.francisco.whatsapptest.util.ToolbarUtils


class UserProfileFragment : Fragment() {

    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackButtonPressed()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ToolbarUtils.show(requireActivity() as AppCompatActivity, "Profile") { view ->
            view.findNavController().navigate(R.id.action_userProfileFragment_to_mainChatFragment)
        }
    }

    fun onBackButtonPressed() {
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
}