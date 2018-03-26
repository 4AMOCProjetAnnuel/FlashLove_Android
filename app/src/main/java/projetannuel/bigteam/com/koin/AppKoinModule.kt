package projetannuel.bigteam.com.koin

import org.koin.dsl.module.applicationContext
import projetannuel.bigteam.com.navigation.AppNavigator
import projetannuel.bigteam.com.navigation.Navigator

/**
 * AppKoinModule -
 * @author guirassy
 * @version $Id$
 */

class Dummy

val appKoinModule = applicationContext {

    factory { AppNavigator(get(), get()) as Navigator }

}