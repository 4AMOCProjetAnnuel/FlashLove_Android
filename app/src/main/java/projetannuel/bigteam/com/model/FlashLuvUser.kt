package projetannuel.bigteam.com.model

import android.support.annotation.Keep

/**
 * FlashLuvUser -
 * @author guirassy
 * @version $Id$
 */
@Keep
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
        var flirts: Int = 0,
        var humidity: Int = 0,
        var temperature: Int = 0,
        var heartbeat: Int = 0,
        var questions: MutableList<String> = mutableListOf(),
        var fcmToken:  String = "",
        var meToOthersFlirts : MutableList<Flirt> = mutableListOf(),
        var othersToMeFlirts : MutableList<Flirt> = mutableListOf()) {

    constructor() : this(false, "", 0, "",
            "", "", "", "", false,
            0, 0, 0,0,0,0, mutableListOf(),
            "", mutableListOf(), mutableListOf())
}
