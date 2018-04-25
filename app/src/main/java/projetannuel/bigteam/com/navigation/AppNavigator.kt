package projetannuel.bigteam.com.navigation

import android.app.FragmentManager
import projetannuel.bigteam.com.feat.dashboard.DashboardFragment
import projetannuel.bigteam.com.feat.parties.PartiesFragment
import projetannuel.bigteam.com.feat.profile.update.UpdateProfileFragment
import projetannuel.bigteam.com.feat.register.RegisterFragment

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
                .replace(containerId, RegisterFragment(), RegisterFragment.registerFragmentTag)
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

    override fun displayUpdateProfile() {
        fragmentManager
                .beginTransaction()
                .replace(containerId, UpdateProfileFragment(), UpdateProfileFragment.fragmentTag)
                .addToBackStack(UpdateProfileFragment.fragmentTag)
                .commit()
    }

    override fun displayDashboard() {
        fragmentManager
                .beginTransaction()
                .replace(containerId, DashboardFragment(), DashboardFragment.fragmentTag)
                .commit()
    }

}