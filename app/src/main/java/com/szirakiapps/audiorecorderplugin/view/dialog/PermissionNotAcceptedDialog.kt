package com.szirakiapps.audiorecorderplugin.view.dialog

import android.app.Activity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import com.szirakiapps.audiorecorderplugin.R
import com.szirakiapps.audiorecorderplugin.databinding.DialogPermissionRejectedBinding

class PermissionNotAcceptedDialog : SlidingInDialog() {

    override fun layoutResourceId(): Int = R.layout.dialog_permission_rejected

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = DataBindingUtil.bind<DialogPermissionRejectedBinding>(view) ?: return

        binding.btnExit.setOnClickListener {
            requireActivity().setResult(Activity.RESULT_CANCELED)
            requireActivity().finish()
        }

        isCancelable = false
    }

    companion object {
        fun show(activity: FragmentActivity) {
            PermissionNotAcceptedDialog()
                .show(activity.supportFragmentManager, "permissionNotAcceptedDialog")
        }
    }
}