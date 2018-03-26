package projetannuel.bigteam.com.feat.parties


import android.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import projetannuel.bigteam.com.R


/**
 * A simple [Fragment] subclass.
 */
class PartiesFragment : Fragment() {

    companion object {
        const val PARTIES_FRAGMENT_TAG = "PARTIES_FRAGMENT"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_parties2, container, false)
    }

}
