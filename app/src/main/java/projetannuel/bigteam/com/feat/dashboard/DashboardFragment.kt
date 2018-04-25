package projetannuel.bigteam.com.feat.dashboard


import com.github.salomonbrys.kodein.instance
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.mvp.AppMvpFragment


class DashboardFragment : AppMvpFragment<DashboardContract.Presenter>(), DashboardContract.View {

    override val presenter: DashboardContract.Presenter by injector.instance()

    override val defaultLayout: Int = R.layout.fragment_dashboard


    companion object {
        const val fragmentTag = "dashboard"
    }

}
