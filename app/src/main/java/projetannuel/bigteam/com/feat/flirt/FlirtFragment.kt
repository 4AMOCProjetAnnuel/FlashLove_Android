package projetannuel.bigteam.com.feat.flirt


import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.kodein
import com.github.salomonbrys.kodein.with
import kotlinx.android.synthetic.main.activity_main.app_toolbar
import kotlinx.android.synthetic.main.fragment_flirt.rv_flirt_chat
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.feat.flirt.epoxy.FlirtEpoxyController
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpFragment


class FlirtFragment : AppMvpFragment<FlirtContract.Presenter>(),
        FlirtContract.View {

    override val presenter: FlirtContract.Presenter by lazy {
        val flashedUserId = arguments?.getString(FlirtFragment.FLASHED_USER_ID_TAG) ?: "0"
        val flashingUserId = arguments?.getString(FlirtFragment.FLASHING_USER_ID_TAG) ?: "0"
        val params = FlirtPresenter.FactoryParameters(flashedUserId,
                flashingUserId)

        kodein()
                .value
                .with(params)
                .instance<FlirtContract.Presenter>()

    }

    override val defaultLayout: Int = R.layout.fragment_flirt

    companion object {

        const val fragmentTag = "Flirt"
        const val FLASHED_USER_ID_TAG = "flashedUserId"
        const val FLASHING_USER_ID_TAG = "flashingUserId"


        fun newInstance(flashedUserId : String, flashingUserId :String) : FlirtFragment {
            val fragment = FlirtFragment()
            val args = Bundle()
            args.putString(FLASHED_USER_ID_TAG, flashedUserId)
            args.putString(FLASHING_USER_ID_TAG, flashingUserId)
            fragment.arguments = args
            return fragment
        }
    }


    override fun provideOverridingModule() = Kodein.Module {
        bind<FlirtContract.View>() with instance(this@FlirtFragment)
    }

    private lateinit var epoxyController : FlirtEpoxyController

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

        epoxyController = FlirtEpoxyController()
        rv_flirt_chat.adapter = epoxyController.adapter
        rv_flirt_chat.layoutManager = LinearLayoutManager(context)

    }

    override fun setFlashedUserQuiz(requestedUser: FlashLuvUser) {
        Log.v("@@Quiz", requestedUser.displayName)
        epoxyController.questions = requestedUser.questions
        epoxyController.requestModelBuild()
    }

    override fun onResume() {
        super.onResume()
        //presenter.queryFlashedUser()
    }

}
