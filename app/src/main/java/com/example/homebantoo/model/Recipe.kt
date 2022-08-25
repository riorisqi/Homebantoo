package com.example.homebantoo.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Recipe(
    var title: String? = null,
    var thumb: String? = null,
    var servings: String? = null,
    var times: String? = null,
    var difficulty: String? = null,
    var desc: String? = null,
    var ingridient: String? = null,
    var step: String? = null
): Parcelable