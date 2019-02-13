package com.example.testapp

import android.content.SharedPreferences
import android.graphics.Color
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken

class FancyBoxSettings {
    var tabColors: MutableList<Int>? = null
    var initialTab : Int? = null

    init {

    }

    fun saveSettings() {
        if(MainTestActivity.settingsSaver !=null){
            var editor : SharedPreferences.Editor = MainTestActivity.settingsSaver.edit()
            var gson : Gson = Gson()
            var json : String = gson.toJson(this)

            editor.putString("settings", json)
            editor.commit()
        }

    }

    fun loadSettings() {
        if(MainTestActivity.settingsSaver != null){
            var gson : Gson = Gson()
            var json : String  = MainTestActivity.settingsSaver.getString("settings", "")

            var turnsType = object : TypeToken<FancyBoxSettings>() {}.type

            var settings : FancyBoxSettings? = gson.fromJson(json, turnsType)
            this.tabColors = settings?.tabColors ?: defaulTabColors()
            this.initialTab = settings?.initialTab ?: defaultInitialTab
        }
    }

    companion object {
        fun defaulTabColors() : MutableList<Int>{
            var colors : MutableList<Int> = mutableListOf()
            colors.add(Color.rgb(34, 201, 120))
            colors.add(Color.rgb(255, 187, 0))
            colors.add(Color.rgb(30, 150, 255))
            colors.add(Color.rgb(255, 43, 135))

            return colors
        }

        var defaultInitialTab : Int = 0
    }
}