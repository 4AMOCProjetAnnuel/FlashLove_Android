package projetannuel.bigteam.com

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.gson.Gson
import kotlinx.android.synthetic.main.toolbar.app_toolbar
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.messaging.FlashLuvFirebaseMessagingService
import projetannuel.bigteam.com.messaging.model.AppFCMDataModel
import projetannuel.bigteam.com.messaging.model.AppFCMNotificationModel
import projetannuel.bigteam.com.messaging.model.AppFCMRequestModel
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.navigation.AppNavigator
import projetannuel.bigteam.com.network.FCMServiceInterface

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

        val appFCMRequestModel = AppFCMRequestModel(
                to = "cSWsf5Fv8ig:APA91bGYjsM4mNcV6JSOu_pA6kFOZm5EhZhjVh-G-e9-mDg0rii_OabIxIXmEJtuWWFRu4md0iFReIA2t7AdDMnCRQsZLw4-2D6UVnk5oucJF6JMgGBlWnTu_nv3u9ErEnbAa92iWlVyZcvcNWFxUelYU3r7ZLf6pA",
                data = AppFCMDataModel("dcyNGgJ1NJTjIvsHwn1oIeBL2bD3")
        )

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

            val channelName = "FlirtChannel"
            val description = "First channel for testing purposes"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(getString(R.string.o_nitification_channel), channelName, importance)
            channel.description = description
            val notifManager = getSystemService(NotificationManager::class.java)
            notifManager.createNotificationChannel(channel)

        }

    }

    override fun onNewIntent(intent: Intent?) {

        intent?.let {

            val flashingUserId  = it.getStringExtra(FlashLuvFirebaseMessagingService.NOTIFICATION_PENDING_INTENT_TAG)
            val notificationType = it.getStringExtra(FlashLuvFirebaseMessagingService.NOTIFICATION_TYPE_TAG)

            if (notificationType == resources.getString(R.string.notification_flash)) {
                appNavigator.displayOtherProfile(flashingUserId)
            } else {
                appNavigator.displayFlirt(flashingUserId)
            }
        }
    }

    override fun onDestroy() {
        FirebaseAuth.getInstance().removeAuthStateListener { authStateListener }
        super.onDestroy()
    }
}
