package projetannuel.bigteam.com.feat.register

import android.content.Intent
import com.google.firebase.auth.FirebaseUser
import projetannuel.bigteam.com.mvp.AppView
import projetannuel.bigteam.com.mvp.BasePresenter

/**
 * RegisterContract -
 * @author guirassy
 * @version $Id$
 */
interface RegisterContract {

    interface View : AppView<Presenter>  {}

    interface Presenter : BasePresenter {
        fun signUpWithProvider() : Intent
        fun onSignUpWithProviderSucceeded(user : FirebaseUser)
    }

}