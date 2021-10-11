package com.example.postrequestpractice

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.item_row.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RVUser(private val userList: ArrayList<User.UserInfo>, activity: Activity): RecyclerView.Adapter<RVUser.ItemViewHolder>() {

    val activity = activity

    class ItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_row,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val user = userList[position]

        holder.itemView.apply {
            tvName.text = user.name
            tvLocation.text = user.location

            fabEdit.setOnClickListener { (activity as MainActivity).goToUpdatePage(user) }
            fabDelete.setOnClickListener { deletUser(user.id!!) }
        }
    }

    override fun getItemCount() = userList.size

    fun deletUser(id: Int){
        (activity as MainActivity).showProgressDialog()
        var apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.deleteUser(id)?.enqueue(object : Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                Toast.makeText(activity, "Deleted", Toast.LENGTH_LONG).show()
                (activity as MainActivity).getUsers()
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(activity, "Something went wrong", Toast.LENGTH_LONG).show()
            }

        })
    }


}