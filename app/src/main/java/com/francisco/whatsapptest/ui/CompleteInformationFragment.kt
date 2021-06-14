package com.francisco.whatsapptest.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import com.francisco.imagemanager.loadFileImage
import com.francisco.imagemanager.loadUrlImage
import com.francisco.whatsapptest.R
import com.francisco.whatsapptest.WhatsApp
import com.francisco.whatsapptest.databinding.FragmentCompleteInformationBinding
import com.francisco.whatsapptest.di.CompleteInformationComponent
import com.francisco.whatsapptest.di.CompleteInformationModule
import com.francisco.whatsapptest.presentation.CompleteInformationViewModel
import com.francisco.whatsapptest.util.Event
import com.francisco.whatsapptest.util.ImageCode
import com.francisco.whatsapptest.util.VerificationUserResponse
import com.francisco.whatsapptest.util.getViewModel
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import java.io.File

class CompleteInformationFragment : Fragment() {

    private lateinit var completeInformationComponent: CompleteInformationComponent
    private var _binding: FragmentCompleteInformationBinding? = null
    private val binding get() = _binding!!
    private val completeInformationViewModel: CompleteInformationViewModel by lazy {
        getViewModel { completeInformationComponent.completeInformationViewModel }
    }
    private val mReturnValues = arrayListOf<String>()
    lateinit var mOptions: Options
    var mImageFile: File? = null

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
        onImageClickListener()
        onSaveActionButton()
        observeProducts()
        initOptions()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val args = arguments?.let {
            CompleteInformationFragmentArgs.fromBundle(it).userState
        }
        if (args.isNullOrEmpty()
                .not() && args.equals(VerificationUserResponse.USER_EXIST.toString())
        ) {
            getUserInformation()
        }
    }

    private fun getUserInformation() {
        completeInformationViewModel.getUserInformation()
    }

    private fun onImageClickListener() {
        binding.ivCompleteInformation.setOnClickListener {
            startPix()
        }
    }

    private fun observeProducts() {
        completeInformationViewModel.event.observe(viewLifecycleOwner, Observer(::onEventListener))
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
                is CompleteInformationViewModel.CompleteInformationNavigation.ImageSavedState -> navigation.run {
                    when (this.imageCode) {
                        ImageCode.SAVEDIMAGE -> {
                            onSavedImage()
                        }
                        ImageCode.IMAGEURL -> {
                            onUrlSaved(this.data!!)
                        }
                    }
                }
                is CompleteInformationViewModel.CompleteInformationNavigation.UserData -> navigation.run {
                    val userDomain = this.userDomain
                    displayUserInformation(userDomain.nickname, userDomain.image)
                }
            }
        }
    }

    private fun displayUserInformation(nickname: String?, image: String?) {
        nickname?.let { binding.etCompleteUsername.setText(it) }
        image?.let { binding.ivCompleteInformation.loadUrlImage(requireContext(), it) }
    }

    private fun toGoMessages() {
        completeInformationViewModel.setUserLoginAuthStatus(requireContext(), true)
        val navHostFragment =
            requireActivity().supportFragmentManager.findFragmentById(R.id.myNavHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.action_completeInformationFragment_to_mainChatFragment)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            data?.let {
                val returnValue = it.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                returnValue?.let { value ->
                    mImageFile = File(value[0])
                    mImageFile?.let { resultFile ->
                        binding.ivCompleteInformation.loadFileImage(requireContext(), resultFile)
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PermUtil.REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Pix.start(this, mOptions);
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Approve permissions to open Pix ImagePicker",
                        Toast.LENGTH_LONG
                    ).show()
                }
                return
            }
        }
    }

    private fun startPix() {
        Pix.start(this, mOptions)
    }

    private fun initOptions() {
        mOptions = Options.init()
            .setRequestCode(100)
            .setCount(1)
            .setFrontfacing(true)
            .setPreSelectedUrls(mReturnValues)
            .setSpanCount(4)
            .setMode(Options.Mode.Picture)
            .setVideoDurationLimitinSeconds(0)
            .setScreenOrientation(Options.SCREEN_ORIENTATION_PORTRAIT)
            .setPath("/pix/images")
    }


    private fun onSaveActionButton() {
        binding.btCompleteInformation.setOnClickListener {
            if (mImageFile != null) {
                completeInformationViewModel.saveImageInCloudStore(requireContext(), mImageFile!!)
            } else {
                val username = binding.etCompleteUsername.text.toString()
                updateAuthUser(username, null)
            }
        }
    }

    private fun onSavedImage() {
        completeInformationViewModel.getImageUrl()
    }

    private fun onUrlSaved(url: String) {
        val username = binding.etCompleteUsername.text.toString()
        updateAuthUser(username, url)
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