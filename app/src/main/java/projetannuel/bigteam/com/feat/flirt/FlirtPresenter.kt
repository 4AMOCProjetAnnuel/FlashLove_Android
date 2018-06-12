package projetannuel.bigteam.com.feat.flirt

import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * FlirtPresenter -
 * @author guirassy
 * @version $Id$
 */

class FlirtPresenter(view: FlirtContract.View,
        navigator: AppNavigator) :
        AppMvpPresenter<AppNavigator, FlirtContract.View>(view, navigator),
        FlirtContract.Presenter