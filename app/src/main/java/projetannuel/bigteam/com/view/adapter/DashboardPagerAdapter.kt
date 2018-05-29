package projetannuel.bigteam.com.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import projetannuel.bigteam.com.feat.profile.self.SelfProfileFragment

/**
 * DashboardPagerAdapter -
 * @author guirassy
 * @version $Id$
 */
class DashboardPagerAdapter(fragmentManager: FragmentManager)  : FragmentPagerAdapter(fragmentManager){

    private val dashboardFragments = listOf(SelfProfileFragment())

    override fun getItem(position: Int): Fragment {
       return (dashboardFragments[position] as Fragment)
    }

    override fun getCount(): Int {
        return dashboardFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        if(position == 0) {
            return "My Profile"
        }

        return  null
    }

}