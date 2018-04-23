package projetannuel.bigteam.com.navigation

import android.app.Fragment
import android.app.FragmentManager
import projetannuel.bigteam.com.feat.parties.PartiesFragment
import projetannuel.bigteam.com.feat.register.RegistrationFragment

/**
 * AppNavigator -
 * @author guirassy
 * @version $Id$
 */
class AppNavigator(private val fragmentManager: FragmentManager,
        private val containerId: Int) : Navigator {

    override fun displayRegistration() {
        fragmentManager
                .beginTransaction()
                .replace(containerId, RegistrationFragment(), RegistrationFragment.registerFragmentTag)
                .addToBackStack(RegistrationFragment.registerFragmentTag)
                .commit()
    }

    override fun displayParties() {
        fragmentManager
                .beginTransaction()
                .replace(containerId, PartiesFragment(), PartiesFragment.PARTIES_FRAGMENT_TAG)
                .addToBackStack(PartiesFragment.PARTIES_FRAGMENT_TAG)
                .commit()
    }

    override fun displayPartyDetails() {
        //TODO
    }

    data class FactoryParameters( val fragmentManager: FragmentManager,
            val layout : Int)

}