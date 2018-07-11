package projetannuel.bigteam.com.feat.profile.self


import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
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
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpFragment
import java.util.concurrent.atomic.AtomicLong

class SelfProfileFragment : AppMvpFragment<SelfProfileContract.Presenter>(),
        SelfProfileContract.View {

    override val presenter: SelfProfileContract.Presenter by injector.instance()
    override val defaultLayout: Int = R.layout.fragment_update_profile
    private val appFirebaseDatabase: AppFirebaseDatabase by injector.instance()
    private var flashLuvUser: FlashLuvUser? = null

    private lateinit var notificationManager : NotificationManagerCompat

    companion object {
        const val fragmentTag = "update_profile"
        const val scanCode= 124
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        notificationManager = NotificationManagerCompat.from(activity)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).supportActionBar!!.title = "Dashboard"

        FirebaseAuth.getInstance().currentUser?.let {
            appFirebaseDatabase.usersReference.child(it.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError?) {}
                        override fun onDataChange(snap: DataSnapshot?) {
                            if (snap != null) {
                                flashLuvUser = snap.getValue(FlashLuvUser::class.java)
                                setView()
                            }
                        }
                    })
        }

        submit.setOnClickListener {
            flashLuvUser?.let {
                it.description = ed_user_description.text.toString()
                it.single = false.takeIf { user_status_single_no.isChecked } ?: true
                it.age = (Integer.parseInt(ed_user_age.text.toString()))

                presenter.updateFlashLuvUser(it)

                Toast.makeText(activity, "Your profile has been successfully updated",
                        Toast.LENGTH_SHORT).show()
            }
        }

        fab.setOnClickListener {
            /*
            RxPermissions(activity)
                    .request(Manifest.permission.CAMERA)
                    .subscribe {
                        if(it) { startActivityForResult(Intent(activity, QRCodeActivity::class.java), SelfProfileFragment.scanCode) }
                        else {
                            Toast.makeText(activity, "Camera permission is needed for QrCode Scan",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
            */

            sendNotification()
            presenter.onScanSuccess("RWVkt3kbU4UKaovuNLw90lSUgBx2")
        }
    }

    private fun sendNotification() {
        //TODO setIntent to open new activity or fragment(with data)
        val notifBuilder = NotificationCompat.Builder(activity, getString(R.string.o_nitification_channel))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(getString(R.string.app_name))
                .setContentText(getString(R.string.notif_just_flashed_msg, flashLuvUser!!.displayName))
                .setPriority(NotificationCompat.PRIORITY_HIGH)


        //TODO generate random ids
        notificationManager.notify(49375454, notifBuilder.build())

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        data?.let {
            when(resultCode) {

                     RESULT_OK -> {

                         when(requestCode) {
                             SelfProfileFragment.scanCode -> {
                                 val res = it.getStringExtra(QRCodeActivity.scanCodeFlag)
                                 Log.v("@@scanRES", res)
                                    //presenter.onScanSuccess()
                             }
                             else -> { }
                         }
                     }
                    else -> {}
            }
        }
    }

    private fun setView() {
        flashLuvUser?.let {
            Picasso.with(activity)
                    .load(Uri.parse(it.photoUrl))
                    .into(iv_user)

            tv_user_name.text = it.displayName

            radioGroup.check(
                    user_status_single_no.id
                            .takeIf { !flashLuvUser!!.single }
                            ?: user_status_single_yes.id
            )

            val age = it.age.toString().takeIf { it != "0"}
                    ?: ed_user_age.hint
            ed_user_age.setText(age.toString())

            ed_user_email.setText(it.email)

            ed_user_description.setText(it.description.takeIf { it.isNotEmpty() && it.isNotBlank() }
                    ?: ed_user_description.hint)

            tv_user_views.text = it.views.toString()
            tv_user_likes.text = it.likes.toString()
            tv_user_flirts.text = it.flirts.toString()
        }
    }

    private fun generateRandomId() : Int {
        return  AtomicLong(System.currentTimeMillis()).incrementAndGet().toInt()
    }

}
