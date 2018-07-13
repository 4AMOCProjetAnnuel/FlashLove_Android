package projetannuel.bigteam.com.network

import io.reactivex.Observable
import projetannuel.bigteam.com.messaging.model.AppFCMRequestModel
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

/**
 * FCMServiceInterface -
 * @author guirassy
 * @version $Id$
 */
interface FCMServiceInterface {

    @POST("send/")
    fun postNotification(@Body appFCMRequestModel: AppFCMRequestModel) : Observable<Any>
}