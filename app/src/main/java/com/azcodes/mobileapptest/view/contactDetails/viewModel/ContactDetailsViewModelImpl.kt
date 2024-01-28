package com.azcodes.mobileapptest.view.contactDetails.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.azcodes.mobileapptest.model.ContactDetails
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.azcodes.mobileapptest.view.contactDetails.viewModel.ContactDetailsViewModel.ContactDetailsEvent
import com.google.firebase.database.database

class ContactDetailsViewModelImpl : ViewModel(), ContactDetailsViewModel {

    private var databaseReference: DatabaseReference? = null

    override val event: LiveData<ContactDetailsEvent>
        get() = _mutableEvent
    private val _mutableEvent: MutableLiveData<ContactDetailsEvent> =
        MutableLiveData()

    override fun init(contactDetails: ContactDetails) {
        _mutableEvent.postValue(
            ContactDetailsEvent.OnDisplayContactDetails(
                contactDetails
            )
        )
    }

    override fun saveContact(contactDetails: ContactDetails, position: Int) {
        databaseReference = Firebase.database.reference

        databaseReference?.let {
            val newContact = ContactDetails(
                contactDetails.id,
                contactDetails.firstName,
                contactDetails.lastName,
                contactDetails.email,
                contactDetails.dob
            )

            it.child(position.toString()).setValue(newContact)
                .addOnSuccessListener {
                    _mutableEvent.postValue(
                        ContactDetailsEvent.OnDisplaySuccessSubmission
                    )
                }.addOnFailureListener {
                    _mutableEvent.postValue(
                        ContactDetailsEvent.OnDisplayFailureSubmission
                    )
                }
        }
    }
}