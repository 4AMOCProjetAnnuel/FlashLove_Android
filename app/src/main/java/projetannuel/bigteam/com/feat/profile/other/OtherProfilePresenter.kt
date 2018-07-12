package projetannuel.bigteam.com.feat.profile.other

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
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

    private lateinit var flashedUser: FlashLuvUser
    private lateinit var query: Query

    val flashedUserValuesEventListener = object : ChildEventListener {

        override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

        override fun onChildAdded(p0: DataSnapshot?, p1: String?) {}

        override fun onChildRemoved(p0: DataSnapshot?) {}

        override fun onCancelled(p0: DatabaseError?) {}

        override fun onChildChanged(dataSnap: DataSnapshot?, childName: String?) {

            childName?.let {
                if (dataSnap != null && dataSnap.value != null) {
                    queryFlashLuvUser(false, false, false)
                }

            }
        }
    }

    override fun queryFlashLuvUser(incrementViews: Boolean, incrementLikes: Boolean,
            incrementFlirts: Boolean) {

        appFirebaseDatabase.usersReference.child(flashLuvUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {}
                    override fun onDataChange(snap: DataSnapshot?) {
                        snap?.let {
                            flashedUser = it.getValue(FlashLuvUser::class.java)!!

                            flashedUser?.let {


                                view.setFlashLuvUser(it)

                                if (incrementViews) {
                                    it.views++
                                    appFirebaseDatabase.saveFlashLuvUser(it)
                                    queryFlashLuvUser(false, false, false)
                                }

                                if (incrementLikes) {
                                    it.likes++
                                    appFirebaseDatabase.saveFlashLuvUser(it)
                                    queryFlashLuvUser(false, false, false)

                                }

                                if (incrementFlirts) {
                                    it.flirts++
                                    appFirebaseDatabase.saveFlashLuvUser(it)
                                    queryFlashLuvUser(false, false, false)
                                    navigator.displayFlirt(it.uid)
                                }

                                query = appFirebaseDatabase.usersReference.child(it.uid)
                                query.addChildEventListener(flashedUserValuesEventListener)
                            }
                        }
                    }
                })
    }

    override fun goFlirt() {
        navigator.displayFlirt(flashedUser.uid)
    }

    override fun stop() {
        query.removeEventListener(flashedUserValuesEventListener)
        super.stop()
    }


    data class FactoryParameters(val flashLuvUserId: String)
}