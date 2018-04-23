package projetannuel.bigteam.com.kodein

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.factory
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.jxinject.jxInjectorModule
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import kotlinx.android.synthetic.main.activity_main.view.main_container_id
import projetannuel.bigteam.com.MainActivity
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.feat.register.RegisterContract
import projetannuel.bigteam.com.feat.register.RegisterPresenter
import projetannuel.bigteam.com.feat.register.RegistrationFragment
import projetannuel.bigteam.com.navigation.AppNavigator

/**
 * kodeinModule -
 * @author guirassy
 * @version $Id$
 */

val kodeinModule = Kodein.Module {

    import(jxInjectorModule)

    bind<MainActivity>() with provider { (instance<Activity>()) as MainActivity }

    bind<RegisterContract.Presenter>() with provider { (RegisterPresenter(instance(), instance())) as RegisterContract.Presenter}

    bind<RegisterContract.View>() with provider { (RegistrationFragment()) as RegisterContract.View }

    bind<AppNavigator>() with singleton { AppNavigator(instance(), instance("main_container_id")) }

    bind<Int>("main_container_id") with provider { R.id.main_container_id }

}