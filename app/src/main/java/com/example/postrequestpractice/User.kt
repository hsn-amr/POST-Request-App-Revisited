package com.example.postrequestpractice

import com.google.gson.annotations.SerializedName

data class User(val name: String, val location: String) {

    class UserInfo {

        @SerializedName("pk")
        var id: Int? = null

        @SerializedName("name")
        var name: String? = null

        @SerializedName("location")
        var location: String? = null

        constructor(id: Int, name: String, location: String){
            this.id = id
            this.name = name
            this.location = location
        }

    }
}