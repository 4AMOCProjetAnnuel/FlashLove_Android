package projetannuel.bigteam.com.feat.dashboard

import projetannuel.bigteam.com.mvp.BasePresenter
import projetannuel.bigteam.com.mvp.BaseView

/**
 * DashboardContract -
 * @author guirassy
 * @version $Id$
 */
interface DashboardContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter

}