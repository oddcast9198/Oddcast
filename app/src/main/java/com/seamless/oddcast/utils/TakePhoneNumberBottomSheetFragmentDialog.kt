package com.seamless.oddcast.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.seamless.oddcast.R
import com.seamless.oddcast.databinding.PhoneNumberBottomSheetDialogBinding


class TakePhoneNumberBottomSheetFragmentDialog(
    private val phoneNumberBottomSheetListener: PhoneNumberBottomSheetListener
) : BottomSheetDialogFragment(), View.OnClickListener {


    private lateinit var binding: PhoneNumberBottomSheetDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.TransparentBackgroundDialog)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.phone_number_bottom_sheet_dialog,
            container,
            false
        )
        return binding.rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewInitialization()
        listenerInitialization()
        setObservables()
    }

    private fun viewInitialization() {


    }

    private fun listenerInitialization() {

        binding.buttonGoogleLogin.setOnClickListener {
            if (binding.etMobileNumber.text.toString().length == 10) {
                phoneNumberBottomSheetListener.onSavePhoneNumberListener(binding.etMobileNumber.text.toString())
                dismiss()
            } else {
                Toast.makeText(requireContext(), "Enter valid mobile number", Toast.LENGTH_LONG)
                    .show()
            }
        }

    }

    private fun setObservables() {

    }

    override fun onClick(v: View?) {

    }

    interface PhoneNumberBottomSheetListener {
        fun onSavePhoneNumberListener(mobileNumber: String)
    }
}