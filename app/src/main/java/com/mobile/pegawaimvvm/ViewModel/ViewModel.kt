package com.mobile.pegawaimvvm.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobile.pegawai.model.ResponseUser
import com.mobile.pegawaimvvm.Repository.Repository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class ViewModel : ViewModel() {

    // todo 2 inisiaslisasi repository
    val repository = Repository()

    // todo 3 membuat variabel agar bisa ditampung oleh view
    //register dan selectData
    var responData = MutableLiveData<ResponseUser>()
    var errorData = MutableLiveData<Throwable>()

    //delete
    var responDelete = MutableLiveData<ResponseUser>()
    var errorDelete = MutableLiveData<Throwable>()

    //update
    var responUpdate = MutableLiveData<ResponseUser>()
    var errorUpdate = MutableLiveData<Throwable>()

    // todo 4 membuat fungsi yang diperlukan
    fun tampilData() {
        repository.getData({ response ->
            responData.value = response
        }, { t ->
            errorData.value = t
        })
    }

    fun register(
        nama: RequestBody,
        email: RequestBody,
        pass: RequestBody,
        photo: MultipartBody.Part
    ) {
        repository.tambahdata(nama, email, pass, photo, { response ->
            responData.value = response
        }, { t ->
            errorData.value = t
        })
    }

    fun delete(idUser: String?) {
        repository.deleteData(idUser, { response ->
            responDelete.value = response
        }, { t ->
            errorDelete.value = t
        })
    }

    fun update(
        idUser: RequestBody,
        nama: RequestBody,
        email: RequestBody,
        pass: RequestBody,
        photo: MultipartBody.Part
    ) {
        repository.updateData(idUser, nama, email, pass, photo, {
            response->
            responUpdate.value = response
        }, {
            t->
            errorUpdate.value = t
        })
    }
}