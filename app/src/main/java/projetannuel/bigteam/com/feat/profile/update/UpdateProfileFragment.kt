package projetannuel.bigteam.com.feat.profile.update


import android.os.Bundle
import android.view.View
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

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        activity.actionBar.title = "Hello World"
    }
}
