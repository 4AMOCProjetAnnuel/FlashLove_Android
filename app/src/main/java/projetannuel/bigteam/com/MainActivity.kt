package projetannuel.bigteam.com

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.toolbar.app_toolbar
import projetannuel.bigteam.com.messaging.FlashLuvFirebaseMessagingService
import projetannuel.bigteam.com.navigation.AppNavigator

//ref.unauth()

class MainActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector = KodeinInjector()
    private var appNavigator = AppNavigator(fragmentManager, R.id.main_container_id)
    private lateinit var authStateListener : FirebaseAuth.AuthStateListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        setContentView(R.layout.activity_main)

        setSupportActionBar(app_toolbar)

        createNotificationChannel()

        /*
        authStateListener = FirebaseAuth.AuthStateListener {
            val user = FirebaseAuth.getInstance().currentUser
            if(user == null) {
                appNavigator.displayRegistration()
            } else {
                appNavigator.displayDashboard()
            }
        }
        */
        //FirebaseAuth.getInstance().addAuthStateListener { authStateListener }

        startNavigation()
    }

    private fun startNavigation() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            appNavigator.displayRegistration()
        } else {
            appNavigator.displayDashboard()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        if (fragmentManager.backStackEntryCount >= 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }


    private fun createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelName = getString(R.string.notification_flirt_channel_name)
            val description = getString(R.string.notification_flirt_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.o_nitification_channel), channelName, importance)
            channel.description = description
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)

        }

    }

    override fun onNewIntent(intent: Intent?) {

        intent?.let {

            val flashedUserId  = it
                    .getStringExtra(FlashLuvFirebaseMessagingService.MESSAGING_FLASHED_USER_ID_VALUE_TAG)
            val flashingUserId = it
                    .getStringExtra(FlashLuvFirebaseMessagingService.MESSAGING_FLASHING_USER_ID_VALUE_TAG)
            val notificationType = it
                    .getStringExtra(FlashLuvFirebaseMessagingService.NOTIFICATION_TYPE_TAG)

            if (notificationType == resources.getString(R.string.notification_flash)) {
                appNavigator.displayOtherProfile(flashedUserId, flashingUserId)
            } else {
                appNavigator.displayFlirt(flashedUserId)
            }
        }
    }

    override fun onDestroy() {
        FirebaseAuth.getInstance().removeAuthStateListener { authStateListener }
        super.onDestroy()
    }
}
