package com.example.postrequestpractice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddNewUser : AppCompatActivity() {

    lateinit var nameUser: EditText
    lateinit var locationUser: EditText
    lateinit var saveButton: Button
    lateinit var viewButton: Button

    lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_user)

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        nameUser = findViewById(R.id.etName)
        locationUser = findViewById(R.id.etLocation)
        saveButton = findViewById(R.id.btnSave)
        viewButton = findViewById(R.id.btnView)

        saveButton.setOnClickListener {
            showProgressDialog()
            if(nameUser.text.isNotEmpty() && locationUser.text.isNotEmpty()){
                val name = nameUser.text.toString()
                val location = locationUser.text.toString()

                apiInterface?.addUser(User(name, location))?.enqueue(object : Callback<User>{
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        Toast.makeText(this@AddNewUser, "User Saved", Toast.LENGTH_LONG).show()
                        nameUser.text.clear()
                        locationUser.text.clear()
                        removeProgressDialog()
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        Toast.makeText(this@AddNewUser, "Something went wrong", Toast.LENGTH_LONG).show()
                        removeProgressDialog()
                    }
                })
            }else{
                Toast.makeText(this, "Please Type name and location", Toast.LENGTH_LONG).show()
            }
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