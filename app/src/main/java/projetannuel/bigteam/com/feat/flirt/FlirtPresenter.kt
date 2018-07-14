package projetannuel.bigteam.com.feat.flirt

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * FlirtPresenter -
 * @author guirassy
 * @version $Id$
 */

class FlirtPresenter(view: FlirtContract.View,
        navigator: AppNavigator,
        private val appFirebaseDatabase: AppFirebaseDatabase,
        private val flashedUserId : String,
        private val flashingUserId : String) :
        AppMvpPresenter<AppNavigator, FlirtContract.View>(view, navigator),
        FlirtContract.Presenter {


    override fun resume() {

        appFirebaseDatabase.usersReference.child(flashedUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener{
                    override fun onCancelled(p0: DatabaseError?) {}

                    override fun onDataChange(snap: DataSnapshot?) {
                        snap?.let {
                            it.getValue(FlashLuvUser::class.java)?.let {
                                Log.v("@Flashed User", it.displayName)
                                view.setFlashedUserQuiz(it)
                            }
                        }
                    }

                })


    }

    override fun queryFlashedUser() {}


    data class FactoryParameters(val flashedUserId: String, val flashingUserId: String)

}