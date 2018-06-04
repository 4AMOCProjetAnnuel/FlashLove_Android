package projetannuel.bigteam.com.model

import android.os.Parcelable

/**
 * FlashLuvUser -
 * @author guirassy
 * @version $Id$
 */

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
        var flirts: Int = 0) {

    constructor() : this(false, "", 0, "",
            "", "", "", "", false,
            0, 0, 0)

}
