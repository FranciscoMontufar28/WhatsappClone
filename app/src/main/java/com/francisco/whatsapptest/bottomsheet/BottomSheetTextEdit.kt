package com.francisco.whatsapptest.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.francisco.whatsapptest.databinding.BottomSheetEditTextBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

enum class BottomSheetTextType {
    USER_NAME,
    USER_ABOUT
}

interface BottomSheetTextCallback {
    fun onSave(bottomSheetTextType: BottomSheetTextType, text: String)
}

class BottomSheetTextEdit(
    val bottomSheetTextCallback: BottomSheetTextCallback,
    val bottomSheetTextType: BottomSheetTextType
) :
    BottomSheetDialogFragment() {

    companion object {
        fun getInstance(
            bottomSheetTextCallback: BottomSheetTextCallback,
            bottomSheetTextType: BottomSheetTextType
        ): BottomSheetTextEdit {
            return BottomSheetTextEdit(bottomSheetTextCallback, bottomSheetTextType)
        }
    }

    private var _binding: BottomSheetEditTextBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetEditTextBinding.inflate(inflater, container, false)

        val titleView = binding.tvEditUserTitle
        when (bottomSheetTextType) {
            BottomSheetTextType.USER_NAME -> {
                titleView.text = "Escribe tu nombre"
                binding.btEditUserSave.setOnClickListener {
                    val textUpdate = binding.etEditUserText.text.toString()
                    bottomSheetTextCallback.onSave(BottomSheetTextType.USER_NAME, textUpdate)
                }
            }
            BottomSheetTextType.USER_ABOUT -> {
                titleView.text = "Actualiza tu estado"
                binding.btEditUserSave.setOnClickListener {
                    val textUpdate = binding.etEditUserText.text.toString()
                    bottomSheetTextCallback.onSave(BottomSheetTextType.USER_ABOUT, textUpdate)
                }
            }
        }
        return binding.root
    }
}