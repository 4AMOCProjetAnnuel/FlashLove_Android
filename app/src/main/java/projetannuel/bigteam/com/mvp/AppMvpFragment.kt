package projetannuel.bigteam.com.mvp

import com.github.salomonbrys.kodein.KodeinInjector

/**
 * AppMvpFragment -
 * @author guirassy
 * @version $Id$
 */
abstract class AppMvpFragment<P : BasePresenter> : AbstractMvpFragment<P>(), BaseView<P> {

    final override fun setToolbarText() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
