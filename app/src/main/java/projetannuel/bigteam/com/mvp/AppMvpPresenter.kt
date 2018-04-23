package projetannuel.bigteam.com.mvp

/**
 * AppMvpPresenter -
 * @author guirassy
 * @version $Id$
 */
abstract class AppMvpPresenter<out N, out V : BaseView<out BasePresenter>>(protected val view: V,
        protected val navigator: N) : BasePresenter