package projetannuel.bigteam.com.mvp

import android.support.v7.app.AppCompatActivity
import android.widget.Toast

/**
 * AppMvpFragment -
 * @author guirassy
 * @version $Id$
 */
abstract class AppMvpFragment<P : BasePresenter> : MvpKodeinFragment<P>(), BaseView<P> {

    final override fun setToolbarText(title: String) {
    }

    override fun toastMessage(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

}
