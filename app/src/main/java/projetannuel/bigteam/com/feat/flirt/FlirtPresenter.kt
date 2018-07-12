package projetannuel.bigteam.com.feat.flirt

import android.util.Log
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * FlirtPresenter -
 * @author guirassy
 * @version $Id$
 */

class FlirtPresenter(view: FlirtContract.View,
        navigator: AppNavigator,
        private val requestedUserId : String) :
        AppMvpPresenter<AppNavigator, FlirtContract.View>(view, navigator),
        FlirtContract.Presenter {

    override fun queryRequestingUser() {

        Log.v("@Quiz Id", requestedUserId)



    }


    data class FactoryParameters(val requestedUserId: String)
}