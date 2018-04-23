package projetannuel.bigteam.com

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import projetannuel.bigteam.com.kodein.kodeinModule

/**
 * FlashLoveApplication -
 * @author guirassy
 * @version $Id$
 */

class FlashLoveApplication : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<Application>() with instance(this@FlashLoveApplication)
        import(kodeinModule)

    }

    override fun onCreate() {
        super.onCreate()

       // startKoin(this, listOf(appKoinModule))

        RxPaparazzo
                .register(this)
                .withFileProviderAuthority("$packageName.provider")
                .withFileProviderPath("medias")
    }

}