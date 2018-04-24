package projetannuel.bigteam.com.feat.register


import android.app.Activity.RESULT_OK
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_registration.facebook_sign
import kotlinx.android.synthetic.main.fragment_registration.google_sign
import kotlinx.android.synthetic.main.fragment_registration.instagram_sign_in
import kotlinx.android.synthetic.main.fragment_registration.twitter_sign_in
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.mvp.AppMvpFragment


class RegisterFragment : AppMvpFragment<RegisterContract.Presenter>(), RegisterContract.View {

    override val presenter: RegisterContract.Presenter by injector.instance()

    override val defaultLayout = R.layout.fragment_registration

    private val RCSIGNIN = 444

    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference

    private var user: FirebaseUser? = null

    companion object {
        const val registerFragmentTag = "register_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance()
        databaseRef = database.reference

        user = FirebaseAuth.getInstance().currentUser

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        google_sign.setOnClickListener { startActivityForResult( presenter.signUpWithProvider() , RCSIGNIN ) }

        facebook_sign.setOnClickListener { startActivityForResult( presenter.signUpWithProvider() , RCSIGNIN ) }

        twitter_sign_in.setOnClickListener { startActivityForResult( presenter.signUpWithProvider() , RCSIGNIN ) }

        instagram_sign_in.setOnClickListener { startActivityForResult( presenter.signUpWithProvider() , RCSIGNIN ) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {

            RESULT_OK -> {

                if (requestCode == RCSIGNIN) {

                    user?.let {

                        //TODO save in database

                        it.providerData.forEach {

                            Log.v("@@@@TEST ProviderId ", it.providerId)

                            Log.v("@@@@TEST Display Name ", "${it.displayName}")
                            Log.v("@@@@TEST Email ", "${it.email}")
                            Log.v("@@@@TEST Photo Url ", "${it.photoUrl}")
                            Log.v("@@@@TEST email verified", " ${it.isEmailVerified}")
                            Log.v("@@@@TEST uid ", it.uid)

                        }

                    }
                }
            }
            else -> {
                toastMessage(getString(R.string.sign_up_with_provider_error_message))
            }

        }
    }

}
