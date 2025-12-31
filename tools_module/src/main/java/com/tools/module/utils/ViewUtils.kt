package com.tools.module.utils

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.Toast
import cn.tools.module.R

fun String.parseColor(): Int = Color.parseColor(this)

fun View.showOrHide(show: Boolean) {
    visibility = if (show) View.VISIBLE else View.GONE
}

fun View.singleClick(block: (View) -> Unit) {
    setOnClickListener(throttleClick(1000, block))
}

fun String.toast(context: Context){
    Toast.makeText(context,this,Toast.LENGTH_SHORT).show()
}

fun throttleClick(wait: Long = 1000, block: ((View) -> Unit)): View.OnClickListener {
    return View.OnClickListener { v ->
        val current = System.currentTimeMillis()
        val lastClickTime = (v.getTag(R.id.click_timestamp) as? Long) ?: 0
        if (current - lastClickTime > wait) {
            v.setTag(R.id.click_timestamp, current)
            block(v)
        }
    }
}