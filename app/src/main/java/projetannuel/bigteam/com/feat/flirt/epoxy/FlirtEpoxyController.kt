package projetannuel.bigteam.com.feat.flirt.epoxy

import com.airbnb.epoxy.EpoxyController
import projetannuel.bigteam.com.feat.flirt.model.FlirtViewModel

/**
 * FlirtEpoxyController -
 * @author guirassy
 * @version $Id$
 */

class FlirtEpoxyController(private val onSubmitCurrentFlirtItemClicked: (currentFlirtItem : FlirtViewModel) -> Any) : EpoxyController(){

    var flirtViewModels : MutableList<FlirtViewModel>? = null

    override fun buildModels() {
        flirtViewModels?.let {
            it.forEach {
                FlirtItemEpoxyModel(it, onSubmitCurrentFlirtItemClicked)
                        .id(it.hashCode())
                        .addTo(this)
            }
        }
    }
}