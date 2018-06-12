package projetannuel.bigteam.com.mvp

/**
 * BaseView -
 * @author guirassy
 * @version $Id$
 */
interface BaseView<P : BasePresenter> {

    fun setToolbarText(title: String)
    fun toastMessage(message :String)

}