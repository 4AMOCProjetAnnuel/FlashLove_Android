package projetannuel.bigteam.com.feat.dashboard


import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.salomonbrys.kodein.instance
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.mvp.AppMvpFragment


class DashboardFragment : AppMvpFragment<DashboardContract.Presenter>(), DashboardContract.View {

    override val presenter: DashboardContract.Presenter by injector.instance()

    override val defaultLayout: Int = R.layout.fragment_dashboard


    companion object {
        const val fragmentTag = "Dashboard"
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = DashboardFragment.fragmentTag
        (activity as AppCompatActivity).supportActionBar!!.setIcon(R.drawable.ic_home_white)
        (activity as AppCompatActivity).supportActionBar!!.setHomeButtonEnabled(true)

    }

}
