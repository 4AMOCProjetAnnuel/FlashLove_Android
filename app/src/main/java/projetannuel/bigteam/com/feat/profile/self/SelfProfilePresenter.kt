package projetannuel.bigteam.com.feat.profile.self

import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
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
 * SelfProfilePresenter -
 * @author guirassy
 * @version $Id$
 */

class SelfProfilePresenter(view: SelfProfileContract.View,
        navigator: AppNavigator,
        private val appFirebaseDatabase: AppFirebaseDatabase,
        private val fcmServiceInterface: FCMServiceInterface,
        private val flashLuvCurrentUserId: String) :
        AppMvpPresenter<AppNavigator, SelfProfileContract.View>(view, navigator),
        SelfProfileContract.Presenter {

    lateinit var flashLuvUser: FlashLuvUser
    var disposableBag =  CompositeDisposable()

    override fun resume() {

        appFirebaseDatabase.usersReference.child(flashLuvCurrentUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError?) {}
                    override fun onDataChange(snap: DataSnapshot?) {
                        if (snap != null) {

                            flashLuvUser = snap.getValue(FlashLuvUser::class.java)!!

                            view.setFlashLuvUSer(flashLuvUser)

                            val query = appFirebaseDatabase.usersReference.child(flashLuvUser!!.uid)
                            query.addChildEventListener(userValuesEventListener)

                        }
                    }
                })
    }

    override fun updateFlashLuvUser(description: String, status: Boolean, age : Int) {

        flashLuvUser.description = description
        flashLuvUser.single = status
        flashLuvUser.age = age

        appFirebaseDatabase.saveFlashLuvUser(flashLuvUser)
    }

    override fun onScanSuccess(flashedUserId: String) {
        navigator.displayOtherProfile(flashedUserId, flashLuvCurrentUserId)
    }


    override fun notifyFlashedUser(flashedUserId: String, notificationBody: String) {

        appFirebaseDatabase.usersReference.child(flashedUserId)
                .addListenerForSingleValueEvent(object : ValueEventListener {
                    override fun onCancelled(p0: DatabaseError?) {}
                    override fun onDataChange(snap: DataSnapshot?) {
                        if (snap != null) {

                            val flashedUser = snap.getValue(FlashLuvUser::class.java)!!

                            val appFCMRequestModel = AppFCMRequestModel(
                                    to = flashedUser.fcmToken,
                                    notification = AppFCMNotificationModel(BuildConfig.NotificationFlashAlert,
                                            "${flashedUser.displayName} ".plus(notificationBody)),
                                    data = AppFCMDataModel(flashLuvUser.uid, ""))

                            val notificationObservable = fcmServiceInterface
                                    .postNotification(appFCMRequestModel)
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe({
                                        navigator.displayOtherProfile(flashedUser.uid, flashLuvCurrentUserId)
                                    }, {
                                            view.notifyFCMError()
                                    })

                            disposableBag.add(notificationObservable)
                        }
                    }
                })
    }

    override fun pause() {
        disposableBag.clear()
        super.pause()
    }

    val userValuesEventListener = object : ChildEventListener {

        override fun onCancelled(p0: DatabaseError?) {}

        override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

        override fun onChildChanged(data: DataSnapshot?, childName: String?) {

            data?.let {
                resume()
            }
        }

        override fun onChildAdded(p0: DataSnapshot?, p1: String?) {}

        override fun onChildRemoved(p0: DataSnapshot?) {}
    }


    data class FactoryParameters(val flashedUserId: String)

}