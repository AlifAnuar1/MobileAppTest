package com.azcodes.mobileapptest.view.contactDetails.viewModel

import androidx.lifecycle.LiveData
import com.azcodes.mobileapptest.model.ContactDetails

interface ContactDetailsViewModel {

    val event: LiveData<ContactDetailsEvent>

    fun init(contactDetails: ContactDetails)
    fun saveContact(contactDetails: ContactDetails, position: Int)

    sealed interface ContactDetailsEvent {

        data class OnDisplayContactDetails(val contactDetails: ContactDetails) :
            ContactDetailsEvent

        object OnDisplaySuccessSubmission : ContactDetailsEvent
        object OnDisplayFailureSubmission : ContactDetailsEvent
    }
}