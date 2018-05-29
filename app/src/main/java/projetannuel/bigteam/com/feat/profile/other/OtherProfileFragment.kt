package projetannuel.bigteam.com.feat.profile.other

import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import com.github.salomonbrys.kodein.instance
import kotlinx.android.synthetic.main.activity_main.app_toolbar
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.mvp.AppMvpFragment

/**
 * OtherProfileFragment -
 * @author guirassy
 * @version $Id$
 */

class OtherProfileFragment : AppMvpFragment<OtherProfileContract.Presenter>(),
        OtherProfileContract.View {

    override val presenter: OtherProfileContract.Presenter by injector.instance()

    override val defaultLayout: Int =  R.layout.fragment_other_profile

    companion object {
        const val fragmentTag = "otherProfile"
        const val fragmentTitle = "Other Profile"
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).supportActionBar!!.title = OtherProfileFragment.fragmentTitle
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
}