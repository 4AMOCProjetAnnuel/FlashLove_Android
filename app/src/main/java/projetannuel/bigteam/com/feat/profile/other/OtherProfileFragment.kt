package projetannuel.bigteam.com.feat.profile.other

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.kodein
import com.github.salomonbrys.kodein.with
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.app_toolbar
import kotlinx.android.synthetic.main.fragment_other_profile.go_to_quizz
import kotlinx.android.synthetic.main.fragment_other_profile.user_profile_picture
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpFragment

/**
 * OtherProfileFragment -
 * @author guirassy
 * @version $Id$
 */

class OtherProfileFragment : AppMvpFragment<OtherProfileContract.Presenter>(),
        OtherProfileContract.View {

    override val presenter: OtherProfileContract.Presenter by lazy {
        val userId = arguments?.getString(OtherProfileFragment.USER_ID_TAG) ?: "0"
        val params = OtherProfilePresenter.FactoryParameters(userId)
        kodein()
                .value
                .with(params)
                .instance<OtherProfileContract.Presenter>()
    }

    override val defaultLayout: Int =  R.layout.fragment_other_profile

    companion object {

        const val FRAGMENT_TAG = "otherProfile"
        const val FRAGMENT_TITLE = "Other Profile"
        const val USER_ID_TAG = "userId"

        fun newInstance(userId : String) : OtherProfileFragment {
            val fragment = OtherProfileFragment()
            val args = Bundle()
            args.putString(USER_ID_TAG, userId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun provideOverridingModule() = Kodein.Module {
        bind<OtherProfileContract.View>() with instance(this@OtherProfileFragment)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).app_toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        (activity as AppCompatActivity).supportActionBar!!.setIcon(
                ColorDrawable(ContextCompat.getColor(activity, R.color.fui_transparent)).apply {
                    setBounds(0,0,0,0)
                }
        )
        (activity as AppCompatActivity).app_toolbar.setNavigationOnClickListener {
            activity.onBackPressed()
        }

        go_to_quizz.setOnClickListener {
            presenter.goToQuiz()
        }
    }

    override fun setFlashLuvUser(flashLuvUser: FlashLuvUser) {
        val displayMetrics = resources.displayMetrics
        val width = displayMetrics.widthPixels / displayMetrics.density

        (activity as AppCompatActivity).supportActionBar!!.title = flashLuvUser.displayName
        Picasso.with(activity)
                .load(flashLuvUser.photoUrl)
                 .resize(width.toInt() + 3,250)
                .centerCrop()
                .into(user_profile_picture)
    }

    override fun onResume() {
        super.onResume()
        presenter.queryFlashLuvUser()
    }
}