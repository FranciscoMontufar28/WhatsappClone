package com.francisco.whatsapptest.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.francisco.whatsapptest.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetImageSelector : BottomSheetDialogFragment() {

    companion object {
        fun getInstance(): BottomSheetImageSelector {
            return BottomSheetImageSelector()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.botton_sheet_select_image, container, false)
    }
}