package projetannuel.bigteam.com.mvp

/**
 * BaseView -
 * @author guirassy
 * @version $Id$
 */
interface BaseView<P : BasePresenter> {

    fun setToolbarText()
    fun toastMessage(message :String)

}