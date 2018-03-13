package projetannuel.bigteam.com

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware

/**
 * FlashLoveApplication -
 * @author guirassy
 * @version $Id$
 */


class FlashLoveApplication : Application(), KodeinAware {

    override val kodein: Kodein
        get() = TODO("not implemented") //To change initializer of created properties use File | Settings | File Templates.

}