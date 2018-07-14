package projetannuel.bigteam.com.feat.flirt.epoxy

import android.support.constraint.ConstraintLayout
import com.airbnb.epoxy.EpoxyModel
import kotlinx.android.synthetic.main.item_flirt_item.view.tv_item
import projetannuel.bigteam.com.R

/**
 * FlirtItemEpoxyModel -
 * @author guirassy
 * @version $Id$
 */
class FlirtItemEpoxyModel(val question : String) : EpoxyModel<ConstraintLayout>(){

    override fun getDefaultLayout(): Int = R.layout.item_flirt_item

    override fun bind(view: ConstraintLayout) {
        view.tv_item.text = question
    }
}