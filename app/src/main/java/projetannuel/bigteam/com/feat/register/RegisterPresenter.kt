package projetannuel.bigteam.com.feat.register

import android.util.Log
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * RegisterPresenter -
 * @author guirassy
 * @version $Id$
 */
class RegisterPresenter(view : RegisterContract.View,
        navigator: AppNavigator) :
        AppMvpPresenter<AppNavigator, RegisterContract.View>(view, navigator), RegisterContract.Presenter {

    override fun resume() {
        Log.v("RegisterPresenter : ", " Presenter resume")
    }

    override fun pause() {

        Log.v("RegisterPresenter : ", " Presenter pause")

    }

    override fun stop() {

        Log.v("RegisterPresenter : ", " Presenter stop")
    }

}