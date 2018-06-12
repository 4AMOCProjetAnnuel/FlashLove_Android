package projetannuel.bigteam.com.navigation

import android.app.FragmentManager
import projetannuel.bigteam.com.feat.dashboard.DashboardFragment
import projetannuel.bigteam.com.feat.flirt.FlirtFragment
import projetannuel.bigteam.com.feat.parties.PartiesFragment
import projetannuel.bigteam.com.feat.profile.other.OtherProfileFragment
import projetannuel.bigteam.com.feat.profile.self.SelfProfileFragment
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

    override fun displaySelfProfile() {
        fragmentManager
                .beginTransaction()
                .replace(containerId, SelfProfileFragment(), SelfProfileFragment.fragmentTag)
                .addToBackStack(SelfProfileFragment.fragmentTag)
                .commit()
    }

    override fun displayDashboard() {

        var dashboard = fragmentManager.findFragmentByTag(DashboardFragment.fragmentTag)
        if(dashboard == null) {
            dashboard = DashboardFragment()
        }

        fragmentManager
                .beginTransaction()
                .replace(containerId, dashboard, DashboardFragment.fragmentTag)
                .addToBackStack(DashboardFragment.fragmentTag)
                .commit()
    }

    override fun displayOtherProfile(flashLuvUserId: String) {
        var otherProfileFragment = fragmentManager.findFragmentByTag(OtherProfileFragment.FRAGMENT_TAG)
        if(otherProfileFragment == null) {
            otherProfileFragment = OtherProfileFragment.newInstance(flashLuvUserId)
        }
        fragmentManager
                .beginTransaction()
                .replace(containerId, otherProfileFragment , OtherProfileFragment.FRAGMENT_TAG)
                .addToBackStack(OtherProfileFragment.FRAGMENT_TAG)
                .commit()
    }

    override fun displayFlirt(){

        var flirtFragment = fragmentManager.findFragmentByTag(FlirtFragment.fragmentTag)

        if(flirtFragment == null){
            flirtFragment = FlirtFragment()
        }

        fragmentManager
                .beginTransaction()
                .replace(containerId, flirtFragment, FlirtFragment.fragmentTag)
                .addToBackStack(FlirtFragment.fragmentTag)
                .commit()
    }

}