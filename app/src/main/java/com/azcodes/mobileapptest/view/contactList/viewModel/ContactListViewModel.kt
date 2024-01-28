package com.azcodes.mobileapptest.view.contactList.viewModel

import android.location.Location
import androidx.lifecycle.LiveData
import com.azcodes.mobileapptest.model.ContactDetails

interface ContactListViewModel {

    val event: LiveData<ContactListEvent>

    fun init()

    sealed interface ContactListEvent {

        data class OnDisplayContactList(val contactList: ArrayList<ContactDetails>) :
            ContactListEvent

        object ShowProgressDialog : ContactListEvent
    }
}