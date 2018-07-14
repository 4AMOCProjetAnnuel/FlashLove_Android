package projetannuel.bigteam.com.feat.profile.self


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.bind
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.kodein
import com.github.salomonbrys.kodein.with
import com.squareup.picasso.Picasso
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.fragment_update_profile.ed_user_age
import kotlinx.android.synthetic.main.fragment_update_profile.ed_user_description
import kotlinx.android.synthetic.main.fragment_update_profile.ed_user_email
import kotlinx.android.synthetic.main.fragment_update_profile.fab
import kotlinx.android.synthetic.main.fragment_update_profile.iv_user
import kotlinx.android.synthetic.main.fragment_update_profile.radioGroup
import kotlinx.android.synthetic.main.fragment_update_profile.submit
import kotlinx.android.synthetic.main.fragment_update_profile.tv_user_flirts
import kotlinx.android.synthetic.main.fragment_update_profile.tv_user_likes
import kotlinx.android.synthetic.main.fragment_update_profile.tv_user_name
import kotlinx.android.synthetic.main.fragment_update_profile.tv_user_views
import kotlinx.android.synthetic.main.fragment_update_profile.user_status_single_no
import kotlinx.android.synthetic.main.fragment_update_profile.user_status_single_yes
import projetannuel.bigteam.com.QRCodeActivity
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpFragment

class SelfProfileFragment : AppMvpFragment<SelfProfileContract.Presenter>(),
        SelfProfileContract.View {

    override val presenter: SelfProfileContract.Presenter by lazy {
        val flashedUserId = arguments?.getString(SelfProfileFragment.FLASHED_USER_ID_TAG) ?: "0"
        val params = SelfProfilePresenter.FactoryParameters(flashedUserId)
        kodein()
                .value
                .with(params)
                .instance<SelfProfileContract.Presenter>()
    }

    override fun provideOverridingModule() = Kodein.Module {
        bind<SelfProfileContract.View>() with instance(this@SelfProfileFragment)
    }

    override val defaultLayout: Int = R.layout.fragment_update_profile


    companion object {
        const val fragmentTag = "update_profile"
        const val scanCode = 124

        const val FLASHED_USER_ID_TAG = "flashedUserId"

        fun newInstance(flashedUserId : String) : SelfProfileFragment {

            val fragment = SelfProfileFragment()
            val args = Bundle()
            args.putString(FLASHED_USER_ID_TAG, flashedUserId)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Dashboard"

        submit.setOnClickListener {

                val description = ed_user_description.text.toString()
                val single = false.takeIf { user_status_single_no.isChecked } ?: true
                val age = ed_user_age.text.toString().takeIf { ed_user_age.text.isNotBlank() &&
                        ed_user_age.text.isNotEmpty() && ed_user_age.text.toString() != ed_user_age.hint.toString() }
                        ?: "0"

                presenter.updateFlashLuvUser(description, single, age.toInt())

                Toast.makeText(activity, getString(R.string.toast_profile_update_success),
                        Toast.LENGTH_SHORT).show()
        }

        fab.setOnClickListener {

            RxPermissions(activity)
                    .request(Manifest.permission.CAMERA)
                    .subscribe {
                        if (it) {
                            startActivityForResult(Intent(activity, QRCodeActivity::class.java), SelfProfileFragment.scanCode)
                        } else {
                            Toast.makeText(activity, getString(R.string.qr_code_camera_permission_denied_message),
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let {
            when (resultCode) {
                RESULT_OK -> {
                    when (requestCode) {
                        SelfProfileFragment.scanCode -> {
                            val res = it.getStringExtra(QRCodeActivity.scanCodeFlag)
                            presenter.notifyFlashedUser(res,
                                    resources.getString(R.string.notif_just_flashed_msg))
                        }
                        else -> {
                            Toast.makeText(
                                    activity,
                                    resources.getString(R.string.qrcode_scan_error_message),
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                else -> Toast.makeText(
                        activity,
                        resources.getString(R.string.qrcode_scan_error_message),
                        Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun notifyFCMError() {
        Toast.makeText(context, getString(R.string.notification_flash_error), Toast.LENGTH_SHORT)
                .show()
    }

    override fun notifyFCMSuccess() {
        Toast.makeText(context, getString(R.string.notification_flash_success), Toast.LENGTH_SHORT)
                .show()
    }

    override fun setFlashLuvUSer(flashLuvUser: FlashLuvUser) {

        flashLuvUser.apply {

            if (this@SelfProfileFragment.view != null) {

                Picasso.with(activity)
                        .load(Uri.parse(this.photoUrl))
                        .into(iv_user)

                tv_user_name.text = this.displayName

                radioGroup.check(
                        user_status_single_no.id
                                .takeIf { !flashLuvUser!!.single }
                                ?: user_status_single_yes.id
                )

                val age = this.age.toString().takeIf { this.age != 0 }
                        ?: ed_user_age.hint
                ed_user_age.setText(age.toString())

                ed_user_email.setText(this.email)

                ed_user_description.setText(this.description.takeIf { it.isNotEmpty() && it.isNotBlank() }
                        ?: ed_user_description.hint)

                tv_user_views.text = this.views.toString()
                tv_user_likes.text = this.likes.toString()
                tv_user_flirts.text = this.flirts.toString()

            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        //super.onSaveInstanceState(outState)
    }

}
