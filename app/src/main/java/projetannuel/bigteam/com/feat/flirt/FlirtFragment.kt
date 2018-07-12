package projetannuel.bigteam.com.feat.flirt


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.kodein
import com.github.salomonbrys.kodein.with
import kotlinx.android.synthetic.main.activity_main.app_toolbar
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpFragment


class FlirtFragment : AppMvpFragment<FlirtContract.Presenter>(),
        FlirtContract.View {

    override val presenter: FlirtContract.Presenter by lazy {
        val userId = arguments?.getString(FlirtFragment.USER_ID_TAG) ?: "0"
        val params = FlirtPresenter.FactoryParameters(userId)
        kodein()
                .value
                .with(params)
                .instance<FlirtContract.Presenter>()
    }

    override val defaultLayout: Int = R.layout.fragment_flirt

    companion object {
        const val fragmentTag = "Flirt"

        const val USER_ID_TAG = "userId"

        fun newInstance(userId : String) : FlirtFragment {
            val fragment = FlirtFragment()
            val args = Bundle()
            args.putString(USER_ID_TAG, userId)
            fragment.arguments = args
            return fragment
        }
    }


    override fun provideOverridingModule() = Kodein.Module {
        bind<FlirtContract.View>() with instance(this@FlirtFragment)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).app_toolbar.title = FlirtFragment.fragmentTag
        (activity as AppCompatActivity).app_toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        (activity as AppCompatActivity).supportActionBar!!.setIcon(
                ColorDrawable(ContextCompat.getColor(activity, R.color.fui_transparent)).apply {
                    setBounds(0,0,0,0)
                }
        )
        (activity as AppCompatActivity).app_toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }
    }


    override fun setRequestedUserQuiz(requestedUser: FlashLuvUser) {
        Log.v("@@Quiz", requestedUser.displayName)
    }

    override fun onResume() {
        super.onResume()
        presenter.queryRequestingUser()
    }

}
