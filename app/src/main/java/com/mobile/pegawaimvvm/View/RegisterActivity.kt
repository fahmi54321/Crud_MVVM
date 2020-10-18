package com.mobile.pegawaimvvm.View

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobile.pegawai.model.ResponseUser
import com.mobile.pegawai.model.ResultItem
import com.mobile.pegawaimvvm.R
import com.mobile.pegawaimvvm.Utils.ImageAttachmentListener
import com.mobile.pegawaimvvm.Utils.ImageUtils
import com.mobile.pegawaimvvm.ViewModel.ViewModel
import kotlinx.android.synthetic.main.activity_register.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class RegisterActivity : AppCompatActivity(), ImageAttachmentListener {

    var photo: File? = null;
    var imageUtils: ImageUtils? = null

    //todo 1 deklarasi view model
    lateinit var viewModel: ViewModel
    lateinit var viewModelEdit: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(
            R.layout.activity_register
        )

        val getData = intent.getParcelableExtra<ResultItem>("data")
        if (getData != null) {
            idUser.setText(getData.id_user)
            edtNama.setText(getData.nama)
            edtEmail.setText(getData.email)
            edtPassword.setText(getData.password)
            val Contants = "http://10.124.1.167/Pegawai/foto_user/"
            Glide.with(this)
                .load(Contants + getData.photo)
                .apply(RequestOptions().error(R.drawable.icon_nopic))
                .into(imgProfile)
            bttnSimpan.text = "Update"
        }

        //todo 2 inisialiasi view model
        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModelEdit = ViewModelProviders.of(this).get(ViewModel::class.java)

        //todo 3 membuat fungsi pengamatan view model
        pengamatan()

        imageUtils = ImageUtils(this)

        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
                checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ),
                    0
                )
            }
        }

        imgPlus.setOnClickListener {
            imageUtils?.imagepicker(1)
        }

        bttnSimpan.setOnClickListener {
            if (photo != null) {

                //todo ambil view

                val requestBody = RequestBody.create(MediaType.parse("image/*"), photo)

                //id
                val requestBodyId: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    idUser.text.toString()
                )

                //nama
                val requestBodyNama: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtNama.text.toString()
                )

                //email
                val requestBodyEmail: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtEmail.text.toString()
                )

                //password
                val requestBodyPasword: RequestBody = RequestBody.create(
                    MediaType.parse("text/plain"),
                    edtPassword.text.toString()
                )

                //photo
                val multipartBodyPhoto =
                    MultipartBody.Part.createFormData("photo", photo?.name, requestBody)

                when (bttnSimpan.text) {
                    "Update" -> {
                        AlertDialog.Builder(this).apply {
                            setTitle("Update Data")
                            setMessage("Yakin mau mengupdate ?")
                            setPositiveButton("Update") { dialog, which ->
                                //todo 4 eksekusi view model
                                viewModelEdit.update(
                                    requestBodyId,
                                    requestBodyNama,
                                    requestBodyEmail,
                                    requestBodyPasword,
                                    multipartBodyPhoto
                                )
                            }
                            setNegativeButton("Batal") { dialog, which ->
                                dialog.dismiss()
                            }.show()
                        }
                    }
                    else -> {
                        //todo 4 eksekusi view model
                        viewModel.register(
                            requestBodyNama,
                            requestBodyEmail,
                            requestBodyPasword,
                            multipartBodyPhoto
                        )
                    }
                }


            } else {
                Toast.makeText(this, "Foto tidak boleh kosong", Toast.LENGTH_SHORT).show()
            }
        }

    }

    //todo 3 membuat fungsi pengamatan view model
    private fun pengamatan() {
        viewModel.responData.observe(this, Observer { responSukses(it) })
        viewModel.errorData.observe(this, Observer { responGagal(it) })
        viewModelEdit.responUpdate.observe(this, Observer { responUpdate(it) })
        viewModelEdit.errorUpdate.observe(this, Observer { errorUpdate(it) })
    }

    private fun responUpdate(it: ResponseUser?) {
        Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        Log.d("Response Sukses",it?.message?:"")
    }

    private fun errorUpdate(it: Throwable?) {
        Toast.makeText(this, it?.localizedMessage, Toast.LENGTH_SHORT).show()

        Log.d("Response Gagal",it?.message?:"")
    }

    private fun responGagal(it: Throwable?) {
        Toast.makeText(this, it?.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun responSukses(it: ResponseUser?) {
        Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        imageUtils?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        imageUtils?.request_permission_result(requestCode, permissions, grantResults)
    }

    override fun image_attachment(from: Int, filename: String?, file: Bitmap?, uri: Uri?) {
        imgProfile.setImageBitmap(file)

        val path: String? = imageUtils?.getPath(uri)
        photo = File(path)
    }
}