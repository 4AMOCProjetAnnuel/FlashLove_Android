package projetannuel.bigteam.com

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import projetannuel.bigteam.com.navigation.AppNavigator


class MainActivity : AppCompatActivity() {

    private var appNavigator = AppNavigator(fragmentManager, R.id.main_container_id)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        appNavigator.displayRegistration()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        supportFragmentManager.fragments.forEach {
            it.onActivityResult(requestCode,resultCode,data)
        }
    }
}
