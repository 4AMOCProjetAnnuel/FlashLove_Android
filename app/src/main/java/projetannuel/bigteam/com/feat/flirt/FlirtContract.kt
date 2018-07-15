package projetannuel.bigteam.com.feat.flirt

import projetannuel.bigteam.com.feat.flirt.model.FlirtViewModel
import projetannuel.bigteam.com.model.FlashLuvConversation
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.BasePresenter
import projetannuel.bigteam.com.mvp.BaseView

/**
 * FlirtContract -
 * @author guirassy
 * @version $Id$
 */
interface FlirtContract {

    interface View : BaseView<Presenter> {
        fun setCurrentFlirtViewModel(flirtViewModels: MutableList<FlirtViewModel>)
        fun setFlashedUserInfo(flashedUser: FlashLuvUser)
    }

    interface Presenter: BasePresenter {

        fun updateFlirt(flirtViewModel: FlirtViewModel)


    }

}