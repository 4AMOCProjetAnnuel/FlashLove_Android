package projetannuel.bigteam.com.feat.profile.self

import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.BasePresenter
import projetannuel.bigteam.com.mvp.BaseView

/**
 * SelfProfileContract -
 * @author guirassy
 * @version $Id$
 */
interface SelfProfileContract {

    interface View : BaseView<Presenter>

    interface Presenter : BasePresenter {
        fun updateFlashLuvUser(flashLuvUser: FlashLuvUser)
        fun onScanSuccess(flashLuvUserId: String)
        fun notifyFlash()
    }

}