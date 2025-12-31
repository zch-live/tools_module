package com.tools.home

import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.tools.module.utils.JumpTools
import com.tools.module.utils.toast

@RequiresApi(Build.VERSION_CODES.N)
class MainActivity : AppCompatActivity() {

    var btn1: Button? = null
    var btn2: Button? = null
    var btn3: Button? = null
    var btn4: Button? = null
    var btn5: Button? = null
    var btn6: Button? = null
    var btn7: Button? = null
    var btn8: Button? = null
    var btn9: Button? = null
    var btn10: Button? = null
    var btn11: Button? = null
    var btn12: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn1 = findViewById(R.id.btn1)
        btn2 = findViewById(R.id.btn2)
        btn3 = findViewById(R.id.btn3)
        btn4 = findViewById(R.id.btn4)
        btn5 = findViewById(R.id.btn5)
        btn6 = findViewById(R.id.btn6)
        btn7 = findViewById(R.id.btn7)
        btn8 = findViewById(R.id.btn8)
        btn9 = findViewById(R.id.btn9)
        btn10 = findViewById(R.id.btn10)
        btn11 = findViewById(R.id.btn11)
        btn12 = findViewById(R.id.btn12)

        setOnClick()
    }

    private fun setOnClick() {
        btn1!!.setOnClickListener {
            JumpTools.jump(this@MainActivity, "大小写", object : JumpTools.ActivityListener {
                override fun start() {
                    "界面打开".toast(this@MainActivity)
                }

                override fun destroy() {
                    "界面关闭".toast(this@MainActivity)
                }
            })
        }
        btn2!!.setOnClickListener { JumpTools.jump(this@MainActivity, "长度转换") }
        btn3!!.setOnClickListener { JumpTools.jump(this@MainActivity, "面积转换") }
        btn4!!.setOnClickListener { JumpTools.jump(this@MainActivity, "体积转换") }
        btn5!!.setOnClickListener { JumpTools.jump(this@MainActivity, "温度") }
        btn6!!.setOnClickListener {
            JumpTools.jump(this@MainActivity, "速度", object : JumpTools.ActivityListener {
                override fun start() {
                    "界面打开".toast(this@MainActivity)
                }

                override fun destroy() {
                    "界面关闭".toast(this@MainActivity)
                }
            })
        }

        btn7!!.setOnClickListener { JumpTools.jump(this@MainActivity, "时间") }
        btn8!!.setOnClickListener { JumpTools.jump(this@MainActivity, "重量转换") }
        btn9!!.setOnClickListener { JumpTools.jump(this@MainActivity, "AA") }
        btn10!!.setOnClickListener { JumpTools.jump(this@MainActivity, "汇率") }
        btn11!!.setOnClickListener { JumpTools.jump(this@MainActivity, "BMI计算") }
        btn12!!.setOnClickListener { JumpTools.jump(this@MainActivity, "称呼", object : JumpTools.ActivityListener {
            override fun start() {
                "界面打开".toast(this@MainActivity)
            }

            override fun destroy() {
                "界面关闭".toast(this@MainActivity)
            }
        }) }
    }

}