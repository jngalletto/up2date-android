package com.mooveit.genesis.model.source

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    val id: String? = "",
    val name: String? = ""
) : Parcelable