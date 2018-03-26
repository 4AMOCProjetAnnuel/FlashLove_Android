package projetannuel.bigteam.com.navigation

import android.app.FragmentManager
import projetannuel.bigteam.com.feat.parties.PartiesFragment

/**
 * AppNavigator -
 * @author guirassy
 * @version $Id$
 */
class AppNavigator(private val fragmentManager: FragmentManager,
        private val containerId: Int) : Navigator {

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

}