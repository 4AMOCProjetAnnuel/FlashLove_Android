package projetannuel.bigteam.com.messaging.model

/**
 * AppFCMRequestModel -
 * @author guirassy
 * @version $Id$
 */


data class AppFCMRequestModel(val to : String,
        val data : AppFCMDataModel,
        val notification: AppFCMNotificationModel)
data class AppFCMNotificationModel(val title:  String, val body: String)
data class AppFCMDataModel(val userId : String)