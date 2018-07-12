package projetannuel.bigteam.com.feat.profile.self


import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.Toast
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
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
import projetannuel.bigteam.com.appFirebase.AppFirebaseDatabase
import projetannuel.bigteam.com.model.FlashLuvUser
import projetannuel.bigteam.com.mvp.AppMvpFragment

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

        loadFlashLuvUser()


        submit.setOnClickListener {
            flashLuvUser?.let {
                it.description = ed_user_description.text.toString()
                it.single = false.takeIf { user_status_single_no.isChecked } ?: true
                it.age = (Integer.parseInt(ed_user_age.text.toString()))

                presenter.updateFlashLuvUser(it)

                Toast.makeText(activity, getString(R.string.toast_profile_update_success),
                        Toast.LENGTH_SHORT).show()
            }
        }

        fab.setOnClickListener {

            RxPermissions(activity)
                    .request(Manifest.permission.CAMERA)
                    .subscribe {
                        if(it) { startActivityForResult(Intent(activity, QRCodeActivity::class.java), SelfProfileFragment.scanCode) }
                        else {
                            Toast.makeText(activity, "Camera permission is needed for QrCode Scan",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }
        }
    }

    fun loadFlashLuvUser() {

        FirebaseAuth.getInstance().currentUser?.let {
            appFirebaseDatabase.usersReference.child(it.uid)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onCancelled(error: DatabaseError?) {}
                        override fun onDataChange(snap: DataSnapshot?) {
                            if (snap != null) {
                                flashLuvUser = snap.getValue(FlashLuvUser::class.java)
                                setView()

                                val query =  appFirebaseDatabase.usersReference.child(flashLuvUser!!.uid)
                                query.addChildEventListener(userValuesEventListener)

                            }
                        }
                    })
        }

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
                                    presenter.onScanSuccess(res)
                             }
                             else -> {
                                 Toast.makeText(
                                         activity,
                                         resources.getString(R.string.qrcode_scan_error_message),
                                         Toast.LENGTH_SHORT
                                 ).show()
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

    override fun onSaveInstanceState(outState: Bundle?) {
        //super.onSaveInstanceState(outState)
    }

    val userValuesEventListener = object : ChildEventListener {

        override fun onCancelled(p0: DatabaseError?) {}

        override fun onChildMoved(p0: DataSnapshot?, p1: String?) {}

        override fun onChildChanged(data: DataSnapshot?, childName: String?) {
            childName?.let {
                if(data != null && data.value != null) {
                    loadFlashLuvUser()
                }
            }
        }
        override fun onChildAdded(p0: DataSnapshot?, p1: String?) {}

        override fun onChildRemoved(p0: DataSnapshot?) {}
    }

}
