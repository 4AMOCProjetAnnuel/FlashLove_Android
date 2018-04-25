package projetannuel.bigteam.com.feat.profile.update

import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * UpdateProfilePresenter -
 * @author guirassy
 * @version $Id$
 */
class UpdateProfilePresenter(view: UpdateProfileContract.View,
        navigator: AppNavigator) :
        AppMvpPresenter<AppNavigator, UpdateProfileContract.View>(view, navigator),
        UpdateProfileContract.Presenter