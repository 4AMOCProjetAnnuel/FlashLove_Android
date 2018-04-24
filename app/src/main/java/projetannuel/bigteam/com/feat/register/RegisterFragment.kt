package projetannuel.bigteam.com.feat.register


import android.app.Activity.RESULT_OK
import android.content.Intent
import com.github.salomonbrys.kodein.instance
import com.google.firebase.auth.FirebaseAuth
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.mvp.AppMvpFragment


class RegisterFragment : AppMvpFragment<RegisterContract.Presenter>(), RegisterContract.View {

    override val presenter: RegisterContract.Presenter by injector.instance()

    override val defaultLayout = R.layout.fragment_registration

    private val RCSIGNIN = 444

    companion object {
        const val registerFragmentTag = "register_fragment"
    }

    override fun onStart() {
        super.onStart()
        startActivityForResult(presenter.signUpWithProvider(), RCSIGNIN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when (resultCode) {

            RESULT_OK -> {

                if (requestCode == RCSIGNIN) {

                    FirebaseAuth.getInstance().currentUser?.let {
                        presenter.onSignUpWithProviderSucceeded(it)
                    }
                }
            }
            else -> {
                toastMessage(getString(R.string.sign_up_with_provider_error_message))
            }

        }
    }

}
