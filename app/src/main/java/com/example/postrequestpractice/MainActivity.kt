package com.example.postrequestpractice

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    lateinit var swipRefresh: SwipeRefreshLayout
    lateinit var addNewUserButton: FloatingActionButton
    lateinit var rvMain: RecyclerView
    var userList = arrayListOf<User.UserInfo>()

    lateinit var progress: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        swipRefresh = findViewById(R.id.swipRefresh)
        addNewUserButton = findViewById(R.id.btnAddNewUser)
        rvMain = findViewById(R.id.rvMain)

        val adaptor = RVUser(userList, this)
        rvMain.adapter = adaptor
        rvMain.layoutManager = LinearLayoutManager(this)


        showProgressDialog()
        getUsers()

        swipRefresh.setOnRefreshListener {
            getUsers()
        }
        addNewUserButton.setOnClickListener {
            val intent = Intent(this, AddNewUser::class.java)
            startActivity(intent)
        }
    }

    fun getUsers(){
        if(userList.isNotEmpty()){
            userList.clear()
        }
        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)
        val call: Call<List<User.UserInfo>?>? = apiInterface!!.getUsers()

        call?.enqueue(object : Callback<List<User.UserInfo>?>{

            override fun onResponse(
                call: Call<List<User.UserInfo>?>,
                response: Response<List<User.UserInfo>?>
            ) {
                val response: List<User.UserInfo>? = response.body()
                for(user in response!!){
                    userList.add(User.UserInfo(user.id!!,user.name.toString(), user.location.toString()))
                }
                rvMain.adapter!!.notifyDataSetChanged()
                removeProgressDialog()
                swipRefresh.isRefreshing = false
            }

            override fun onFailure(call: Call<List<User.UserInfo>?>, t: Throwable) {
                Log.d("TAG", "${t}")
                Toast.makeText(this@MainActivity, "Something went wrong $t", Toast.LENGTH_LONG).show()
                removeProgressDialog()
                swipRefresh.isRefreshing = false
            }
        })
    }

    fun showProgressDialog(){
        progress = ProgressDialog(this@MainActivity)
        progress.setTitle("Loading")
        progress.setMessage("Wait while loading...")
        progress.show()
    }
    fun removeProgressDialog(){
        progress.dismiss()
    }

    fun goToUpdatePage(user: User.UserInfo){
        val intent = Intent(this, UpdateUser::class.java)
        intent.putExtra("id", user.id)
        intent.putExtra("name", user.name)
        intent.putExtra("location", user.location)
        startActivity(intent)
    }
}