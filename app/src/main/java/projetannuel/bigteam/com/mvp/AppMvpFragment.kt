package projetannuel.bigteam.com.mvp

import android.widget.Toast

/**
 * AppMvpFragment -
 * @author guirassy
 * @version $Id$
 */
abstract class AppMvpFragment<P : BasePresenter> : AbstractMvpFragment<P>(), BaseView<P> {

    final override fun setToolbarText() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}
