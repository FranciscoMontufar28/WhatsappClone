package com.francisco.whatsapptest.ui

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
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
import com.francisco.imagemanager.loadFileImage
import com.francisco.imagemanager.loadUrlImage
import com.francisco.imagemanager.setDrawable
import com.francisco.whatsapptest.R
import com.francisco.whatsapptest.WhatsApp
import com.francisco.whatsapptest.bottomsheet.*
import com.francisco.whatsapptest.databinding.FragmentUserProfileBinding
import com.francisco.whatsapptest.di.UserProfileComponent
import com.francisco.whatsapptest.di.UserProfileModule
import com.francisco.whatsapptest.presentation.UserProfileViewModel
import com.francisco.whatsapptest.util.Event
import com.francisco.whatsapptest.util.ToolbarUtils
import com.francisco.whatsapptest.util.getViewModel
import com.fxn.pix.Options
import com.fxn.pix.Pix
import com.fxn.utility.PermUtil
import java.io.File


class UserProfileFragment : Fragment(), BottomSheetImageSelectorCallback, BottomSheetTextCallback {

    private lateinit var userProfileComponent: UserProfileComponent
    private var _binding: FragmentUserProfileBinding? = null
    private val binding get() = _binding!!
    var mImageFile: File? = null
    lateinit var mOptions: Options
    private val mReturnValues = arrayListOf<String>()
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
        onEditProfileActions()
        getUserInformation()
        initOptions()
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

    private fun onEditProfileActions() {
        binding.btUpdateProfileImage.setOnClickListener {
            val bottomSheet = BottomSheetImageSelector.getInstance(this)
            bottomSheet.show(requireActivity().supportFragmentManager, bottomSheet.tag)
        }

        binding.userProfileNameEdit.setOnClickListener {
            val bottomSheetEditText =
                BottomSheetTextEdit.getInstance(this, BottomSheetTextType.USER_NAME)
            bottomSheetEditText.show(
                requireActivity().supportFragmentManager,
                bottomSheetEditText.tag
            )
        }
        binding.userProfileAboutEdit.setOnClickListener {
            val bottomSheetEditText =
                BottomSheetTextEdit.getInstance(this, BottomSheetTextType.USER_ABOUT)
            bottomSheetEditText.show(
                requireActivity().supportFragmentManager,
                bottomSheetEditText.tag
            )
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
                is UserProfileViewModel.UserProfileNavigation.ImageDeleted -> {
                    getUserInformation()
                    showToast("Imagen de perfil actualizada")
                }
                is UserProfileViewModel.UserProfileNavigation.ImageSavedState -> navigation.run {
                    when (this.imageCode) {
                        com.francisco.whatsapptest.util.ImageCode.SAVEDIMAGE -> {
                            onSavedImage()
                        }
                        com.francisco.whatsapptest.util.ImageCode.IMAGEURL -> {
                            onUrlSaved(this.data!!)
                        }
                    }
                }
                is UserProfileViewModel.UserProfileNavigation.ImageSaved -> navigation.run {
                    showToast("Imagen de perfil actualizada")
                }
                is UserProfileViewModel.UserProfileNavigation.AboutSaved -> navigation.run {
                    getUserInformation()
                    showToast("Descripcion actualizada")
                }
                is UserProfileViewModel.UserProfileNavigation.NameSaved -> navigation.run {
                    getUserInformation()
                    showToast("Nombre actualizado")
                }
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

    private fun onSavedImage() {
        userProfileViewModel.getImageUrl()
    }

    private fun updateUserData(userDomain: UserDomain) {
        binding.userProfileName.text = userDomain.nickname
        binding.userProfileAbout.text = userDomain.about
        binding.userProfilePhone.text = userDomain.phone
        if (userDomain.image != null)
            binding.userProfileImage.loadUrlImage(requireContext(), userDomain.image!!) else
            binding.userProfileImage.setDrawable(requireContext(), R.drawable.ic_person_white)
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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

    private fun onUrlSaved(url: String) {
        userProfileViewModel.updateUserProfileImage(url)
    }

    override fun onClick(type: BottomSheetImageSelectorType) {
        when (type) {
            BottomSheetImageSelectorType.OPEN_GALLERY -> {
            }
            BottomSheetImageSelectorType.REMOVE_PHOTO -> {
                userProfileViewModel.deleteUserProfileImage()
            }
            BottomSheetImageSelectorType.OPEN_CAMERA -> {
                startPix()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            data?.let {
                val returnValue = it.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                returnValue?.let { value ->
                    mImageFile = File(value[0])
                    mImageFile?.let { resultFile ->
                        binding.userProfileImage.loadFileImage(requireContext(), resultFile)
                        userProfileViewModel.saveImageInCloudStore(requireContext(), resultFile)
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

    override fun onSave(bottomSheetTextType: BottomSheetTextType, text: String) {
        when (bottomSheetTextType) {
            BottomSheetTextType.USER_ABOUT -> {
                userProfileViewModel.updateAboutInformation(text)
            }
            BottomSheetTextType.USER_NAME -> {
                userProfileViewModel.updateUserName(text)
            }
        }
    }
}