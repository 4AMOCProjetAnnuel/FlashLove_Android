package projetannuel.bigteam.com.feat.profile.other

import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * OtherProfilePresenter -
 * @author guirassy
 * @version $Id$
 */

class OtherProfilePresenter(view: OtherProfileContract.View,
        navigator: AppNavigator) :
        AppMvpPresenter<AppNavigator, OtherProfileContract.View>(view, navigator),
        OtherProfileContract.Presenter {

}