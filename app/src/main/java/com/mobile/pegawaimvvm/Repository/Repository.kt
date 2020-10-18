package com.mobile.pegawaimvvm.Repository

import com.mobile.pegawai.model.ResponseUser
import com.mobile.pendataransdmvprxandroid.network.kotlin.ConfigNetwork
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

class Repository {

    // todo 1 Konfigurasi repository
    fun getData(reponseHandler: (ResponseUser) -> Unit, errorHandler: (Throwable) -> Unit) {
        ConfigNetwork.getRetrofit().selectUser()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                reponseHandler(response)
            }, { t ->
                errorHandler(t)
            })
    }

    fun tambahdata(
        nama: RequestBody,
        email: RequestBody,
        pass: RequestBody,
        photo: MultipartBody.Part,
        reponseHandler: (ResponseUser) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        ConfigNetwork.getRetrofit().signup(nama, email, pass, photo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                reponseHandler(response)
            }, { t ->
                errorHandler(t)
            })
    }

    fun deleteData(
        idUser: String?,
        reponseHandler: (ResponseUser) -> Unit, errorHandler: (Throwable) -> Unit
    ) {
        ConfigNetwork.getRetrofit().deleteUser(idUser)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ response ->
                reponseHandler(response)
            }, { t ->
                errorHandler(t)
            })
    }

    fun updateData(
        idUser:RequestBody,
        nama: RequestBody,
        email: RequestBody,
        pass: RequestBody,
        photo: MultipartBody.Part,
        reponseHandler: (ResponseUser) -> Unit,
        errorHandler: (Throwable) -> Unit
    ) {
        ConfigNetwork.getRetrofit().editPegawai(idUser,nama,email,pass,photo)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                response->
                    reponseHandler(response)
            },{
                t->
                    errorHandler(t)
            })
    }
}