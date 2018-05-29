package projetannuel.bigteam.com

import android.content.Intent
import android.os.Bundle
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
import kotlinx.android.synthetic.main.toolbar.app_toolbar
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.navigation.AppNavigator


class MainActivity : AppCompatActivity(), AppCompatActivityInjector {

    override val injector = KodeinInjector()

    private var appNavigator = AppNavigator(fragmentManager, R.id.main_container_id)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        setContentView(R.layout.activity_main)

        setSupportActionBar(app_toolbar)

        startNavigation()

    }

    private fun startNavigation() {
        if (FirebaseAuth.getInstance().currentUser == null) {
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener {
                        appNavigator.displayRegistration()
                    }
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
        if(fragmentManager.backStackEntryCount >= 0){
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}
