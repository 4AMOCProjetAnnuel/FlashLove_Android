package projetannuel.bigteam.com.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import projetannuel.bigteam.com.BuildConfig
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
                        .addHeader("Authorization",BuildConfig.FCMServerKey)
                        .build())
            }
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.HEADERS))
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build()
}

fun buildFCMRetrofit(): Retrofit {

    return Retrofit.Builder()
            .client(buildFCMOKHttpClient())
            .baseUrl(BuildConfig.FCMServerBaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
}

fun buildFCMService(retrofit: Retrofit) : FCMServiceInterface {
    return retrofit.create(FCMServiceInterface::class.java)
}