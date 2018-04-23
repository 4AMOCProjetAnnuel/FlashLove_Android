package projetannuel.bigteam.com.feat.register

import android.content.Intent
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser
import projetannuel.bigteam.com.AppFirebase.FirebaseAuthManager
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * RegisterPresenter -
 * @author guirassy
 * @version $Id$
 */
class RegisterPresenter(view : RegisterContract.View,
        navigator: AppNavigator,
        private val authManager: FirebaseAuthManager) :
        AppMvpPresenter<AppNavigator, RegisterContract.View>(view, navigator), RegisterContract.Presenter {

    override fun resume() {}

    override fun signUpWithProvider() : Intent {

        return AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setIsSmartLockEnabled(false)
                .setAvailableProviders(authManager.authProviders)
                .build()

    }

    override fun onSignUpWithProviderSucceeded(user: FirebaseUser) {
        authManager.user = user
    }

}