package com.azcodes.mobileapptest.view.contactDetails.view

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.azcodes.mobileapptest.databinding.ActivityContactDetailsBinding
import com.azcodes.mobileapptest.model.ContactDetails
import com.azcodes.mobileapptest.view.contactDetails.viewModel.ContactDetailsViewModelImpl
import com.azcodes.mobileapptest.view.contactDetails.viewModel.ContactDetailsViewModel.ContactDetailsEvent
import com.google.firebase.FirebaseApp

class ContactDetailsActivity : AppCompatActivity() {

    private val viewModel: ContactDetailsViewModelImpl by lazy {
        ViewModelProvider(this)[ContactDetailsViewModelImpl::class.java]
    }

    private val binding: ActivityContactDetailsBinding by lazy {
        ActivityContactDetailsBinding.inflate(layoutInflater)
    }

    companion object {
        private const val INTENT_CONTACT_DETAILS = "ContactDetails"
        private const val INTENT_POSITION = "ContactPosition"

        fun startActivity(mContext: Activity, contactDetails: ContactDetails, position: Int) {
            val intent = Intent(mContext, ContactDetailsActivity::class.java)
            intent.putExtra(INTENT_CONTACT_DETAILS, contactDetails)
            intent.putExtra(INTENT_POSITION, position)
            mContext.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this@ContactDetailsActivity)

        initUI()
        setupViewModel()
    }

    private fun initUI() {
        val contactDetails =
            intent.extras?.get(INTENT_CONTACT_DETAILS) as ContactDetails?

        contactDetails?.let { contactDetails ->
            viewModel.init(contactDetails)

            binding.tvCancel.setOnClickListener { finish() }

            binding.tvSave.setOnClickListener {
                //displayUpdateLocationDialog(mosqueProfileDetails)
            }
        }
    }

    private fun setupViewModel() {
        viewModel.event.observe(this) {
            when (it) {

                is ContactDetailsEvent.OnDisplayContactDetails -> {
                    displayDetails(it.contactDetails)
                }

                ContactDetailsEvent.OnDisplayFailureSubmission -> {
                    showLongToast(this@ContactDetailsActivity, "Fail")
                }

                ContactDetailsEvent.OnDisplaySuccessSubmission -> {
                    showLongToast(this@ContactDetailsActivity, "Success")
                }
            }
        }
    }

    private fun displayDetails(contactDetails: ContactDetails) {

        contactDetails.let {

            it.firstName?.let { firstName ->
                binding.tvFirstName.text = firstName
            }

            it.lastName?.let { lastName ->
                binding.tvLastName.text = lastName
            }

            it.email?.let { email ->
                binding.tvEmail.text = email
            }

            it.dob?.let { dob ->
                binding.tvDob.text = dob
            }
        }
    }

    private fun showLongToast(context: Context, text: String) {
        return Toast.makeText(
            context,
            text,
            Toast.LENGTH_LONG
        ).show()
    }
}