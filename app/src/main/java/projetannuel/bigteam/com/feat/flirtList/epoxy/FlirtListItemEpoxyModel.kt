package projetannuel.bigteam.com.feat.flirtList.epoxy

import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_flirt_list_item.view.iv_from
import kotlinx.android.synthetic.main.item_flirt_list_item.view.iv_to
import kotlinx.android.synthetic.main.item_flirt_list_item.view.tv_body_humidity
import kotlinx.android.synthetic.main.item_flirt_list_item.view.tv_body_temperature
import kotlinx.android.synthetic.main.item_flirt_list_item.view.tv_heartBeat

import projetannuel.bigteam.com.R
import projetannuel.bigteam.com.feat.flirtList.model.FlirtListViewModel

/**
 * FlirtListItemEpoxyModel -
 * @author guirassy
 * @version $Id$
 */


class FlirtListItemEpoxyModel(private val flirtListViewModel: FlirtListViewModel) : EpoxyModel<ConstraintLayout>() {

    override fun getDefaultLayout(): Int = R.layout.item_flirt_list_item

    override fun bind(view: ConstraintLayout) {

        view.tv_heartBeat.text = view.resources.getString(R.string.flirt_current_user_heartbeat, flirtListViewModel.recordedHeartBeat)
        view.tv_body_humidity.text = view.resources.getString(R.string.flirt_current_user_humidity, flirtListViewModel.recordedHumidity)
        view.tv_body_temperature.text = view.resources.getString(R.string.flirt_current_user_temperature, flirtListViewModel.recordedTemperature)

        Glide
                .with(view.context)
                .load(flirtListViewModel.fromBitmap)
                .into(view.iv_from)

        Glide
                .with(view.context)
                .load(flirtListViewModel.toBitmap)
                .into(view.iv_to)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        if (!super.equals(other)) return false

        other as FlirtListItemEpoxyModel

        if (flirtListViewModel != other.flirtListViewModel) return false

        return true
    }

    override fun hashCode(): Int {
        var result = super.hashCode()
        result = 31 * result + flirtListViewModel.hashCode()
        return result
    }
}