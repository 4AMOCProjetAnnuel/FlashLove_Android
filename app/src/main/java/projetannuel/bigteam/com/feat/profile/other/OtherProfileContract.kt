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

    interface View : BaseView<Presenter>{
        fun setFlashLuvUser(flashLuvUser: FlashLuvUser)
    }

    interface Presenter : BasePresenter {
        fun queryFlashLuvUser()
        fun goToQuiz()
    }
}