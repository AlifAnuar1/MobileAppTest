package com.azcodes.mobileapptest.view.contactList.view

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.azcodes.mobileapptest.databinding.ActivityContactListBinding
import com.azcodes.mobileapptest.model.ContactDetails
import com.azcodes.mobileapptest.view.contactDetails.view.ContactDetailsActivity
import com.azcodes.mobileapptest.view.contactList.adapter.ContactListAdapter
import com.azcodes.mobileapptest.view.contactList.viewModel.ContactListViewModel
import com.azcodes.mobileapptest.view.contactList.viewModel.ContactListViewModelImpl
import com.google.firebase.FirebaseApp

class ContactListActivity : AppCompatActivity() {

    private val viewModel: ContactListViewModelImpl by lazy {
        ViewModelProvider(this)[ContactListViewModelImpl::class.java]
    }

    private val binding: ActivityContactListBinding by lazy {
        ActivityContactListBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this@ContactListActivity)

        initUI()
        setupViewModel()
    }

    private fun initUI() {
        binding.ivAddContact.setOnClickListener {
            onDisplayAddContactDialog()
        }

        binding.ivSearch.setOnClickListener {
            onDisplaySearchDialog()
        }
    }

    private fun setupViewModel() {
        viewModel.init()

        viewModel.event.observe(this) {
            when (it) {
                is ContactListViewModel.ContactListEvent.OnDisplayContactList -> {
                    binding.pbLoadingBar.visibility = View.GONE
                    onDisplayContactList(it.contactList)
                }

                ContactListViewModel.ContactListEvent.ShowProgressDialog -> {
                    binding.pbLoadingBar.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun onDisplayAddContactDialog() {

    }

    private fun onDisplaySearchDialog() {

    }

    private fun onDisplayContactList(contactList: ArrayList<ContactDetails>) {
        val rvAdapter = ContactListAdapter(contactList)
        binding.rvMosqueList.layoutManager = LinearLayoutManager(this)
        binding.rvMosqueList.adapter = rvAdapter

        rvAdapter.setOnClickListener(object : ContactListAdapter.OnClickListener {
            override fun onDetailClick(detail: ContactDetails, position: Int) {
                ContactDetailsActivity.startActivity(
                    this@ContactListActivity,
                    contactDetails = detail,
                    position = position
                )
            }
        })
    }
}
