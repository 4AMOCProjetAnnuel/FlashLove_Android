package projetannuel.bigteam.com.model

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * FlashLuvUser -
 * @author guirassy
 * @version $Id$
 */

@Parcelize
data class FlashLuvUser(var single: Boolean = false,
        var description: String = "",
        var age: Int = 0,
        var picture: String = "",
        var displayName: String,
        var email: String,
        var photoUrl: String,
        var uid: String,
        var profileCompleted: Boolean = false,
        var views: Int = 0,
        var likes: Int = 0,
        var flirts: Int = 0) : Parcelable {

    constructor() : this(false, "", 0, "",
            "", "", "", "", false,
            0, 0, 0)

}
