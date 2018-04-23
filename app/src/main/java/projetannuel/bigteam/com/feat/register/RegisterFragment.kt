package projetannuel.bigteam.com.feat.register


import android.app.Activity.RESULT_OK
import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_registration.facebook_sign
import kotlinx.android.synthetic.main.fragment_registration.google_sign
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.mvp.AppMvpFragment


/**
 * A simple [Fragment] subclass.
 */
class RegisterFragment : AppMvpFragment<RegisterContract.Presenter>(), RegisterContract.View {

    override val presenter: RegisterContract.Presenter by injector.instance()

    override val defaultLayout = R.layout.fragment_registration

    private val RC_SIGN_IN = 444


    private lateinit var database: FirebaseDatabase
    private lateinit var databaseRef: DatabaseReference

    private var user: FirebaseUser? = null

    companion object {
        const val registerFragmentTag = "register_fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_registration, container, false)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = FirebaseDatabase.getInstance()
        databaseRef = database.reference

        user = FirebaseAuth.getInstance().currentUser

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        google_sign.setOnClickListener { startActivityForResult( presenter.signUpWithProvider() , RC_SIGN_IN ) }

        facebook_sign.setOnClickListener { startActivityForResult( presenter.signUpWithProvider() , RC_SIGN_IN ) }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {

            RESULT_OK -> {

                if (requestCode == RC_SIGN_IN) {

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
                Log.v("@@@@@TEST", "Authentication failed. REsponse code is : $resultCode")
            }

        }
    }

}