package projetannuel.bigteam.com.mvp

import android.app.Fragment
import android.os.Bundle
import android.support.annotation.CallSuper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.FragmentInjector

/**
 * MvpFragment -
 * @author guirassy
 * @version $Id$
 */
abstract class AbstractMvpFragment<P : BasePresenter>: Fragment(), BaseView<P>, FragmentInjector {

    open val TAG = "MvpFragment"

    abstract val presenter: P

    override val injector = KodeinInjector()

    abstract val defaultLayout: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        retainInstance = true
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(defaultLayout, container, false)


    //Because we want super method call mandatory
    @CallSuper
    override fun onResume() {
        Log.v(TAG, "onResume(): " + this.toString())
        super.onResume()
        presenter.resume()
    }

    @CallSuper
    override fun onPause() {
        Log.v(TAG, "onPause(): " + this.toString())
        presenter.pause()
        super.onPause()
    }

    @CallSuper
    override fun onDestroy() {
        Log.v(TAG, "onDestroy(): " + this.toString())
        super.onDestroy()
        destroyInjector()
    }

    companion object {
        val fragmentTag: String
        get() = this::class.java.simpleName
    }

    /*
    /**
     * This method can be overridden by subclassing Fragment
     * to allow custom back-press event handling
     * inside the Fragment
     */
    open fun onBackPressed(): Boolean {
        return false
    }
    */

}