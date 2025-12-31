package com.tools.module.wright

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.GridLayout
import android.widget.ImageView
import cn.tools.module.R
import com.tools.module.bean.Constants.KB_C_0
import com.tools.module.bean.Constants.KB_C_1
import com.tools.module.bean.Constants.KB_C_2
import com.tools.module.bean.Constants.KB_C_3
import com.tools.module.bean.Constants.KB_C_4
import com.tools.module.bean.Constants.KB_C_5
import com.tools.module.bean.Constants.KB_C_6
import com.tools.module.bean.Constants.KB_C_7
import com.tools.module.bean.Constants.KB_C_8
import com.tools.module.bean.Constants.KB_C_9
import com.tools.module.bean.Constants.KB_C_AC
import com.tools.module.bean.Constants.KB_C_DEL
import com.tools.module.bean.Constants.KB_C_EQUAL
import com.tools.module.bean.Constants.KB_C_POINT

/**
 * 键盘
 */
class KeyboardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GridLayout(context, attrs, defStyleAttr) {

    private var clickListener: ((key: String) -> Unit)? = null

    private var btn0: ImageView? = null
    private var btn1: ImageView? = null
    private var btn2: ImageView? = null
    private var btn3: ImageView? = null
    private var btn4: ImageView? = null
    private var btn5: ImageView? = null
    private var btn6: ImageView? = null
    private var btn7: ImageView? = null
    private var btn8: ImageView? = null
    private var btn9: ImageView? = null
    private var btnPoint: ImageView? = null
    private var btnDel: ImageView? = null
    private var btnAc: ImageView? = null
    private var btnEqual: ImageView? = null

    init {
        val view = LayoutInflater.from(context).inflate(R.layout.layout_keyboard, this, true)
        btn0 = view.findViewById(R.id.btn_0)
        btn1 = view.findViewById(R.id.btn_1)
        btn2 = view.findViewById(R.id.btn_2)
        btn3 = view.findViewById(R.id.btn_3)
        btn4 = view.findViewById(R.id.btn_4)
        btn5 = view.findViewById(R.id.btn_5)
        btn6 = view.findViewById(R.id.btn_6)
        btn7 = view.findViewById(R.id.btn_7)
        btn8 = view.findViewById(R.id.btn_8)
        btn9 = view.findViewById(R.id.btn_9)
        btnPoint = view.findViewById(R.id.btn_point)
        btnDel = view.findViewById(R.id.btn_del)
        btnAc = view.findViewById(R.id.btn_ac)
        btnEqual = view.findViewById(R.id.btn_equal)
        initClick()
    }

    private fun initClick() {
        btn0?.setOnClickListener { clickListener?.invoke(KB_C_0) }
        btn1?.setOnClickListener { clickListener?.invoke(KB_C_1) }
        btn2?.setOnClickListener { clickListener?.invoke(KB_C_2) }
        btn3?.setOnClickListener { clickListener?.invoke(KB_C_3) }
        btn4?.setOnClickListener { clickListener?.invoke(KB_C_4) }
        btn5?.setOnClickListener { clickListener?.invoke(KB_C_5) }
        btn6?.setOnClickListener { clickListener?.invoke(KB_C_6) }
        btn7?.setOnClickListener { clickListener?.invoke(KB_C_7) }
        btn8?.setOnClickListener { clickListener?.invoke(KB_C_8) }
        btn9?.setOnClickListener { clickListener?.invoke(KB_C_9) }
        btnPoint?.setOnClickListener { clickListener?.invoke(KB_C_POINT) }
        btnDel?.setOnClickListener { clickListener?.invoke(KB_C_DEL) }
        btnAc?.setOnClickListener { clickListener?.invoke(KB_C_AC) }
        btnEqual?.setOnClickListener { clickListener?.invoke(KB_C_EQUAL) }
    }

    fun setOnKeyboardClickListener(clickListener: (key: String) -> Unit) {
        this.clickListener = clickListener
    }
}