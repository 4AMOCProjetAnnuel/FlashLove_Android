package projetannuel.bigteam.com.feat.flirtList

import projetannuel.bigteam.com.feat.flirtList.model.FlirtListViewModel
import projetannuel.bigteam.com.mvp.BasePresenter
import projetannuel.bigteam.com.mvp.BaseView

/**
 * FlirtListContract -
 * @author guirassy
 * @version $Id$
 */

interface FlirtListContract {

    interface View : BaseView<Presenter> {

        fun setFlirtListViewModel(flirtsList: MutableList<FlirtListViewModel>)

    }

    interface Presenter : BasePresenter

}