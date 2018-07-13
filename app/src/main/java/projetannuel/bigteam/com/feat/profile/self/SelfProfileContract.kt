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

    interface View : BaseView<Presenter> {
            fun setFlashLuvUSer(flashLuvUser: FlashLuvUser)
            fun notifyFCMError()
            fun notifyFCMSuccess()
    }

    interface Presenter : BasePresenter {
        fun updateFlashLuvUser(description: String, status: Boolean, age : Int)
        fun onScanSuccess(flashLuvUserId: String)
        fun notifyFlashedUser(flashedUserId: String, notificationBody: String)
    }
}