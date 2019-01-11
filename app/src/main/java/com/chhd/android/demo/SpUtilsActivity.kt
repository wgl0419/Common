package com.chhd.android.demo

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.blankj.utilcode.util.LogUtils
import com.chhd.android.common.util.SpUtils

class SpUtilsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sp_utils)

        val map = HashMap<String, String>()
        map["a"] = "1"
        map["b"] = "2"
        SpUtils.put("map", map)
        val newMap = SpUtils.get("map", Map::class.java) as Map<String, String>?
        LogUtils.i(map, newMap)
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
