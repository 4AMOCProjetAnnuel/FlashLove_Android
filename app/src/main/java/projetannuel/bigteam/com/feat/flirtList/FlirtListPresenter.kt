package projetannuel.bigteam.com.feat.flirtList

import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.feat.flirtList.model.FlirtListViewModel
import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * FlirtListPresenter -
 * @author guirassy
 * @version $Id$
 */


class FlirtListPresenter(view: FlirtListContract.View,
        navigator: AppNavigator,
        private val appFirebaseDatabase: AppFirebaseDatabase) :
        AppMvpPresenter<AppNavigator, FlirtListContract.View>(view, navigator), FlirtListContract.Presenter {

    private lateinit var flirtsList : MutableList<FlirtListViewModel>

    override fun resume() {}

}