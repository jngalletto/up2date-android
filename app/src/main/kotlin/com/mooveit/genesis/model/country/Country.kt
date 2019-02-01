package com.mooveit.genesis.model.country

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Country(
    val key: String? = "us"
) : Parcelable
