package projetannuel.bigteam.com.mvp

/**
 * AppMvpPresenter -
 * @author guirassy
 * @version $Id$
 */
abstract class AppMvpPresenter<out N, out V : BaseView<out BasePresenter>>(protected val view: V,
        protected val navigator: N) : BasePresenter {

    public open override fun resume() {
        //noop
    }

    public open override fun pause() {
        //noop
    }

    public open override fun stop() {
        //noop
    }

}