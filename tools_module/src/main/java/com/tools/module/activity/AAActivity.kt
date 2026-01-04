package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.bean.Constants.KB_C_AC
import com.tools.module.bean.Constants.KB_C_DEL
import com.tools.module.bean.Constants.KB_C_EQUAL
import com.tools.module.bean.Constants.KB_C_POINT
import com.tools.module.utils.*
import com.tools.module.wright.KeyboardView
import kotlin.math.max


/**
 * AA收款
 */
@RequiresApi(Build.VERSION_CODES.N)
class AAActivity : AppCompatActivity() {

    private lateinit var inputHelper: InputHelper

    private lateinit var v1: View
    private lateinit var v2: View
    private lateinit var tvMoneyInput: TextView
    private lateinit var tvPeopleInput: TextView
    private lateinit var keyboard: KeyboardView
    private lateinit var tvResultInput: TextView
    private lateinit var ivClose: ImageView

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, AAActivity::class.java).apply {
                //putExtra("type", type)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        setContentView(R.layout.activity_aa)
        JumpTools.mOnListener?.start()
        initView()
        initClick()
    }


    private fun initClick() {
        ivClose.singleClick {finish() }
        v1.singleClick { inputHelper.switchCurrent(tvMoneyInput) }
        v2.singleClick { inputHelper.switchCurrent(tvPeopleInput) }
        keyboard.setOnKeyboardClickListener {
            when {
                it == KB_C_AC -> inputHelper.clear()
                it == KB_C_DEL -> inputHelper.delete()
                it == KB_C_EQUAL -> calAA()
                it == KB_C_POINT && inputHelper.isCurrent(tvPeopleInput) -> Unit
                else -> inputHelper.input(it)
            }
        }
    }


    private fun calAA() {
        try {
            val m = inputHelper.getInputText(tvMoneyInput).toDouble()
            val n = inputHelper.getInputText(tvPeopleInput).toDouble()
            val r = max(0.01, m / n)
            tvResultInput.text = DecimalFormat("0.00#").format(r)
        } catch (e: Exception) {
            "输入有误".toast(this@AAActivity)
        }
    }

    private fun initView(){
        v1 = findViewById(R.id.v_1)
        v2 = findViewById(R.id.v_2)
        tvMoneyInput = findViewById(R.id.tv_money_input)
        tvPeopleInput = findViewById(R.id.tv_people_input)
        keyboard = findViewById(R.id.keyboard)
        ivClose = findViewById(R.id.iv_close)
        tvResultInput = findViewById(R.id.tv_result_input)

        inputHelper = InputHelper(
            mapOf(
                tvMoneyInput to "请输入金额",
                tvPeopleInput to "请输入人数"
            ),
            "#000000",
            "#8588A4",
            10
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        JumpTools.mOnListener?.destroy()
    }
}