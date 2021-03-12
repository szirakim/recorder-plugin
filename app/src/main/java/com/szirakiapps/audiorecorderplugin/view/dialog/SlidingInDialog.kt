package com.szirakiapps.audiorecorderplugin.view.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.szirakiapps.audiorecorderplugin.R

abstract class SlidingInDialog : DialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        return inflater.inflate(layoutResourceId(), container, false)
    }

    override fun onStart() {
        super.onStart()

        val params = dialog?.window?.attributes ?: return
        params.horizontalMargin = 10f
        params.gravity = Gravity.END and Gravity.CENTER_VERTICAL
        params.windowAnimations = R.style.DialogEndAnimation

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.attributes = params
    }

    abstract fun layoutResourceId(): Int
}
