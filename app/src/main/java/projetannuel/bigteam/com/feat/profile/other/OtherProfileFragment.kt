package projetannuel.bigteam.com.feat.profile.other

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.kodein
import com.github.salomonbrys.kodein.with
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.app_toolbar
import kotlinx.android.synthetic.main.fragment_other_profile.fab_other_profile_like
import kotlinx.android.synthetic.main.fragment_other_profile.go_to_quizz
import kotlinx.android.synthetic.main.fragment_other_profile.tv_user_views
import kotlinx.android.synthetic.main.fragment_other_profile.user_description
import kotlinx.android.synthetic.main.fragment_other_profile.user_email
import kotlinx.android.synthetic.main.fragment_other_profile.user_flirts
import kotlinx.android.synthetic.main.fragment_other_profile.user_likes
import kotlinx.android.synthetic.main.fragment_other_profile.user_profile_picture
import kotlinx.android.synthetic.main.fragment_other_profile.user_status_and_age
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
        val flashedUserId = arguments?.getString(OtherProfileFragment.FLASHED_USER_ID_TAG) ?: "0"
        val flashingUserId = arguments?.getString(OtherProfileFragment.FLASHING_USER_ID_TAG) ?: "0"

        val params = OtherProfilePresenter.FactoryParameters(flashedUserId, flashingUserId)
        kodein()
                .value
                .with(params)
                .instance<OtherProfileContract.Presenter>()
    }

    override val defaultLayout: Int =  R.layout.fragment_other_profile

    companion object {

        const val FRAGMENT_TAG = "otherProfile"
        const val FLASHED_USER_ID_TAG = "userId"
        const val FLASHING_USER_ID_TAG = "flashingUserId"

        fun newInstance(userId : String, flashingUserId: String) : OtherProfileFragment {
            val fragment = OtherProfileFragment()
            val args = Bundle()
            args.putString(FLASHED_USER_ID_TAG, userId)
            args.putString(FLASHING_USER_ID_TAG, flashingUserId)
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
            presenter.notifyQuiz(getString(R.string.messaging_quiz_alert_message))
            presenter.queryFlashLuvUser(false, false, true)
        }

        fab_other_profile_like.setOnClickListener {
            presenter.queryFlashLuvUser(false, true, false)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.queryFlashLuvUser(true, false, false)
    }

    override fun setFlashLuvUser(flashLuvUser: FlashLuvUser) {

        if(this.view != null) {
            val displayMetrics = resources.displayMetrics
            val width = displayMetrics.widthPixels / displayMetrics.density

            (activity as AppCompatActivity).supportActionBar!!.title = flashLuvUser.displayName
            Picasso.with(activity)
                    .load(flashLuvUser.photoUrl)
                    .resize(width.toInt() + 3,250)
                    .centerCrop()
                    .into(user_profile_picture)

            val userStatus = getString(R.string.single_status_title.takeIf { flashLuvUser.single } ?: R.string.other_profile_dating_title)
            user_status_and_age.text = getString(R.string.other_profile_status_and_age, userStatus , flashLuvUser.age)
            user_email.text = flashLuvUser.email
            tv_user_views.text = flashLuvUser.views.toString()
            user_likes.text = flashLuvUser.likes.toString()
            user_flirts.text =flashLuvUser.flirts.toString()
            user_description.text = flashLuvUser.description
        }
    }

    override fun notifyFCMError() {
        Toast.makeText(context, getString(R.string.notification_quiz_error), Toast.LENGTH_SHORT)
                .show()
    }

    override fun notifyFCMSuccess() {
        Toast.makeText(context, getString(R.string.notification_quiz_success), Toast.LENGTH_SHORT)
                .show()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        //super.onSaveInstanceState(outState)
    }
}