package projetannuel.bigteam.com.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * NetworkFactories -
 * @author guirassy
 * @version $Id$
 */

fun buildFCMOKHttpClient(): OkHttpClient {

    return OkHttpClient.Builder()
            .addInterceptor {
                it.proceed(it.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/json")
                        .addHeader("Authorization","key=AAAAB3Q73EI:APA91bGTGzl844pGXaMT4NdsFbgA0fmQmd_rF6N4G3LCsgQyEvWUGaYD0E12a329aeOO3snFLpUYfVR8Z9r9kLzjgh6KsqMODFPpAOLhJLaiB_MDPFW6dgHhHrIo9IC0f83fasdFFtsK")
                        .build())

            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
}

fun buildFCMRetrofit(): Retrofit {

    return Retrofit.Builder()
            .client(buildFCMOKHttpClient())
            .baseUrl("https://fcm.googleapis.com/fcm/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}


fun buildFCMService(retrofit: Retrofit) : FCMServiceInterface {
    return retrofit.create(FCMServiceInterface::class.java)
}