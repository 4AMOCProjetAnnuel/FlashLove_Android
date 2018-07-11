package projetannuel.bigteam.com.messaging

import android.util.Log
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * FlashLuvFirebaseInstanceIdService -
 * @author guirassy
 * @version $Id$
 */
class FlashLuvFirebaseInstanceIdService :  FirebaseInstanceIdService() {

    override fun onTokenRefresh() {
        val refreshedToken = FirebaseInstanceId.getInstance().token
        Log.d("FCM token", "Refreshed token: $refreshedToken")
        
    }
}