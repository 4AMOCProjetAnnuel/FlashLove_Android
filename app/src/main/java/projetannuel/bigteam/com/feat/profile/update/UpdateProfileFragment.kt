package projetannuel.bigteam.com.feat.profile.update


import com.github.salomonbrys.kodein.instance
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.mvp.AppMvpFragment

class UpdateProfileFragment : AppMvpFragment<UpdateProfileContract.Presenter>(),
        UpdateProfileContract.View {


    override val presenter: UpdateProfileContract.Presenter by injector.instance()

    override val defaultLayout: Int = R.layout.fragment_update_profile

    companion object {
        const val fragmentTag = "update_profile"
    }

}
