package projetannuel.bigteam.com.feat.profile.other

import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.BasePresenter
import projetannuel.bigteam.com.mvp.BaseView


/**
 * OtherProfileContract -
 * @author guirassy
 * @version $Id$
 */


interface OtherProfileContract {

    interface View : BaseView<Presenter> {
        fun setFlashLuvUser(flashLuvUser: FlashLuvUser)
        fun notifyFCMError()
        fun notifyFCMSuccess()
    }

    interface Presenter : BasePresenter {
        fun queryFlashLuvUser(incrementViews: Boolean,
                incrementLikes: Boolean,
                incrementFlirts: Boolean)

        fun notifyQuiz(notificationBody : String)
    }
}