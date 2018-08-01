package com.chhd.android.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.chhd.android.common.util.SpUtils
import java.util.*

class SpUtilsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_utils)

        val temp = ArrayList<User>()
        temp.add(User("1", "小雨"))
        temp.add(User("2", "小花"))
        SpUtils.put("strList", temp)
        val list = SpUtils.getList("strList", User::class.java)
        Log.i("debug-app", "" + list);
    }


    class User {

        var id = ""
        var name = ""
        var pro = Profession()

        constructor()

        constructor(id: String, name: String) {
            this.id = id
            this.name = name
        }

        override fun toString(): String {
            return "User(id='$id', name='$name', pro=$pro)"
        }

        class Profession {

            var name = "职业"

            override fun toString(): String {
                return "Profession(name='$name')"
            }
        }
    }
}
