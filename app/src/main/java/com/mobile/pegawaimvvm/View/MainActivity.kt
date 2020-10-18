package com.mobile.pegawaimvvm.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.mobile.pegawai.adapter.HomeAdapter
import com.mobile.pegawai.model.ResponseUser
import com.mobile.pegawai.model.ResultItem
import com.mobile.pegawaimvvm.R
import com.mobile.pegawaimvvm.ViewModel.ViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    // todo 1 deklarasi view model select
    lateinit var viewModelSelect: ViewModel
    lateinit var viewModeDelete: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // todo 2 inisialiasi view model
        viewModelSelect = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModeDelete = ViewModelProviders.of(this).get(ViewModel::class.java)

        // todo 3 membuat fungsi pengamatan view model
        pengamatan()

        // todo 4 eksekusi view model
        viewModelSelect.tampilData()

        floatingActionButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    // todo 3 membuat fungsi pengamatan view model
    private fun pengamatan() {
        viewModelSelect.responData.observe(this, Observer { showData(it) })
        viewModelSelect.errorData.observe(this, Observer { errorShowData(it) })
        viewModeDelete.responDelete.observe(this, Observer { successDeleteData(it) })
        viewModeDelete.errorDelete.observe(this, Observer { errorDeleteData(it) })
    }


    private fun showData(it: ResponseUser?) {
        val adapter = HomeAdapter(it?.result, object : HomeAdapter.onClickListener {
            override fun detail(item: ResultItem?) {
            }

            override fun hapus(item: ResultItem?) {

                AlertDialog.Builder(this@MainActivity).apply {
                    setTitle("Hapus Data")
                    setMessage("Yakin mau menghapus ?")
                    setPositiveButton("Hapus") { dialog, which ->

                        // todo 4 eksekusi view model
                        viewModeDelete.delete(item?.id_user)
                    }
                    setNegativeButton("Batal") { dialog, which ->
                        dialog.dismiss()
                    }.show()
                }
            }

            override fun edit(item: ResultItem?) {
                val intent = Intent(applicationContext, RegisterActivity::class.java)
                intent.putExtra("data",item)
                startActivity(intent)
            }

        })

        rv_data.adapter = adapter
    }

    private fun successDeleteData(it: ResponseUser?) {
        Toast.makeText(this, it?.message, Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun errorShowData(it: Throwable?) {
        Toast.makeText(this, it?.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    private fun errorDeleteData(it: Throwable?) {
        Toast.makeText(this, it?.localizedMessage, Toast.LENGTH_SHORT).show()
    }


    override fun onResume() {
        super.onResume()
        viewModelSelect.tampilData()
    }
}