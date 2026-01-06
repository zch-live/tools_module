package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.bean.Constants.CAL_TYPE_0
import com.tools.module.bean.Constants.CAL_TYPE_1
import com.tools.module.bean.Constants.CAL_TYPE_1X
import com.tools.module.bean.Constants.CAL_TYPE_2
import com.tools.module.bean.Constants.CAL_TYPE_3
import com.tools.module.bean.Constants.CAL_TYPE_4
import com.tools.module.bean.Constants.CAL_TYPE_5
import com.tools.module.bean.Constants.CAL_TYPE_6
import com.tools.module.bean.Constants.CAL_TYPE_7
import com.tools.module.bean.Constants.CAL_TYPE_8
import com.tools.module.bean.Constants.CAL_TYPE_9
import com.tools.module.bean.Constants.CAL_TYPE_AC
import com.tools.module.bean.Constants.CAL_TYPE_ADD
import com.tools.module.bean.Constants.CAL_TYPE_ANGLE
import com.tools.module.bean.Constants.CAL_TYPE_BRACKETS1
import com.tools.module.bean.Constants.CAL_TYPE_BRACKETS2
import com.tools.module.bean.Constants.CAL_TYPE_CHANGE
import com.tools.module.bean.Constants.CAL_TYPE_COS
import com.tools.module.bean.Constants.CAL_TYPE_DEL
import com.tools.module.bean.Constants.CAL_TYPE_DIVIDE
import com.tools.module.bean.Constants.CAL_TYPE_E
import com.tools.module.bean.Constants.CAL_TYPE_EX
import com.tools.module.bean.Constants.CAL_TYPE_LN
import com.tools.module.bean.Constants.CAL_TYPE_LOG
import com.tools.module.bean.Constants.CAL_TYPE_MULTIPLY
import com.tools.module.bean.Constants.CAL_TYPE_PERCENT
import com.tools.module.bean.Constants.CAL_TYPE_PI
import com.tools.module.bean.Constants.CAL_TYPE_POINT
import com.tools.module.bean.Constants.CAL_TYPE_RESULT
import com.tools.module.bean.Constants.CAL_TYPE_SIN
import com.tools.module.bean.Constants.CAL_TYPE_SQUARE
import com.tools.module.bean.Constants.CAL_TYPE_SUBTRACT
import com.tools.module.bean.Constants.CAL_TYPE_TAN
import com.tools.module.bean.Constants.CAL_TYPE_X2
import com.tools.module.bean.Constants.CAL_TYPE_XY
import com.tools.module.utils.*

/**
 * 工具-计算器*/
class JSQActivity : AppCompatActivity() {

    private lateinit var ivClose: ImageView
    private lateinit var ivExpand: ImageView
    private lateinit var clKeyboard: FrameLayout
    private lateinit var btnChange: ImageView
    private lateinit var btnAc: ImageView
    private lateinit var btnDelete: ImageView
    private lateinit var btn1: ImageView
    private lateinit var btn2: ImageView
    private lateinit var btn3: ImageView
    private lateinit var btn4: ImageView
    private lateinit var btn5: ImageView
    private lateinit var btn6: ImageView
    private lateinit var btn7: ImageView
    private lateinit var btn8: ImageView
    private lateinit var btn9: ImageView
    private lateinit var btn0: ImageView
    private lateinit var btnPoint: ImageView
    private lateinit var btnAdd: ImageView
    private lateinit var btnSubtract: ImageView
    private lateinit var btnMultiply: ImageView
    private lateinit var btnDivide: ImageView
    private lateinit var btnPercent: ImageView
    private lateinit var btnEqual: ImageView
    private lateinit var btnSChange: ImageView
    private lateinit var btnSAc: ImageView
    private lateinit var btnSDelete: ImageView
    private lateinit var btnS1: ImageView
    private lateinit var btnS2: ImageView
    private lateinit var btnS3: ImageView
    private lateinit var btnS4: ImageView
    private lateinit var btnS5: ImageView
    private lateinit var btnS6: ImageView
    private lateinit var btnS7: ImageView
    private lateinit var btnS8: ImageView
    private lateinit var btnS9: ImageView
    private lateinit var btnS0: ImageView
    private lateinit var btnSE: ImageView
    private lateinit var btnSPi: ImageView
    private lateinit var btnSPoint: ImageView
    private lateinit var btnS1x: ImageView
    private lateinit var btnSEX: ImageView
    private lateinit var btnSSquare: ImageView
    private lateinit var btnSX2: ImageView
    private lateinit var btnSAngle: ImageView
    private lateinit var btnSSin: ImageView
    private lateinit var btnSCos: ImageView
    private lateinit var btnSTan: ImageView
    private lateinit var btnSXY: ImageView
    private lateinit var btnSLog: ImageView
    private lateinit var btnSLn: ImageView
    private lateinit var btnSBrackets1: ImageView
    private lateinit var btnSBrackets2: ImageView
    private lateinit var btnSAdd: ImageView
    private lateinit var btnSSubtract: ImageView
    private lateinit var btnSMultiply: ImageView
    private lateinit var btnSDivide: ImageView
    private lateinit var btnSPercent: ImageView
    private lateinit var btnSEqual: ImageView
    private lateinit var glCal: GridLayout
    private lateinit var glCalS: GridLayout
    private lateinit var tvInput: TextView
    private lateinit var tvResult: TextView
    private lateinit var tvHis: TextView

    private var expandCal = false
    private var expandResult = false
    private var countTimes = 0

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, JSQActivity::class.java).apply {
                //putExtra("type", type)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        setContentView(R.layout.activity_jsq)
        JumpTools.mOnListener?.start()
        initView()
        initClick()
    }

    private fun initView() {
        ivClose = findViewById(R.id.iv_close)
        ivExpand = findViewById(R.id.iv_expand)
        clKeyboard = findViewById(R.id.cl_keyboard)
        btnChange = findViewById(R.id.btn_change)
        btnAc = findViewById(R.id.btn_ac)
        btnDelete = findViewById(R.id.btn_delete)
        btn1 = findViewById(R.id.btn_1)
        btn2 = findViewById(R.id.btn_2)
        btn3 = findViewById(R.id.btn_3)
        btn4 = findViewById(R.id.btn_4)
        btn5 = findViewById(R.id.btn_5)
        btn6 = findViewById(R.id.btn_6)
        btn7 = findViewById(R.id.btn_7)
        btn8 = findViewById(R.id.btn_8)
        btn9 = findViewById(R.id.btn_9)
        btn0 = findViewById(R.id.btn_0)
        btnPoint = findViewById(R.id.btn_point)
        btnAdd = findViewById(R.id.btn_add)
        btnSubtract = findViewById(R.id.btn_subtract)
        btnMultiply = findViewById(R.id.btn_multiply)
        btnDivide = findViewById(R.id.btn_divide)
        btnPercent = findViewById(R.id.btn_percent)
        btnEqual = findViewById(R.id.btn_equal)
        btnSChange = findViewById(R.id.btn_s_change)
        btnSAc = findViewById(R.id.btn_s_ac)
        btnSDelete = findViewById(R.id.btn_s_delete)
        btnS1 = findViewById(R.id.btn_s_1)
        btnS2 = findViewById(R.id.btn_s_2)
        btnS3 = findViewById(R.id.btn_s_3)
        btnS4 = findViewById(R.id.btn_s_4)
        btnS5 = findViewById(R.id.btn_s_5)
        btnS6 = findViewById(R.id.btn_s_6)
        btnS7 = findViewById(R.id.btn_s_7)
        btnS8 = findViewById(R.id.btn_s_8)
        btnS9 = findViewById(R.id.btn_s_9)
        btnS0 = findViewById(R.id.btn_s_0)
        btnSE = findViewById(R.id.btn_s_e)
        btnSPi = findViewById(R.id.btn_s_pi)
        btnSPoint = findViewById(R.id.btn_s_point)
        btnS1x = findViewById(R.id.btn_s_1x)
        btnSEX = findViewById(R.id.btn_s_e_x)
        btnSSquare = findViewById(R.id.btn_s_square)
        btnSX2 = findViewById(R.id.btn_s_x_2)
        btnSAngle = findViewById(R.id.btn_s_angle)
        btnSSin = findViewById(R.id.btn_s_sin)
        btnSCos = findViewById(R.id.btn_s_cos)
        btnSTan = findViewById(R.id.btn_s_tan)
        btnSXY = findViewById(R.id.btn_s_x_y)
        btnSLog = findViewById(R.id.btn_s_log)
        btnSLn = findViewById(R.id.btn_s_ln)
        btnSBrackets1 = findViewById(R.id.btn_s_brackets1)
        btnSBrackets2 = findViewById(R.id.btn_s_brackets2)
        btnSAdd = findViewById(R.id.btn_s_add)
        btnSSubtract = findViewById(R.id.btn_s_subtract)
        btnSMultiply = findViewById(R.id.btn_s_multiply)
        btnSDivide = findViewById(R.id.btn_s_divide)
        btnSPercent = findViewById(R.id.btn_s_percent)
        btnSEqual = findViewById(R.id.btn_s_equal)
        glCal = findViewById(R.id.gl_cal)
        glCalS = findViewById(R.id.gl_cal_s)
        tvInput = findViewById(R.id.tv_input)
        tvResult = findViewById(R.id.tv_result)
        tvHis = findViewById(R.id.tv_his)
    }

    private fun initClick() {
        ivClose.singleClick {
            finish()
        }
        ivExpand.singleClick {
            expandResult = !expandResult
            clKeyboard.showOrHide(!expandResult)
        }
        btnChange.singleClick { doInput(CAL_TYPE_CHANGE) }
        btnAc.setOnClickListener { doInput(CAL_TYPE_AC) }
        btnDelete.setOnClickListener { doInput(CAL_TYPE_DEL) }
        btn1.setOnClickListener { doInput(CAL_TYPE_1) }
        btn2.setOnClickListener { doInput(CAL_TYPE_2) }
        btn3.setOnClickListener { doInput(CAL_TYPE_3) }
        btn4.setOnClickListener { doInput(CAL_TYPE_4) }
        btn5.setOnClickListener { doInput(CAL_TYPE_5) }
        btn6.setOnClickListener { doInput(CAL_TYPE_6) }
        btn7.setOnClickListener { doInput(CAL_TYPE_7) }
        btn8.setOnClickListener { doInput(CAL_TYPE_8) }
        btn9.setOnClickListener { doInput(CAL_TYPE_9) }
        btn0.setOnClickListener { doInput(CAL_TYPE_0) }
        btnPoint.setOnClickListener { doInput(CAL_TYPE_POINT) }
        btnAdd.setOnClickListener { doInput(CAL_TYPE_ADD) }
        btnSubtract.setOnClickListener { doInput(CAL_TYPE_SUBTRACT) }
        btnMultiply.setOnClickListener { doInput(CAL_TYPE_MULTIPLY) }
        btnDivide.setOnClickListener { doInput(CAL_TYPE_DIVIDE) }
        btnPercent.setOnClickListener { doInput(CAL_TYPE_PERCENT) }
        btnEqual.setOnClickListener { doInput(CAL_TYPE_RESULT) }


        btnSChange.singleClick { doInput(CAL_TYPE_CHANGE) }
        btnSAc.setOnClickListener { doInput(CAL_TYPE_AC) }
        btnSDelete.setOnClickListener { doInput(CAL_TYPE_DEL) }
        btnS1.setOnClickListener { doInput(CAL_TYPE_1) }
        btnS2.setOnClickListener { doInput(CAL_TYPE_2) }
        btnS3.setOnClickListener { doInput(CAL_TYPE_3) }
        btnS4.setOnClickListener { doInput(CAL_TYPE_4) }
        btnS5.setOnClickListener { doInput(CAL_TYPE_5) }
        btnS6.setOnClickListener { doInput(CAL_TYPE_6) }
        btnS7.setOnClickListener { doInput(CAL_TYPE_7) }
        btnS8.setOnClickListener { doInput(CAL_TYPE_8) }
        btnS9.setOnClickListener { doInput(CAL_TYPE_9) }
        btnS0.setOnClickListener { doInput(CAL_TYPE_0) }
        btnSE.setOnClickListener { doInput(CAL_TYPE_E) }
        btnSPi.setOnClickListener { doInput(CAL_TYPE_PI) }
        btnSPoint.setOnClickListener { doInput(CAL_TYPE_POINT) }
        btnS1x.setOnClickListener { doInput(CAL_TYPE_1X) }
        btnSEX.setOnClickListener { doInput(CAL_TYPE_EX) }
        btnSSquare.setOnClickListener { doInput(CAL_TYPE_SQUARE) }
        btnSX2.setOnClickListener { doInput(CAL_TYPE_X2) }
        btnSAngle.setOnClickListener { doInput(CAL_TYPE_ANGLE) }
        btnSSin.setOnClickListener { doInput(CAL_TYPE_SIN) }
        btnSCos.setOnClickListener { doInput(CAL_TYPE_COS) }
        btnSTan.setOnClickListener { doInput(CAL_TYPE_TAN) }
        btnSXY.setOnClickListener { doInput(CAL_TYPE_XY) }
        btnSLog.setOnClickListener { doInput(CAL_TYPE_LOG) }
        btnSLn.setOnClickListener { doInput(CAL_TYPE_LN) }
        btnSBrackets1.setOnClickListener { doInput(CAL_TYPE_BRACKETS1) }
        btnSBrackets2.setOnClickListener { doInput(CAL_TYPE_BRACKETS2) }
        btnSAdd.setOnClickListener { doInput(CAL_TYPE_ADD) }
        btnSSubtract.setOnClickListener { doInput(CAL_TYPE_SUBTRACT) }
        btnSMultiply.setOnClickListener { doInput(CAL_TYPE_MULTIPLY) }
        btnSDivide.setOnClickListener { doInput(CAL_TYPE_DIVIDE) }
        btnSPercent.setOnClickListener { doInput(CAL_TYPE_PERCENT) }
        btnSEqual.setOnClickListener { doInput(CAL_TYPE_RESULT) }

    }

    private fun exchangeType() {
        expandCal = !expandCal
        doInput(CAL_TYPE_AC)
        glCal.showOrHide(!expandCal)
        glCalS.showOrHide(expandCal)
    }

    private fun doInput(input: String) {
        when (input) {
            CAL_TYPE_AC -> {
                CalculatorUtils.reset()
                tvInput.text = "0"
                tvResult.text = "0"
            }

            CAL_TYPE_DEL -> {
                tvInput.text = CalculatorUtils.delete().ifEmpty { "0" }
            }

            CAL_TYPE_RESULT -> {
                countTimes++
                if (countTimes == 1){} /*EventBus.getDefault().post(HomeAliSingGetEvent())*/
                val result = CalculatorUtils.calculate()
                val s = "${CalculatorUtils.getInput()}=${result}"
                tvInput.text = s
                tvResult.text = result
                tvHis.text = "${tvHis.text}\n$s"
            }

            CAL_TYPE_CHANGE -> {
                exchangeType()
            }

            CAL_TYPE_1X, CAL_TYPE_EX, CAL_TYPE_SQUARE, CAL_TYPE_SIN, CAL_TYPE_COS, CAL_TYPE_TAN, CAL_TYPE_LOG, CAL_TYPE_LN -> {
                if (CalculatorUtils.getInput().isEmpty() || CalculatorUtils.hasCalculated()) {
                    tvInput.text = CalculatorUtils.add(input)
                }
            }

            CAL_TYPE_X2, CAL_TYPE_XY -> {
                if (CalculatorUtils.getInput()
                        .isNotEmpty() || CalculatorUtils.hasCalculated()
                ) {
                    tvInput.text = CalculatorUtils.add(input)
                }
            }

            else -> {
                tvInput.text = CalculatorUtils.add(input)
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        JumpTools.mOnListener?.destroy()
    }

}