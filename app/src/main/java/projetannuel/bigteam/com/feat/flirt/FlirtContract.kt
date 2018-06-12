package projetannuel.bigteam.com.feat.flirt

import projetannuel.bigteam.com.mvp.BasePresenter
import projetannuel.bigteam.com.mvp.BaseView

/**
 * FlirtContract -
 * @author guirassy
 * @version $Id$
 */
interface FlirtContract {

    interface View : BaseView<Presenter> {}


    interface Presenter: BasePresenter
}