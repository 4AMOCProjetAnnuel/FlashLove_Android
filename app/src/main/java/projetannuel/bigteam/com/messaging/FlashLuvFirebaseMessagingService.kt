package projetannuel.bigteam.com.messaging

import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import projetannuel.bigteam.com.MainActivity
import projetannuel.bigteam.com.R
import java.util.Random

/**
 * FlashLuvFirebaseMessagingService -
 * @author guirassy
 * @version $Id$
 */
class FlashLuvFirebaseMessagingService : FirebaseMessagingService() {

    companion object {

        const val MESSAGING_FLASHED_USER_ID_VALUE_TAG = "flashedUserId"
        const val MESSAGING_FLASHING_USER_ID_VALUE_TAG = "flashingUserId"
        const val NOTIFICATION_TYPE_TAG = "notificationType"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // super.onMessageReceived(msg)

        remoteMessage?.let {

            var flashedUserId = ""
            var flashingUserId = ""

            // Check if message contains a data payload.
            if (it.data.isNotEmpty()) {
                Log.d("@@TEST", "Message data payload: " + it.data)
            }

            it.data?.let {
                it["flashedUserId"]?.let {
                    flashedUserId = it
                }

                it["flashingUserId"]?.let {
                    flashingUserId = it
                }
            }

            var title = ""
            var message = ""

            // Check if message contains a notification payload.
            if (it.notification != null) {
                Log.d("@@TEST", "Message Notification Body: " + it.notification?.body)

                it.notification?.title?.let {
                    title = it
                }

                it.notification?.body?.let {
                    message = it
                }
            }

            sendNotification(flashedUserId, flashingUserId, title, message)

        }

    }

    private fun sendNotification(flashedUserId: String, flashingUserId: String, title: String, message: String) {

        val toMainActivity = Intent(this, MainActivity::class.java)
        toMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        toMainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        toMainActivity.putExtra(FlashLuvFirebaseMessagingService.MESSAGING_FLASHED_USER_ID_VALUE_TAG, flashedUserId)
        toMainActivity.putExtra(FlashLuvFirebaseMessagingService.MESSAGING_FLASHING_USER_ID_VALUE_TAG, flashingUserId)

        if(title == resources.getString(R.string.notification_flash_title)) {
            toMainActivity.putExtra(FlashLuvFirebaseMessagingService.NOTIFICATION_TYPE_TAG, resources.getString(R.string.notification_flash))
        }else {
            toMainActivity.putExtra(FlashLuvFirebaseMessagingService.NOTIFICATION_TYPE_TAG, resources.getString(R.string.notification_quiz))
        }

        val pendingIntent = PendingIntent.getActivity(this, 0, toMainActivity, PendingIntent.FLAG_UPDATE_CURRENT)

        val notificationManager = NotificationManagerCompat.from(this)

        val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.o_nitification_channel))
                .setAutoCancel(true)
                .setDefaults(android.app.Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_filled_favorite)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(ContextCompat.getColor(this, R.color.primary_pink_light))
                .setContentIntent(pendingIntent)

        notificationManager.notify(Random().nextInt(84394454), notificationBuilder.build())
    }

}