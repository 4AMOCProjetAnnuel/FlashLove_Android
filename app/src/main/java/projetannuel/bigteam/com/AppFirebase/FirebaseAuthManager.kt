package projetannuel.bigteam.com.AppFirebase

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser

/**
 * FirebaseAuthManager -
 * @author guirassy
 * @version $Id$
 */
class FirebaseAuthManager {

     var authProviders  = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build())

    var user: FirebaseUser? = null

    fun setFirebaseUser(user: FirebaseUser) {
        this.user = user
    }

}