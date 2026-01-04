package com.tools.module.utils

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


object SharedPreferencesUtils {

    private var sharedPreferences: SharedPreferences? = null
    private var editor: SharedPreferences.Editor? = null

    private fun createSP(context: Context){
        if(sharedPreferences == null){
            sharedPreferences = context.getSharedPreferences("tools_MODULE", MODE_PRIVATE)
            editor = sharedPreferences?.edit()
        }
    }

    fun saveSPString(context: Context, key: String, value: String){
        createSP(context)
        editor?.putString(key, value)
        editor?.commit() // 提交数据
    }

    fun getSPString(context: Context, key: String): String{
        createSP(context)
        var mResult = ""
        mResult = sharedPreferences!!.getString(key, "")
        return mResult
    }
}