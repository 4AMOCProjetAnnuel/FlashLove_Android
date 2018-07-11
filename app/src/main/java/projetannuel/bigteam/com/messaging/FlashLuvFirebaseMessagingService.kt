package projetannuel.bigteam.com.messaging

import android.app.PendingIntent
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import projetannuel.bigteam.com.MainActivity
import projetannuel.bigteam.com.R

/**
 * FlashLuvFirebaseMessagingService -
 * @author guirassy
 * @version $Id$
 */
class FlashLuvFirebaseMessagingService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // super.onMessageReceived(msg)

        remoteMessage?.let {

            // TODO(developer): Handle FCM messages here.
            // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
            Log.d("@@TEST", "From: " + remoteMessage?.getFrom());

            // Check if message contains a data payload.
            if (it.data.isNotEmpty()) {
                Log.d("@@TEST", "Message data payload: " + it.data)
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

                sendNotification(title, message)

            }
            // Also if you intend on generating your own notifications as a result of a received FCM
            // message, here is where that should be initiated. See sendNotification method below.
        }

    }

    private fun sendNotification(title: String, message: String) {


        var i = Intent(this, MainActivity::class.java)
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        i.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
        i.putExtra("flashingUserId", "RWVkt3kbU4UKaovuNLw90lSUgBx2")
        var  pendingIntent = PendingIntent.getActivity (this, 0, i, PendingIntent.FLAG_UPDATE_CURRENT)

        var notificationManager = NotificationManagerCompat.from(this)

        val notifBuilder = NotificationCompat.Builder(this, getString(R.string.o_nitification_channel))
                .setAutoCancel(true)
                .setDefaults(android.app.Notification.DEFAULT_ALL)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(message)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setColor(ContextCompat.getColor(this, R.color.primary_pink_light))
                .setContentIntent(pendingIntent)

        //TODO generate random ids
        notificationManager.notify(84394454, notifBuilder.build())
    }

}