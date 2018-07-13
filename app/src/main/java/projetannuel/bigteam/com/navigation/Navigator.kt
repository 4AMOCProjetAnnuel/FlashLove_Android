package projetannuel.bigteam.com.navigation

/**
 * Navigator -
 * @author guirassy
 * @version $Id$
 */
interface Navigator {

    fun displayRegistration()
    fun displayPartyDetails()
    fun displayParties()
    fun displaySelfProfile(flashLuvUserId: String)
    fun displayDashboard()
    fun displayOtherProfile(flashLuvUserId: String)
    fun displayFlirt(requestUserId: String)

}