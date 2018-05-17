package projetannuel.bigteam.com.kodein

import android.app.Activity
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.jxinject.jxInjectorModule
import com.github.salomonbrys.kodein.provider
import com.github.salomonbrys.kodein.singleton
import projetannuel.bigteam.com.MainActivity
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.appFirebase.FirebaseAuthManager
import projetannuel.bigteam.com.feat.dashboard.DashboardContract
import projetannuel.bigteam.com.feat.dashboard.DashboardFragment
import projetannuel.bigteam.com.feat.dashboard.DashboardPresenter
import projetannuel.bigteam.com.feat.profile.update.UpdateProfileContract
import projetannuel.bigteam.com.feat.profile.update.UpdateProfileFragment
import projetannuel.bigteam.com.feat.profile.update.UpdateProfilePresenter
import projetannuel.bigteam.com.feat.register.RegisterContract
import projetannuel.bigteam.com.feat.register.RegisterPresenter
import projetannuel.bigteam.com.feat.register.RegisterFragment
import projetannuel.bigteam.com.navigation.AppNavigator
import projetannuel.bigteam.com.view.adapter.DashboardPagerAdapter

/**
 * kodeinModule -
 * @author guirassy
 * @version $Id$
 */

val kodeinModule = Kodein.Module {

    import(jxInjectorModule)

    bind<MainActivity>() with provider { (instance<Activity>()) as MainActivity }

    bind<RegisterContract.Presenter>() with provider { (RegisterPresenter(instance(), instance(), instance(), instance())) as RegisterContract.Presenter}

    bind<RegisterContract.View>() with provider { (RegisterFragment()) as RegisterContract.View }

    bind<AppNavigator>() with singleton { AppNavigator(instance(), instance("main_container_id")) }

    bind<Int>("main_container_id") with provider { R.id.main_container_id }

    bind<FirebaseAuthManager>() with singleton { FirebaseAuthManager() }

    bind<AppFirebaseDatabase>() with singleton { AppFirebaseDatabase() }

    bind<UpdateProfileContract.Presenter>() with provider { (UpdateProfilePresenter(instance(), instance())) as UpdateProfileContract.Presenter}

    bind<UpdateProfileContract.View>() with provider { (UpdateProfileFragment()) as UpdateProfileContract.View }

    bind<DashboardContract.Presenter>() with provider { (DashboardPresenter(instance(), instance())) as DashboardContract.Presenter}

    bind<DashboardContract.View>() with provider { (DashboardFragment()) as DashboardContract.View }

    bind<DashboardPagerAdapter>() with provider { DashboardPagerAdapter(instance()) }

}