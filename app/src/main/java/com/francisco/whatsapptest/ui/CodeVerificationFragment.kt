package com.francisco.whatsapptest.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.francisco.whatsapptest.MainActivity
import com.francisco.whatsapptest.R
import com.francisco.whatsapptest.WhatsApp
import com.francisco.whatsapptest.databinding.FragmentCodeVerificationBinding
import com.francisco.whatsapptest.di.CodeVerificationComponent
import com.francisco.whatsapptest.di.CodeVerificationModule
import com.francisco.whatsapptest.presentation.CodeVerificationViewModel
import com.francisco.whatsapptest.util.*

class CodeVerificationFragment : Fragment() {

    private lateinit var codeVerificationComponent: CodeVerificationComponent
    private var _binding: FragmentCodeVerificationBinding? = null
    private val binding get() = _binding!!
    private val codeVerificationViewModel: CodeVerificationViewModel by lazy {
        getViewModel { codeVerificationComponent.codeVerificationViewModel }
    }
    var mExtraPhone: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        codeVerificationComponent =
            (requireActivity().application as WhatsApp).getAppComponent().inject(
                CodeVerificationModule()
            )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCodeVerificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mExtraPhone = arguments?.let {
            CodeVerificationFragmentArgs.fromBundle(it).phoneNumber
        }
        mExtraPhone?.let {
            codeVerificationViewModel.authenticateUserByPhoneNumber(it, this.requireActivity())
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        actionButtonValidation()
        observeProducts()
    }

    private fun observeProducts() {
        codeVerificationViewModel.event.observe(viewLifecycleOwner, Observer(::onEventListener))
    }

    private fun onEventListener(event: Event<CodeVerificationViewModel.CodeVerificationNavigation>) {
        event.getContentIfNotHandled()?.let { navigation ->
            when (navigation) {
                is CodeVerificationViewModel.CodeVerificationNavigation.VerificationSuccess -> navigation.run {
                    when (this.type) {
                        SuccessCode.AUTHENTICATEUSER -> {
                            //displayCodeVerification(this.code)
                            validateIfUserExist(this.userId)
                            //saveAuthUser(mExtraPhone)
                        }
                        SuccessCode.USEREXIST -> onUserExist()
                        SuccessCode.NEWUSER -> saveAuthUser(mExtraPhone)
                    }
                }
                is CodeVerificationViewModel.CodeVerificationNavigation.VerificationError -> navigation.run {
                    errorMessage(this.error)
                }
                is CodeVerificationViewModel.CodeVerificationNavigation.ShowLoading -> showLoading()
                is CodeVerificationViewModel.CodeVerificationNavigation.HideLoading -> hideLoader()
                is CodeVerificationViewModel.CodeVerificationNavigation.Error -> showErrorMessage()
            }
        }
    }

    private fun showErrorMessage() {
        showToast("We can't not connect with the service")
    }

    private fun onUserExist() {
        toGoCompleteInformationFragment(VerificationUserResponse.USER_EXIST)
    }

    private fun validateIfUserExist(userId: String) {
        codeVerificationViewModel.validateIfUserExist(userId)
    }

    private fun actionButtonValidation() {
        binding.btValidateCode.setOnClickListener {
            val code = binding.etCodeVerification.text.toString()
            codeVerificationViewModel.signInWithVerificationCode(code)
            it.hideKeyboard()
        }
    }

    private fun toGoCompleteInformationFragment(verificationUserResponse: VerificationUserResponse) {
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        val action =
            CodeVerificationFragmentDirections.actionCodeVerificationFragmentToCompleteInformationFragment(
                verificationUserResponse.toString()
            )
        navController.navigate(action)
    }

    private fun saveAuthUser(mExtraPhone: String?) {
        mExtraPhone?.let { codeVerificationViewModel.saveAuthCurrentUser(it) }
        toGoCompleteInformationFragment(VerificationUserResponse.NEW_USER)
    }

    private fun displayCodeVerification(code: String) {
        binding.etCodeVerification.setText(code)
    }

    private fun hideLoader() {
        binding.pbCodeVerification.visibility = View.GONE
    }

    private fun showLoading() {
        binding.pbCodeVerification.visibility = View.VISIBLE
    }

    private fun errorMessage(message: String) {
        showToast(message)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}