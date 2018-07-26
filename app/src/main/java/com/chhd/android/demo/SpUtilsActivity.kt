package com.chhd.android.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.chhd.android.common.util.SpUtils

class SpUtilsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_utils)

        val l: Long? = null
        SpUtils.put("long", l)
        Log.i("debug-app", "" + SpUtils.getLong("long"))
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
