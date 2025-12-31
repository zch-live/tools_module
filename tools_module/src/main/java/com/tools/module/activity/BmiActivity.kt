package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.bean.Constants.KB_C_AC
import com.tools.module.bean.Constants.KB_C_DEL
import com.tools.module.bean.Constants.KB_C_EQUAL
import com.tools.module.dialog.BmiResultDialog
import com.tools.module.utils.*
import com.tools.module.wright.KeyboardView


/**
 * BMI
 */
@RequiresApi(Build.VERSION_CODES.N)
class BmiActivity : AppCompatActivity() {

    private lateinit var inputHelper: InputHelper

    private lateinit var ivClose: ImageView
    private lateinit var ivSexF: ImageView
    private lateinit var ivSexM: ImageView
    private lateinit var v2: View
    private lateinit var v3: View
    private lateinit var keyboard: KeyboardView
    private lateinit var tvHeightInput: TextView
    private lateinit var tvWeightInput: TextView

    private var isMale = true

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, BmiActivity::class.java).apply {
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        setContentView(R.layout.activity_bmi)
        JumpTools.mOnListener?.start()
        initView()
        initClick()
    }


    private fun initClick() {
        ivClose.singleClick {finish() }
        ivSexF.singleClick { switchSex(false) }
        ivSexM.singleClick { switchSex(true) }
        v2.singleClick { inputHelper.switchCurrent(tvHeightInput) }
        v3.singleClick { inputHelper.switchCurrent(tvWeightInput) }
        keyboard.setOnKeyboardClickListener {
            when (it) {
                KB_C_AC -> inputHelper.clear()
                KB_C_DEL -> inputHelper.delete()
                KB_C_EQUAL -> calBmi()
                else -> inputHelper.input(it)
            }
        }
    }


    private fun switchSex(isMale: Boolean) {
        this.isMale = isMale
        ivSexM.setImageResource(if (isMale) R.mipmap.ic_bmi_m_c else R.mipmap.ic_bmi_m_u)
        ivSexF.setImageResource(if (isMale) R.mipmap.ic_bmi_f_u else R.mipmap.ic_bmi_f_c)
    }

    private fun calBmi() {
        try {
            if (inputHelper.hasEmpty()) {
                "请填写完整数据！".toast(this)
                return
            }
            val w = inputHelper.getInputText(tvWeightInput).toDouble()
            val h = inputHelper.getInputText(tvHeightInput).toDouble() / 100f
            val bmi = w / (h * h)
            BmiResultDialog.show(supportFragmentManager, bmi)
        } catch (e: Exception) {
            "输入有误".toast(this)
        }
    }

    private fun initView(){
        ivClose = findViewById(R.id.iv_close)
        ivSexF = findViewById(R.id.iv_sex_f)
        ivSexM = findViewById(R.id.iv_sex_m)
        v2 = findViewById(R.id.v_2)
        v3 = findViewById(R.id.v_3)
        keyboard = findViewById(R.id.keyboard)
        tvHeightInput = findViewById(R.id.tv_height_input)
        tvWeightInput = findViewById(R.id.tv_weight_input)

        inputHelper = InputHelper(
            mapOf(
                tvHeightInput to "如：175",
                tvWeightInput to "如：68"
            ),
            "#000000",
            "#8588A4",
            6
        )
        inputHelper.reset()
    }


    override fun onDestroy() {
        super.onDestroy()
        JumpTools.mOnListener?.destroy()
    }

}