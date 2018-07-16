package projetannuel.bigteam.com.feat.flirt.epoxy

import android.support.constraint.ConstraintLayout
import com.airbnb.epoxy.EpoxyModel
import kotlinx.android.synthetic.main.item_flirt_item.view.btn_submit_flirt_quiz_item
import kotlinx.android.synthetic.main.item_flirt_item.view.ed_flashing_user_resp
import kotlinx.android.synthetic.main.item_flirt_item.view.tv_item
import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.feat.flirt.model.FlirtViewModel

/**
 * FlirtItemEpoxyModel -
 * @author guirassy
 * @version $Id$
 */


class FlirtItemEpoxyModel(private val currentFlirtItem: FlirtViewModel,
       private val onSubmitFlirtItemClicked: (currentFlirtItem: FlirtViewModel) -> Any) : EpoxyModel<ConstraintLayout>() {

    override fun getDefaultLayout(): Int = R.layout.item_flirt_item

    override fun bind(view: ConstraintLayout) {

        view.tv_item.text = currentFlirtItem.question
        view.ed_flashing_user_resp.setText(currentFlirtItem.response)
        view.btn_submit_flirt_quiz_item.setOnClickListener {

            currentFlirtItem.response = view.ed_flashing_user_resp.text.toString()
            onSubmitFlirtItemClicked(currentFlirtItem)
        }
    }
}