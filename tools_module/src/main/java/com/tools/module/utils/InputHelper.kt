package com.tools.module.utils

import android.widget.TextView

/**
 *管理输入框状态的工具类
 */
class InputHelper(
    private val tvHintMap: Map<TextView, String>,
    private val normalColor: String,
    private val emptyColor: String,
    private val maxLength: Int
) {
    private var current: TextView? = null
    private val tvTextMap = mutableMapOf<TextView, String>()

    init {
        tvHintMap.entries.forEach {
            tvTextMap[it.key] = ""
        }
        current = tvHintMap.keys.first()
    }

    private fun TextView.updateTv() {
        val empty = tvTextMap[this].isNullOrEmpty()
        this.setTextColor((if (empty) emptyColor else normalColor).parseColor())
        this.text = if (empty) tvHintMap[this] else tvTextMap[this]
    }

    fun reset(c: TextView? = null) {
        tvTextMap.keys.forEach {
            tvTextMap[it] = ""
        }
        tvHintMap.keys.forEach {
            it.updateTv()
        }
        c?.let { current = c }
    }

    fun updateTvText(tv: TextView, s: String) {
        tvTextMap[tv] = s
        tv.updateTv()
    }

    fun clear() {
        current?.let {
            tvTextMap[it] = ""
            it.updateTv()
        }
    }

    fun delete() {
        current?.let {
            val currentText = tvTextMap[it] ?: ""
            if (currentText.isNotEmpty()) {
                tvTextMap[it] = currentText.substring(0, currentText.length - 1)
                it.updateTv()
            }
        }
    }

    fun input(i: String) {
        current?.let {
            val currentText = tvTextMap[it] ?: ""
            if (currentText.length < maxLength) {
                tvTextMap[it] = currentText + i
                it.updateTv()
            }
        }
    }

    fun isCurrent(tv: TextView) = current?.id == tv.id

    fun switchCurrent(textView: TextView) {
        if (tvHintMap.containsKey(textView)) current = textView
    }

    fun getInputText(tv: TextView): String {
        return tvTextMap[tv] ?: ""
    }

    fun hasEmpty() = tvTextMap.count { it.value.isEmpty() } > 0

    fun allNotEmpty() = tvTextMap.count { it.value.isNotEmpty() } == tvTextMap.size

    fun allEmpty() = tvTextMap.count { it.value.isEmpty() } == tvTextMap.size

    fun isNotEmpty(tv: TextView) = tvTextMap[tv]?.isNotEmpty() == true

}