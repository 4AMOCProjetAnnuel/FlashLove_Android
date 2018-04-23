package projetannuel.bigteam.com.feat.register

import android.content.Intent
import android.util.Log
import com.firebase.ui.auth.AuthUI
import projetannuel.bigteam.com.feat.AppFirebase.FirebaseAuthManager
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * RegisterPresenter -
 * @author guirassy
 * @version $Id$
 */
class RegisterPresenter(view : RegisterContract.View,
        navigator: AppNavigator,
        val authManager: FirebaseAuthManager) :
        AppMvpPresenter<AppNavigator, RegisterContract.View>(view, navigator), RegisterContract.Presenter {

    override fun resume() {
        Log.v("RegisterPresenter : ", " Presenter resume")
    }

    override fun signUpWithProvider() : Intent {

        return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(authManager.authProviders)
                .build()

    }

}