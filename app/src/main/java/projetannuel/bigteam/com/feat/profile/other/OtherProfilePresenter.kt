package projetannuel.bigteam.com.feat.profile.other

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * OtherProfilePresenter -
 * @author guirassy
 * @version $Id$
 */

class OtherProfilePresenter(view: OtherProfileContract.View,
        navigator: AppNavigator,
        private val flashLuvUserId: String,
        private val appFirebaseDatabase: AppFirebaseDatabase) :
        AppMvpPresenter<AppNavigator, OtherProfileContract.View>(view, navigator),
        OtherProfileContract.Presenter {

    lateinit var flashedUser: FlashLuvUser

    override fun resume() {}

    override fun queryFlashLuvUser() {
        appFirebaseDatabase.usersReference.child(flashLuvUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(error: DatabaseError?) {}
                    override fun onDataChange(snap: DataSnapshot?) {
                        snap?.let {
                            val flashedUser = it.getValue(FlashLuvUser::class.java)
                            view.setFlashLuvUser(flashedUser!!)
                        }
                    }
                })
    }

    override fun goFlirt() {
        navigator.displayFlirt(flashedUser.uid)
    }

    data class FactoryParameters(val flashLuvUserId: String)
}