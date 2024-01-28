package com.azcodes.mobileapptest.view.contactList.viewModel

import android.location.Location
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.azcodes.mobileapptest.model.ContactDetails
import com.azcodes.mobileapptest.view.contactList.viewModel.ContactListViewModel.ContactListEvent
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.launch

class ContactListViewModelImpl : ViewModel(), ContactListViewModel {

    private var databaseReference: DatabaseReference? = null

    private var contactList: List<ContactDetails>? = null
    private var sortedContactList: List<ContactDetails>? = null

    override val event: LiveData<ContactListEvent>
        get() = _mutableEvent
    private val _mutableEvent: MutableLiveData<ContactListEvent> =
        MutableLiveData()

    override fun init() {
        getContactList()
    }

    private fun getContactList() {
        viewModelScope.launch {
            _mutableEvent.postValue(ContactListEvent.ShowProgressDialog)

            databaseReference = Firebase.database.reference

            databaseReference?.let {
                it.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        contactList = ArrayList()
                        for (item in snapshot.children) {
                            val dataClass: ContactDetails? =
                                item.getValue(ContactDetails::class.java)

                            dataClass?.let {
                                (contactList as ArrayList<ContactDetails>).add(dataClass)
                            }
                        }

                        _mutableEvent.postValue(ContactListEvent.OnDisplayContactList(contactList as ArrayList<ContactDetails>))
                    }

                    override fun onCancelled(error: DatabaseError) {}
                })
            }
        }
    }

}