package projetannuel.bigteam.com.feat.register

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.appFirebase.FirebaseAuthManager
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * RegisterPresenter -
 * @author guirassy
 * @version $Id$
 */
class RegisterPresenter(view : RegisterContract.View,
        navigator: AppNavigator,
        private val authManager: FirebaseAuthManager,
        private val appFirebaseDatabase: AppFirebaseDatabase) :
        AppMvpPresenter<AppNavigator, RegisterContract.View>(view, navigator), RegisterContract.Presenter {

    override fun resume() {}

    override fun signUpWithProvider() : Intent {

        return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(authManager.authProviders)
                .setLogo(R.drawable.flashluv_ic_launcher)
                .setTheme(R.style.AppTheme)
                .build()

    }

    override fun onSignUpWithProviderSucceeded(user: FirebaseUser) {

        val flashLuvUser  = FlashLuvUser(displayName = user.displayName!!,
                email = user.email!!,
                photoUrl = user.photoUrl!!.toString(),
                uid = user.uid)

        appFirebaseDatabase.saveFlashLuvUser(flashLuvUser)

        navigator.displayUpdateProfile()
    }

}