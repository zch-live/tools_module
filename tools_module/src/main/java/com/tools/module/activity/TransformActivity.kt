package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.bean.Constants
import com.tools.module.bean.Constants.TRANS_AREA
import com.tools.module.bean.Constants.TRANS_LENGTH
import com.tools.module.bean.Constants.TRANS_SPEED
import com.tools.module.bean.Constants.TRANS_TEMPERATURE
import com.tools.module.bean.Constants.TRANS_TIME
import com.tools.module.bean.Constants.TRANS_UPPERCASE
import com.tools.module.bean.Constants.TRANS_VOLUME
import com.tools.module.bean.Constants.TRANS_WEIGHT
import com.tools.module.bean.Constants.UNIT_AREA
import com.tools.module.bean.Constants.UNIT_LENGTH
import com.tools.module.bean.Constants.UNIT_SPEED
import com.tools.module.bean.Constants.UNIT_TEMPERATURE
import com.tools.module.bean.Constants.UNIT_TIME
import com.tools.module.bean.Constants.UNIT_UPPERCASE
import com.tools.module.bean.Constants.UNIT_VOLUME
import com.tools.module.bean.Constants.UNIT_WEIGHT
import com.tools.module.bean.TransUnitBean
import com.tools.module.dialog.UnitPickerDialog
import com.tools.module.utils.*
import com.tools.module.utils.JumpTools.mOnListener
import com.tools.module.wright.KeyboardView
import kotlin.random.Random

/**
 *转换公共
 */
class TransformActivity : AppCompatActivity() {

    private lateinit var inputHelper: InputHelper

    private var type = TRANS_UPPERCASE

    private lateinit var unit1: TransUnitBean

    private lateinit var unit2: TransUnitBean

    private lateinit var tvTitle: TextView
    private lateinit var ivClose: ImageView
    private lateinit var tvInput1: TextView
    private lateinit var tvInput2: TextView
    private lateinit var keyboard: KeyboardView
    private lateinit var tvUnit1: TextView
    private lateinit var tvUnit2: TextView
    private lateinit var tvLabel1: TextView
    private lateinit var tvLabel2: TextView
    private lateinit var v1: View
    private lateinit var v2: View

    companion object {
        fun start(context: Context, type: Int) {
            val intent = Intent(context, TransformActivity::class.java).apply {
                putExtra("type", type)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        setContentView(R.layout.activity_transform)
        initView()
        mOnListener?.start()
        type = intent.getIntExtra("type", type)
        unit1 = toInitUnit(0)
        unit2 = toInitUnit(1)
        inputHelper = InputHelper(
            mapOf(
                tvInput1 to if (type != TRANS_UPPERCASE) {
                    "如：${Random.nextInt(1, 100)}"
                } else "如：100",
                tvInput2 to if (type != TRANS_UPPERCASE) {
                    "如：${Random.nextInt(1000, 10000)}"
                } else "壹佰元整"
            ),
            "#000000",
            "#8588A4",
            when (type) {
                TRANS_UPPERCASE -> 12
                else -> 20
            }
        )
        inputHelper.reset()
        updateUnit()
        tvTitle.text = "${toTitle()}单位转换"
        keyboard.setOnKeyboardClickListener { k ->
            when (k) {
                Constants.KB_C_AC -> inputHelper.clear()
                Constants.KB_C_DEL -> inputHelper.delete()
                Constants.KB_C_EQUAL -> doTrans()
                else -> inputHelper.input(k)
            }
        }
        tvUnit1.showOrHide(type != TRANS_UPPERCASE)
        tvUnit2.showOrHide(type != TRANS_UPPERCASE)

        initClick()
    }

    private fun initClick() {
        v1.singleClick { switchInput(1) }
        v2.singleClick { switchInput(2) }
        tvUnit1.singleClick { pickUnit(1) }
        tvUnit2.singleClick { pickUnit(2) }
        ivClose.singleClick { finish() }
    }

    private fun switchInput(index: Int) {
        if (type == TRANS_UPPERCASE) return
        if (inputHelper.allNotEmpty()) {
            inputHelper.reset(if (index == 1) tvInput1 else tvInput2)
        } else if (inputHelper.isNotEmpty(if (index == 1) tvInput2 else tvInput1)) {
            "另一栏已有数据，若要输入这栏，清先清空".toast(this)
        } else {
            inputHelper.switchCurrent(if (index == 1) tvInput1 else tvInput2)
        }
    }

    /**
     *  刷新数据
     *  在这里更新单位和标签
     */
    private fun updateUnit() {
        tvLabel1.text = "*" + if (type != TRANS_UPPERCASE) "${unit1.type} (${unit1.unit})" else unit1.type
        tvLabel2.text = "*" + if (type != TRANS_UPPERCASE) "${unit2.type} (${unit2.unit})" else unit2.type
    }

    private fun pickUnit(index: Int) {
        UnitPickerDialog.show(supportFragmentManager, type, if (index == 1) unit1 else unit2) {
            if (inputHelper.allNotEmpty()) inputHelper.reset()
            if (index == 1) unit1 = it else unit2 = it
            updateUnit()
        }
    }

    private fun doTrans() {
        try {
            if (inputHelper.allEmpty()) {
                "请在任意一输入栏里输入数字".toast(this)
                return
            }
            val isUp2Down = inputHelper.isCurrent(tvInput1)
            val v =
                inputHelper.getInputText(if (isUp2Down) tvInput1 else tvInput2)
                    .toDouble()
            val r = when (type) {
                TRANS_UPPERCASE -> TransformUtils.transUppercase(v)
                else -> TransformUtils.transValue(
                    v,
                    if (isUp2Down) unit1 else unit2,
                    if (isUp2Down) unit2 else unit1
                ).toString()
            }
            inputHelper.updateTvText(if (isUp2Down) tvInput2 else tvInput1, r)
        } catch (e: Exception) {
            "输入有误".toast(this)
        }
    }

    private fun toTitle() =
        when (type) {
            TRANS_UPPERCASE -> "大写"
            TRANS_LENGTH -> "长度"
            TRANS_AREA -> "面积"
            TRANS_VOLUME -> "体积"
            TRANS_TEMPERATURE -> "温度"
            TRANS_TIME -> "时间"
            TRANS_SPEED -> "速度"
            TRANS_WEIGHT -> "重量"
            else -> ""
        }

    private fun toInitUnit(index: Int) = when (type) {
        TRANS_UPPERCASE -> UNIT_UPPERCASE[index]
        TRANS_LENGTH -> UNIT_LENGTH[index]
        TRANS_AREA -> UNIT_AREA[index]
        TRANS_VOLUME -> UNIT_VOLUME[index]
        TRANS_TEMPERATURE -> UNIT_TEMPERATURE[index]
        TRANS_TIME -> UNIT_TIME[index]
        TRANS_SPEED -> UNIT_SPEED[index]
        TRANS_WEIGHT -> UNIT_WEIGHT[index]
        else -> TransUnitBean("", "", 0.0)
    }

    private fun initView() {
        tvTitle = findViewById(R.id.tv_title)
        ivClose = findViewById(R.id.iv_close)
        tvInput1 = findViewById(R.id.tv_input_1)
        tvInput2 = findViewById(R.id.tv_input_2)
        keyboard = findViewById(R.id.keyboard)
        tvUnit1 = findViewById(R.id.tv_unit_1)
        tvUnit2 = findViewById(R.id.tv_unit_2)
        tvLabel1 = findViewById(R.id.tv_label_1)
        tvLabel2 = findViewById(R.id.tv_label_2)
        v1 = findViewById(R.id.v_1)
        v2 = findViewById(R.id.v_2)
    }

    override fun onDestroy() {
        super.onDestroy()
        mOnListener?.destroy()
    }
}