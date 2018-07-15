package projetannuel.bigteam.com.feat.profile.other

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import projetannuel.bigteam.com.BuildConfig
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.messaging.model.AppFCMDataModel
import projetannuel.bigteam.com.messaging.model.AppFCMNotificationModel
import projetannuel.bigteam.com.messaging.model.AppFCMRequestModel
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator
import projetannuel.bigteam.com.network.FCMServiceInterface

/**
 * OtherProfilePresenter -
 * @author guirassy
 * @version $Id$
 */

class OtherProfilePresenter(view: OtherProfileContract.View,
        navigator: AppNavigator,
        private val appFirebaseDatabase: AppFirebaseDatabase,
        private val fcmServiceInterface: FCMServiceInterface,
        private val flashedUserId: String,
        private val flashingUserId: String) :
        AppMvpPresenter<AppNavigator, OtherProfileContract.View>(view, navigator),
        OtherProfileContract.Presenter {

    private lateinit var flashingUser: FlashLuvUser
    private lateinit var flashedUser: FlashLuvUser
    private lateinit var query: Query
    private var disposableBag = CompositeDisposable()

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

    override fun resume() {

        appFirebaseDatabase.usersReference.child(flashingUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {}
                    override fun onDataChange(snap: DataSnapshot?) {
                        snap?.let {
                            it.getValue(FlashLuvUser::class.java)?.let {
                                flashingUser = it
                                Log.v("flashinUser", flashingUser.displayName)
                            }
                        }
                    }
                })
    }

    override fun queryFlashLuvUser(incrementViews: Boolean, incrementLikes: Boolean,
            incrementFlirts: Boolean) {

        appFirebaseDatabase.usersReference.child(flashedUserId)
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
                                    navigator.displayFlirt(it.uid, flashingUser.uid)
                                }

                                query = appFirebaseDatabase.usersReference.child(it.uid)
                                query.addChildEventListener(flashedUserValuesEventListener)
                            }

                        }
                    }
                })
    }

    override fun notifyQuiz(notificationBody : String) {

        val appFCMRequestModel = AppFCMRequestModel(
                to = flashedUser.fcmToken,
                notification = AppFCMNotificationModel(BuildConfig.NotificationQuizAlert,
                        "${flashingUser.displayName} ".plus(notificationBody)),
                data = AppFCMDataModel(flashedUser.uid, flashingUser.uid))

        val notificationObservable = fcmServiceInterface
                .postNotification(appFCMRequestModel)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.notifyFCMSuccess()
                }, {
                    view.notifyFCMError()
                })

        disposableBag.add(notificationObservable)
    }

    override fun stop() {
        query.removeEventListener(flashedUserValuesEventListener)
        disposableBag.clear()
        super.stop()
    }

    data class FactoryParameters(val flashedUserId: String, val flashingUserId: String)
}