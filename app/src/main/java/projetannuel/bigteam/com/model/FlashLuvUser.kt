package projetannuel.bigteam.com.model

/**
 * FlashLuvUser -
 * @author guirassy
 * @version $Id$
 */

data class FlashLuvUser(val single: Boolean = false,
        val description: String = "",
        val age: Int = 0,
        val picture: String = "",
        val displayName: String,
        val email: String,
        val photoUrl: String,
        val uid: String)