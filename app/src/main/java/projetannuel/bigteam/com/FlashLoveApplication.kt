package projetannuel.bigteam.com

import android.app.Application
import com.miguelbcr.ui.rx_paparazzo2.RxPaparazzo
import org.koin.android.ext.android.startKoin
import projetannuel.bigteam.com.koin.appKoinModule

/**
 * FlashLoveApplication -
 * @author guirassy
 * @version $Id$
 */

class FlashLoveApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin(this, listOf(appKoinModule))

        RxPaparazzo
                .register(this)
                .withFileProviderAuthority(packageName + ".provider")
                .withFileProviderPath("medias")
    }

}