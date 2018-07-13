package projetannuel.bigteam.com.feat.profile.self

import android.util.Log
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
        private val fcmServiceInterface: FCMServiceInterface) :
        AppMvpPresenter<AppNavigator, SelfProfileContract.View>(view, navigator),
        SelfProfileContract.Presenter {

    override fun updateFlashLuvUser(flashLuvUser: FlashLuvUser) {
        appFirebaseDatabase.saveFlashLuvUser(flashLuvUser)
    }

    override fun onScanSuccess(flashLuvUserId: String) {
        navigator.displayOtherProfile(flashLuvUserId)
    }

    override fun notifyFlash() {
        val appFCMRequestModel = AppFCMRequestModel(
                to = "df8nusHKk3Q:APA91bF90583wyBrfgbXdsWBPeFrlgatVe4s7waNBK6sjdwf4nyBVUZWGKjHe8uhtJrB6szofSHFQWUgWCwF_Mhf1vXhtnwuccNZEecElB2umxve_SXLrvfMgcilwiulEzUeyP5hXJtmKd7jsypNAGHhVWHyknXa3A",
                data = AppFCMDataModel("dcyNGgJ1NJTjIvsHwn1oIeBL2bD3")
        )

        launchNotification()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    Log.v("@@NotifTest", "$it")
                },{
                    Log.v("@@NotifErr", "${it.printStackTrace()}")
                    Log.v("@@NotifErr", it.localizedMessage)
                    Log.v("@@NotifErr", "${it.cause}")
                })

    }

    private fun launchNotification() : Observable<Any> {

        val appFCMRequestModel = AppFCMRequestModel(
                to = "cSWsf5Fv8ig:APA91bGYjsM4mNcV6JSOu_pA6kFOZm5EhZhjVh-G-e9-mDg0rii_OabIxIXmEJtuWWFRu4md0iFReIA2t7AdDMnCRQsZLw4-2D6UVnk5oucJF6JMgGBlWnTu_nv3u9ErEnbAa92iWlVyZcvcNWFxUelYU3r7ZLf6pA",
                data = AppFCMDataModel("dcyNGgJ1NJTjIvsHwn1oIeBL2bD3")
        )
        return fcmServiceInterface.postNotification(appFCMRequestModel)
    }

}