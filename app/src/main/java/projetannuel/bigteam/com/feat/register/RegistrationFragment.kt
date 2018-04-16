package projetannuel.bigteam.com.feat.register


import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.firebase.ui.auth.AuthUI
import projetannuel.bigteam.com.R
import android.app.Activity.RESULT_OK
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_registration.google_sign

/**
 * A simple [Fragment] subclass.
 */
class RegistrationFragment : Fragment() {

    private val RC_SIGN_IN = 444

    lateinit var providers: List<AuthUI.IdpConfig>

    companion object {
        const val registerFragmentTag = "register_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        providers = listOf(AuthUI.IdpConfig.GoogleBuilder().build())

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        google_sign.setOnClickListener {
            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when {
            requestCode == RC_SIGN_IN -> {
                if (resultCode == RESULT_OK) {
                    val user = FirebaseAuth.getInstance().currentUser
                    Log.v("@@@@TEST", "$user")
                } else {
                    Log.v("@@@@@TEST", "Authentication failed. REsponse code is : $resultCode")
                }
            }
        }
    }

}
