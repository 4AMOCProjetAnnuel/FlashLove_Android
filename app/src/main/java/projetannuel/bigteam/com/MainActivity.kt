package projetannuel.bigteam.com

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.navigation.AppNavigator


class MainActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector = KodeinInjector()

    private var appNavigator = AppNavigator(fragmentManager, R.id.main_container_id)

    private var firebaseDatabase = AppFirebaseDatabase()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        setContentView(R.layout.activity_main)

        initiateNavigation()

    }

    private fun initiateNavigation() {
        val firebaseUser = FirebaseAuth.getInstance().currentUser

        if (firebaseUser == null) {
            //Go to Sign in
            appNavigator.displayRegistration()
        } else {

            firebaseDatabase.usersReference.child(firebaseUser.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError?) {}

                        override fun onDataChange(snap: DataSnapshot?) {

                            if (snap != null) {

                                val flashLuvUser = snap.getValue(FlashLuvUser::class.java)

                                if (flashLuvUser != null) {
                                    if (flashLuvUser.profileCompleted) {
                                        appNavigator.displayDashboard()
                                    } else {
                                        appNavigator.displayUpdateProfile()
                                    }
                                }
                            }
                        }

                    })
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments.forEach {
            it.onActivityResult(requestCode, resultCode, data)
        }
    }
}
