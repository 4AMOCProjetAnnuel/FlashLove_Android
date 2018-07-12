package projetannuel.bigteam.com.feat.profile.self

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * SelfProfilePresenter -
 * @author guirassy
 * @version $Id$
 */

class SelfProfilePresenter(view: SelfProfileContract.View,
        navigator: AppNavigator,
        private val appFirebaseDatabase: AppFirebaseDatabase) :
        AppMvpPresenter<AppNavigator, SelfProfileContract.View>(view, navigator),
        SelfProfileContract.Presenter {

    override fun updateFlashLuvUser(flashLuvUser: FlashLuvUser) {
        appFirebaseDatabase.saveFlashLuvUser(flashLuvUser)
    }

    override fun onScanSuccess(flashLuvUserId: String) {
        navigator.displayOtherProfile(flashLuvUserId)
    }
}