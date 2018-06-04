package projetannuel.bigteam.com.mvp

import android.os.Bundle
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.FragmentInjector

/**
 * MvpKodeinFragment -
 * @author guirassy
 * @version $Id$
 */

abstract class MvpKodeinFragment<P: BasePresenter>: AbstractMvpFragment<P>(),
BaseView<P>, FragmentInjector {

    override val injector =  KodeinInjector()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
    }

    override fun onDestroy() {
        destroyInjector()
        super.onDestroy()
    }
}