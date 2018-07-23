package com.chhd.android.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.chhd.android.common.util.SpUtils

class SpUtilsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_utils)

        val user1 = User("1", "小明")
        val user2 = User("2", "小花")


        val list = ArrayList<User>()
        list.add(User("1", "小明"))
        list.add(User("2", "小花"))
        SpUtils.put("list", list)
    }


    class User {

        var id = ""
        var name = ""

        constructor()

        constructor(id: String, name: String) {
            this.id = id
            this.name = name
        }

        override fun toString(): String {
            return "User(id='$id', name='$name')"
        }
    }
}
