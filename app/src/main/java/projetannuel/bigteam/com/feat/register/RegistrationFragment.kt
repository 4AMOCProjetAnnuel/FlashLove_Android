package projetannuel.bigteam.com.feat.register


import android.os.Bundle
import android.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import projetannuel.bigteam.com.R


/**
 * A simple [Fragment] subclass.
 */
class RegistrationFragment : Fragment() {

    companion object {
        val REGISTER_FRAGMENT_TAG = "register_fragment"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_registration, container, false)



    }

}
