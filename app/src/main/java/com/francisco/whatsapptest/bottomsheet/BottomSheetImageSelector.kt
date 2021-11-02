package com.francisco.whatsapptest.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.francisco.whatsapptest.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

enum class BottomSheetImageSelectorType {
    OPEN_GALLERY,
    REMOVE_PHOTO,
    OPEN_CAMERA
}

interface BottomSheetImageSelectorCallback {
    fun onClick(type: BottomSheetImageSelectorType)
}

class BottomSheetImageSelector(val bottomSheetImageSelectorCallback: BottomSheetImageSelectorCallback?) :
    BottomSheetDialogFragment() {

    companion object {
        fun getInstance(bottomSheetImageSelectorCallback: BottomSheetImageSelectorCallback):
                BottomSheetImageSelector {
            return BottomSheetImageSelector(bottomSheetImageSelectorCallback)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.botton_sheet_select_image, container, false)
        view.findViewById<FloatingActionButton>(R.id.botton_sheet_open_gallery)
            .setOnClickListener {
                bottomSheetImageSelectorCallback?.onClick(BottomSheetImageSelectorType.OPEN_GALLERY)
            }
        view.findViewById<FloatingActionButton>(R.id.botton_sheet_remove_photo)
            .setOnClickListener {
                bottomSheetImageSelectorCallback?.onClick(BottomSheetImageSelectorType.REMOVE_PHOTO)
            }
        view.findViewById<FloatingActionButton>(R.id.botton_sheet_open_camera)
            .setOnClickListener {
                bottomSheetImageSelectorCallback?.onClick(BottomSheetImageSelectorType.OPEN_CAMERA)
            }
        return view
    }
}