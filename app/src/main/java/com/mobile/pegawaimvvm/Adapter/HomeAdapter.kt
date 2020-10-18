package com.mobile.pegawai.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.mobile.pegawai.model.ResultItem
import com.mobile.pegawaimvvm.Constants.Constants
import com.mobile.pegawaimvvm.R
import kotlinx.android.synthetic.main.item_pegawai.view.*

class HomeAdapter(
    val data: List<ResultItem?>?,
    val itemClick: onClickListener
) : RecyclerView.Adapter<HomeAdapter.MainViewHolder>() {
    class MainViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val nama = view.txtNama
        val email = view.txtEmail
        val pass = view.txtPassword
        val imgProfile = view.imgProfile
        val btnEdit = view.bttnEdit
        val btnHapus = view.bttnDelete
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pegawai, parent, false)

        return MainViewHolder(view)
    }

    override fun getItemCount(): Int = data?.size ?: 0

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        val item = data?.get(position)
        holder.nama.text = item?.nama
        holder.email.text = item?.email
        holder.pass.text = item?.password
        var constants:Constants?=null
        constants = Constants()
        Glide.with(holder.itemView.context)
            .load(constants?.URL_Image + data?.get(position)?.photo)
            .apply(RequestOptions().error(R.drawable.icon_nopic))
            .into(holder.imgProfile)

        holder.view.setOnClickListener {
            itemClick.detail(item)
        }
        holder.btnHapus.setOnClickListener {
            itemClick.hapus(item)
        }
        holder.btnEdit.setOnClickListener {
            itemClick.edit(item)
        }
    }

    interface onClickListener {
        fun detail(item: ResultItem?)
        fun hapus(item: ResultItem?)
        fun edit(item: ResultItem?)
    }
}

