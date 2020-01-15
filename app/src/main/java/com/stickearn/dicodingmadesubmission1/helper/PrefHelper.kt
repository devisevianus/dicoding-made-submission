package com.stickearn.dicodingmadesubmission1.helper

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by devis on 2020-01-15
 */

class PrefHelper(private val context: Context) {

    companion object {
        private const val PREF_REMINDER_DAILY = "pref_reminder_daily"
        private const val PREF_REMINDER_RELEASE = "pref_reminder_release"
    }

    private fun pref(): SharedPreferences {
        return context.getSharedPreferences("PrefDicoding", Context.MODE_PRIVATE)
    }

    fun setReminderDaily(isEnable: Boolean) {
        pref().edit().putBoolean(PREF_REMINDER_DAILY, isEnable).apply()
    }

    fun getReminderDaily(): Boolean {
        return pref().getBoolean(PREF_REMINDER_DAILY, false)
    }

    fun setReminderRelease(isEnable: Boolean) {
        pref().edit().putBoolean(PREF_REMINDER_RELEASE, isEnable).apply()
    }

    fun getReminderRelease(): Boolean {
        return pref().getBoolean(PREF_REMINDER_RELEASE, false)
    }

}