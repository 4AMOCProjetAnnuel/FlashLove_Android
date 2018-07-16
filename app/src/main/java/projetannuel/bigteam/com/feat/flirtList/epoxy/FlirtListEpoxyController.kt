package projetannuel.bigteam.com.feat.flirtList.epoxy

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import projetannuel.bigteam.com.feat.flirtList.model.FlirtListViewModel
import java.util.Random

/**
 * FlirtListEpoxyController -
 * @author guirassy
 * @version $Id$
 */

class FlirtListEpoxyController : EpoxyController() {

    var flirtsList: MutableList<FlirtListViewModel>? = null

    override fun buildModels() {

        flirtsList?.let {

            it.forEach {

                Log.v("@@photo", "${it.fromPhotoUrl}")
                Log.v("@@photo", "${it.toPhotoUrl}")

                FlirtListItemEpoxyModel(it)
                        .id(Random(500).hashCode())
                        .addTo(this)
            }
        }
    }
}