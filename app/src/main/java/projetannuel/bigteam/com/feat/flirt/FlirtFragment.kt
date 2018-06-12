package projetannuel.bigteam.com.feat.flirt


import com.github.salomonbrys.kodein.instance
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.mvp.AppMvpFragment


class FlirtFragment : AppMvpFragment<FlirtContract.Presenter>(),
        FlirtContract.View {

    override val presenter: FlirtContract.Presenter by injector.instance()
    override val defaultLayout: Int = R.layout.fragment_flirt

    companion object {
        const val fragmentTag = "flirt"
    }


}
