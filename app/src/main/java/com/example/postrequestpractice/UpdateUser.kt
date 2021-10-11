package com.example.postrequestpractice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UpdateUser : AppCompatActivity() {

    private lateinit var nameUser: EditText
    private lateinit var locationUser: EditText
    private lateinit var updateButton: Button
    private lateinit var viewButton: Button

    lateinit var progress: ProgressDialog
    var user = User.UserInfo(0,"","")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_user)

        val extra = intent.extras
        if(extra != null){
            user.id = extra.getInt("id")
            user.name = extra.getString("name")
            user.location = extra.getString("location")
        }

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        nameUser = findViewById(R.id.etNameUpdated)
        nameUser.setText(user.name)
        locationUser = findViewById(R.id.etLocationUpdated)
        locationUser.setText(user.location)
        updateButton = findViewById(R.id.btnUpdate)
        viewButton = findViewById(R.id.btnView)

        updateButton.setOnClickListener {
            showProgressDialog()
            user.name = nameUser.text.toString()
            user.location = locationUser.text.toString()
            apiInterface?.updateUser(user.id!!,user)?.enqueue(object : Callback<User.UserInfo>{
                override fun onResponse(
                    call: Call<User.UserInfo>,
                    response: Response<User.UserInfo>
                ) {
                    Toast.makeText(this@UpdateUser, "Updated", Toast.LENGTH_LONG).show()
                    removeProgressDialog()
                }

                override fun onFailure(call: Call<User.UserInfo>, t: Throwable) {
                    Toast.makeText(this@UpdateUser, "Something went wrong", Toast.LENGTH_LONG).show()
                    removeProgressDialog()
                }
            })
        }

        viewButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

    }

    fun showProgressDialog(){
        progress = ProgressDialog(this)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.show()
    }
    fun removeProgressDialog(){
        progress.dismiss()
    }

}