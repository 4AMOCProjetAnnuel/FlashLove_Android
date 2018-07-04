package projetannuel.bigteam.com.feat.dashboard


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.fragment_dashboard.dashboard_navigator
import kotlinx.android.synthetic.main.toolbar.app_toolbar
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.feat.profile.self.SelfProfileFragment
import projetannuel.bigteam.com.feat.quiz.UserQuizFragment
import projetannuel.bigteam.com.mvp.AppMvpFragment


class DashboardFragment : AppMvpFragment<DashboardContract.Presenter>(), DashboardContract.View {

    override val presenter: DashboardContract.Presenter by injector.instance()

    override val defaultLayout: Int = R.layout.fragment_dashboard

    private var selfProfileFragment  =  SelfProfileFragment()
    private var userQuizFragment =  UserQuizFragment ()

    companion object {
        const val fragmentTag = "Dashboard"
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = DashboardFragment.fragmentTag
        (activity as AppCompatActivity).supportActionBar!!.setIcon(R.drawable.ic_home_white)
        (activity as AppCompatActivity).app_toolbar.navigationIcon = null

        if(childFragmentManager.findFragmentByTag(SelfProfileFragment.fragmentTag) != null){
            selfProfileFragment = childFragmentManager.findFragmentByTag(SelfProfileFragment.fragmentTag) as SelfProfileFragment
        }


        if(childFragmentManager.findFragmentByTag(UserQuizFragment.fragmentTag) != null) {
            userQuizFragment = childFragmentManager.findFragmentByTag(UserQuizFragment.fragmentTag) as UserQuizFragment
        }

        childFragmentManager
                .beginTransaction()
                .replace(R.id.navigation_items_container, selfProfileFragment, SelfProfileFragment.fragmentTag)
                .commit()

        dashboard_navigator.setOnNavigationItemSelectedListener({
            when(it.title) {

                getString(R.string.navigation_title_profile) -> {
                    childFragmentManager
                            .beginTransaction()
                            .replace(R.id.navigation_items_container, SelfProfileFragment(), SelfProfileFragment.fragmentTag)
                            .commit()
                }

                getString(R.string.navigation_title_user_quiz) -> {
                    childFragmentManager
                            .beginTransaction()
                            .replace(R.id.navigation_items_container, userQuizFragment, UserQuizFragment.fragmentTag)
                            .commit()
                }

            }
            true
        })

    }

}
