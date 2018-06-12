package projetannuel.bigteam.com.appFirebase

import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseUser

/**
 * FirebaseAuthManager -
 * @author guirassy
 * @version $Id$
 */
class FirebaseAuthManager {

    var authProviders = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.FacebookBuilder().build(),
            AuthUI.IdpConfig.TwitterBuilder().build(),
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build())

    var user: FirebaseUser? = null

}