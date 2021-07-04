package com.francisco.whatsapptest.util

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.francisco.whatsapptest.R

class ToolbarUtils {
    companion object {
        fun show(activity: AppCompatActivity, title: String, onClick: (view: View) -> Unit) {
            val toolbar: Toolbar = activity.findViewById(R.id.toolbar)
            toolbar.title = title
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
            toolbar.setNavigationOnClickListener {
                onClick(it)
            }
        }
    }
}