package com.mobile.pendataransdmvprxandroid.network.kotlin

import com.mobile.pegawai.model.ResponseUser
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*


//todo 2.1 Config API
interface ConfigApi {

    @GET("selectUser.php")
    fun selectUser(): Flowable<ResponseUser>

    @GET("deleteUser.php")
    fun deleteUser(
        @Query("id_user") id_user: String?
    ): Single<ResponseUser>

    @Multipart
    @POST("signup.php")
    fun signup(
        @Part("nama") nama:RequestBody,
        @Part("email") email:RequestBody,
        @Part("password") password:RequestBody,
        @Part photo:MultipartBody.Part
    ): Single<ResponseUser>
    //todo Single jika response nya cuma satu yaitu berhasil atau gagal
    //todo Observable jika response nya banyak
    //todo Flowable jika response nya banyak

    @Multipart
    @POST("editPegawai.php")
    fun editPegawai(
        @Part("id_user") id_user:RequestBody,
        @Part("nama") nama:RequestBody,
        @Part("email") email:RequestBody,
        @Part("password") password:RequestBody,
        @Part photo:MultipartBody.Part
    ): Single<ResponseUser>
    //todo Single jika response nya cuma satu yaitu berhasil atau gagal
    //todo Observable jika response nya banyak
    //todo Flowable jika response nya banyak
}