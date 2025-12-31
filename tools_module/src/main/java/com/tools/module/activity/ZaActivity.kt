package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.utils.JumpTools
import com.tools.module.utils.StatusBarUtil
import com.tools.module.utils.singleClick
import java.math.BigDecimal
import java.math.RoundingMode

/**
 *汇率转换
 */
class ZaActivity : AppCompatActivity() {

    private lateinit var ivClose: ImageView
    private lateinit var etCny: EditText
    private lateinit var etUsd: EditText
    private lateinit var etEur: EditText
    private lateinit var etJpy: EditText
    private lateinit var etKrw: EditText
    private lateinit var etGbp: EditText


    //汇率 -> 1 人民币
    val usd = 0.1385
    val eur = 0.1305
    val jpy = 21.4693
    val krw = 194.7600
    val gbp = 0.1087

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ZaActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        setContentView(R.layout.activity_za)
        JumpTools.mOnListener?.start()
        initView()
        initClick()
    }

    private fun initClick() {
        ivClose.singleClick {finish() }
        etCny.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) {
                    return
                }
                clearZ(1)
                showH(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        etUsd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) {
                    return
                }
                clearZ(2)
                showH(divideScale(p0.toString(), usd.toString(), 4))
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        etEur.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) {
                    return
                }
                clearZ(3)
                showH(divideScale(p0.toString(), eur.toString(), 4))
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        etJpy.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) {
                    return
                }
                clearZ(4)
                showH(divideScale(p0.toString(), jpy.toString(), 4))
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        etKrw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) {
                    return
                }
                clearZ(5)
                showH(divideScale(p0.toString(), krw.toString(), 4))
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
        etGbp.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0.toString().isEmpty()) {
                    return
                }
                clearZ(6)
                showH(divideScale(p0.toString(), gbp.toString(), 4))
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

    }

    //汇率计算，并给到其他货币
    private fun showH(rmb: String) {
        etCny.setHint(multiplyWithScale(rmb, "1", 4))
        etUsd.setHint(multiplyWithScale(rmb, usd.toString(), 4))
        etEur.setHint(multiplyWithScale(rmb, eur.toString(), 4))
        etJpy.setHint(multiplyWithScale(rmb, jpy.toString(), 4))
        etKrw.setHint(multiplyWithScale(rmb, krw.toString(), 4))
        etGbp.setHint(multiplyWithScale(rmb, gbp.toString(), 4))
    }

    //清除以外的字
    private fun clearZ(i: Int) {
        if (i != 1) {
            etCny.setText("")
        }
        if (i != 2) {
            etUsd.setText("")
        }
        if (i != 3) {
            etEur.setText("")
        }
        if (i != 4) {
            etJpy.setText("")
        }
        if (i != 5) {
            etKrw.setText("")
        }
        if (i != 6) {
            etGbp.setText("")
        }
    }


    //乘法
    private fun multiplyWithScale(v1: String, v2: String, scale: Int): String {
        val b1: BigDecimal = BigDecimal(v1)
        val b2: BigDecimal = BigDecimal(v2)
        var result: BigDecimal = b1.multiply(b2)
        result = result.setScale(scale, RoundingMode.HALF_DOWN)
        return result.toString()
    }

    //除法
    private fun divideScale(v1: String, v2: String, scale: Int): String {
        val b1 = BigDecimal(v1)
        val b2 = BigDecimal(v2)
        return b1.divide(b2, scale, RoundingMode.HALF_DOWN).toString()
    }

    private fun initView(){
        ivClose = findViewById(R.id.iv_close)
        etCny = findViewById(R.id.et_cny)
        etUsd = findViewById(R.id.et_usd)
        etEur = findViewById(R.id.et_eur)
        etJpy = findViewById(R.id.et_jpy)
        etKrw = findViewById(R.id.et_krw)
        etGbp = findViewById(R.id.et_gbp)
    }

    override fun onDestroy() {
        super.onDestroy()
        JumpTools.mOnListener?.destroy()
    }
}