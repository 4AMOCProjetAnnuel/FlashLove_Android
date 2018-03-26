package projetannuel.bigteam.com

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
}
