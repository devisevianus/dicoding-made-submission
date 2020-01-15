package com.stickearn.dicodingmadesubmission1.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.stickearn.dicodingmadesubmission1.model.MovieMdl
import android.widget.Toast
import android.app.AlarmManager
import android.app.PendingIntent
import android.util.Log
import com.stickearn.dicodingmadesubmission1.BuildConfig
import com.stickearn.dicodingmadesubmission1.R
import com.stickearn.dicodingmadesubmission1.api.ApiClient
import com.stickearn.dicodingmadesubmission1.api.ApiService
import com.stickearn.dicodingmadesubmission1.model.MovieResponseMdl
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by devis on 2020-01-16
 */

class AlarmReceiverV2 : BroadcastReceiver() {

    companion object {
        private const val ID_RELEASE = 200
    }

    private var moviesList = arrayListOf<MovieMdl>()

    override fun onReceive(context: Context?, intent: Intent?) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        val today = sdf.format(date)
        val apiService = ApiClient().retrofitClient().create(ApiService::class.java)
        apiService.getMovieRelease(BuildConfig.API_KEY, today, today)
            .enqueue(object : Callback<MovieResponseMdl> {
                override fun onFailure(call: Call<MovieResponseMdl>, t: Throwable) {
                    Log.e("onFailureMovieRelease", t.message!!)
                }

                override fun onResponse(
                    call: Call<MovieResponseMdl>,
                    response: Response<MovieResponseMdl>
                ) {
                    if (response.isSuccessful) {
                        val movies = response.body()?.results!!
                        moviesList.clear()
                        moviesList.addAll(movies)
                        var notifId = 500
                        for (i in movies.indices) {
                            val movie = movies[i]
                            val title = movie.title
                            val message = context?.resources?.getString(
                                R.string.placeholder_notification_message,
                                title
                            )
                            notifId += 1
                            showAlarmNotification(context!!, title, message!!, notifId)
                        }
                    } else {
                        Log.e("onResponse", response.message())
                    }
                }
            })
    }

    fun enableReleaseReminder(context: Context, time: String) {
        disableReminder(context)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiverV2::class.java)
        val timeArray = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calendar.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calendar.set(Calendar.SECOND, 0)

        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )

        Toast.makeText(
            context,
            R.string.message_release_reminder_enable,
            Toast.LENGTH_SHORT
        ).show()
    }

    fun disableReminder(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmReceiverV2::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ID_RELEASE, intent, 0)
        alarmManager.cancel(pendingIntent)
        Toast.makeText(context, R.string.message_release_reminder_disable, Toast.LENGTH_SHORT).show()
    }

    private fun showAlarmNotification(
        context: Context?,
        title: String,
        message: String,
        notifId: Int
    ) {
        val channelId = "Channel_Release"
        val channelName = "Release channel"
        val notificationManagerCompat = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(notifId, notification)
    }

}