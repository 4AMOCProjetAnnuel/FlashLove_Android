package projetannuel.bigteam.com.feat.AppFirebase

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

    var RC_SIGN_IN =  444

}