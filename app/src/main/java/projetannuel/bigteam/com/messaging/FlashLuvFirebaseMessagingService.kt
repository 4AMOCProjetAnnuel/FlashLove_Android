package projetannuel.bigteam.com.messaging

import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import projetannuel.bigteam.com.MainActivity
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.model.FlashLuvUser
import java.util.Random

/**
 * FlashLuvFirebaseMessagingService -
 * @author guirassy
 * @version $Id$
 */
class FlashLuvFirebaseMessagingService : FirebaseMessagingService() {

    companion object {

        const val NOTIFICATION_PENDING_INTENT_TAG = "userId"
        const val NOTIFICATION_TYPE_TAG = "notificationType"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // super.onMessageReceived(msg)

        remoteMessage?.let {

            var userId = ""

            // Check if message contains a data payload.
            if (it.data.isNotEmpty()) {

                Log.d("@@TEST", "Message data payload: " + it.data)
            }

            it.data?.let {
                it["userId"]?.let {
                    userId = it
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

            sendNotification(userId, title, message)

        }

    }

    private fun sendNotification(userId: String, title: String, message: String) {

        val toMainActivity = Intent(this, MainActivity::class.java)
        toMainActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        toMainActivity.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        toMainActivity.putExtra(FlashLuvFirebaseMessagingService.NOTIFICATION_PENDING_INTENT_TAG, userId)

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