package com.azcodes.mobileapptest.view.contactList.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.azcodes.mobileapptest.databinding.ItemContactsBinding
import com.azcodes.mobileapptest.model.ContactDetails

class ContactListAdapter(
    private var contactList: ArrayList<ContactDetails>,
) : RecyclerView.Adapter<ContactListAdapter.ViewHolder>(), Filterable {

    private var isSearch = false
    private var onClickListener: OnClickListener? = null
    private val constItems: ArrayList<ContactDetails> = contactList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemContactsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    inner class ViewHolder(val binding: ItemContactsBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {
            with(contactList[position]) {
                binding.tvContactName.text = this.firstName

                holder.itemView.setOnClickListener {
                    if (onClickListener != null) {
                        onClickListener!!.onDetailClick(contactList[position], position)
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return contactList.size
    }

    fun setOnClickListener(onClickListener: OnClickListener?) {
        this.onClickListener = onClickListener
    }

    interface OnClickListener {
        fun onDetailClick(detail: ContactDetails, position: Int)
    }

    fun filterSearchItem(queryString: String) {
        filter.filter(queryString)
        this.isSearch = queryString.isNotEmpty()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults? {
                val charString = charSequence.toString()
                val filtered: ArrayList<ContactDetails>
                try {
                    if (charString.isEmpty()) {
                        filtered = constItems
                    } else {
                        val filteredList: ArrayList<ContactDetails> = ArrayList()
                        constItems.let {
                            if (constItems.size > 0) {
                                for (contact in constItems) {
                                    contact.let {
                                        if (contact.firstName != null && contact.firstName?.contains(
                                                charString,
                                                ignoreCase = true
                                            ) == true && !filteredList.contains(contact)
                                        ) {
                                            filteredList.add(contact)
                                        } else {

                                        }

                                    }
                                }
                            }
                        }
                        filtered = filteredList
                    }
                    val filterResults = FilterResults()
                    filterResults.values = filtered
                    return filterResults
                } catch (e: Exception) {
                    return null
                }
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                if (filterResults.values != null) {
                    contactList = filterResults.values as ArrayList<ContactDetails>
                }
                notifyDataSetChanged()
            }
        }
    }
}