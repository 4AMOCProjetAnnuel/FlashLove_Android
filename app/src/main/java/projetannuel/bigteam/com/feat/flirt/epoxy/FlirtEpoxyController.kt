package projetannuel.bigteam.com.feat.flirt.epoxy

import com.airbnb.epoxy.EpoxyController

/**
 * FlirtEpoxyController -
 * @author guirassy
 * @version $Id$
 */

class FlirtEpoxyController : EpoxyController(){

    var questions : MutableList<String>? = null

    override fun buildModels() {

        questions?.let {

            it.forEach {

                FlirtItemEpoxyModel(it)
                        .id(it.hashCode())
                        .addTo(this)
            }
        }
    }
}