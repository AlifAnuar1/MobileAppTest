package com.azcodes.mobileapptest.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ContactDetails(

    @SerializedName("id")
    var id: String? = "",

    @SerializedName("firstName")
    var firstName: String? = "",

    @SerializedName("lastName")
    var lastName: String? = "",

    @SerializedName("email")
    var email: String? = "",

    @SerializedName("dob")
    var dob: String? = ""
) : Parcelable
