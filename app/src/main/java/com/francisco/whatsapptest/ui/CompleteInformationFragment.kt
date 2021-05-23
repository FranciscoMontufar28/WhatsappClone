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
import com.francisco.whatsapptest.WhatsApp
import com.francisco.whatsapptest.databinding.FragmentCompleteInformationBinding
import com.francisco.whatsapptest.di.CompleteInformationComponent
import com.francisco.whatsapptest.di.CompleteInformationModule
import com.francisco.whatsapptest.presentation.CompleteInformationViewModel
import com.francisco.whatsapptest.util.Event
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
    lateinit var mImageFile: File

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
            }
        }
    }

    private fun toGoMessages() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == 100) {
            data?.let {
                val returnValue = it.getStringArrayListExtra(Pix.IMAGE_RESULTS)
                returnValue?.let { value ->
                    mImageFile = File(value[0])
                    binding.ivCompleteInformation.setImageBitmap(BitmapFactory.decodeFile(mImageFile.absolutePath))
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
                    Pix.start(requireActivity(), mOptions);
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
            .setScreenOrientation(Options.SCREEN_ORIENTATION_LANDSCAPE)
            .setPath("/pix/images")
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