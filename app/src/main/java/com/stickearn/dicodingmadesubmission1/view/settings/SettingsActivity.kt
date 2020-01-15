package com.stickearn.dicodingmadesubmission1.view.settings

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.helper.PrefHelper
import com.stickearn.dicodingmadesubmission1.util.AlarmReceiver
import com.stickearn.dicodingmadesubmission1.util.AlarmReceiverV2
import kotlinx.android.synthetic.main.activity_reminder_settings.*
import kotlinx.android.synthetic.main.layout_toolbar.*

/**
 * Created by devis on 2020-01-14
 */

class SettingsActivity : AppCompatActivity() {

    companion object {
        fun startThisActivity(context: Context) {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reminder_settings)

        initToolbar()
        initView()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initToolbar() {
        tv_toolbar_title.text = resources.getString(R.string.title_settings)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initView() {
        var isDailyEnabled = PrefHelper(this).getReminderDaily()
        var isReleaseEnabled = PrefHelper(this).getReminderRelease()

        if (isDailyEnabled) {
            AlarmReceiver().enableDailyReminder(
                this,
                "07:00"
            )
        }

        if (isReleaseEnabled) {
            AlarmReceiverV2().enableReleaseReminder(
                this,
                "08:00"
            )
        }

        switch_daily.isChecked = isDailyEnabled
        switch_release.isChecked = isReleaseEnabled

        switch_daily.isChecked = isDailyEnabled
        switch_release.isChecked = isReleaseEnabled

        switch_daily.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AlarmReceiver().enableDailyReminder(
                    this,
                    "07:00"
                )
            } else {
                AlarmReceiver().disableReminder(this)
            }
            isDailyEnabled = isChecked
            PrefHelper(this).setReminderDaily(isDailyEnabled)
        }

        switch_release.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AlarmReceiverV2().enableReleaseReminder(
                    this,
                    "08:00"
                )
            } else {
                AlarmReceiverV2().disableReminder(this)
            }
            isReleaseEnabled = isChecked
            PrefHelper(this).setReminderRelease(isReleaseEnabled)
        }


        if (Build.VERSION.SDK_INT > 22) {
            switch_release.thumbDrawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_custom)
            switch_release.trackDrawable = ContextCompat.getDrawable(this, R.drawable.switch_color)
            switch_release.thumbDrawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_custom)
            switch_release.trackDrawable = ContextCompat.getDrawable(this, R.drawable.switch_color)

            switch_daily.thumbDrawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_custom)
            switch_daily.trackDrawable = ContextCompat.getDrawable(this, R.drawable.switch_color)
            switch_daily.thumbDrawable = ContextCompat.getDrawable(this, R.drawable.switch_thumb_custom)
            switch_daily.trackDrawable = ContextCompat.getDrawable(this, R.drawable.switch_color)
        }
    }

}