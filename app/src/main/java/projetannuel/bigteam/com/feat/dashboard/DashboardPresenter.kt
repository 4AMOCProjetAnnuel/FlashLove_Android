package projetannuel.bigteam.com.feat.dashboard

import projetannuel.bigteam.com.mvp.AppMvpPresenter
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * DashboardPresenter -
 * @author guirassy
 * @version $Id$
 */
class DashboardPresenter(view: DashboardContract.View,
        navigator: AppNavigator) :
        AppMvpPresenter<AppNavigator, DashboardContract.View>(view, navigator),
        DashboardContract.Presenter