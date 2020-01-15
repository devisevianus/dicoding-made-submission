package com.stickearn.favoriteapp.helper

import android.annotation.SuppressLint
import com.stickearn.favoriteapp.helper.DateHelper.DATE_DD_MMMM_YYYY
import com.stickearn.favoriteapp.helper.DateHelper.DATE_YYYY_MM_DD
import java.text.SimpleDateFormat

/**
 * Created by devis on 2020-01-05
 */
 
object DateHelper {
    const val DATE_YYYY_MM_DD = "yyyy-MM-dd"
    const val DATE_DD_MMMM_YYYY = "dd MMMM yyyy"
}

@SuppressLint("SimpleDateFormat")
fun String.convertToLong(): Long? {
    val parseDate = SimpleDateFormat(DATE_YYYY_MM_DD).parse(this)
    return parseDate?.time
}

@SuppressLint("SimpleDateFormat")
fun Long.convertDate(): String {
    val sdf = SimpleDateFormat(DATE_DD_MMMM_YYYY)
    return sdf.format(this)
}