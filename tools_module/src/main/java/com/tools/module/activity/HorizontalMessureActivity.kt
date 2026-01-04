package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.utils.JumpTools
import com.tools.module.utils.StatusBarUtil
import com.tools.module.utils.singleClick
import kotlin.math.atan2
import kotlin.math.sqrt

/**
 * 工具-水平仪*/
@RequiresApi(Build.VERSION_CODES.M)
class HorizontalMessureActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var ivClose: ImageView
    private lateinit var ivBg1: ImageView
    private lateinit var ivBg2: ImageView
    private lateinit var ivBg3: ImageView
    private lateinit var ivBall1: ImageView
    private lateinit var ivBall2: ImageView
    private lateinit var ivBall3: ImageView
    private lateinit var tv1: TextView
    private lateinit var tv2: TextView


    private val sensorManager by lazy { getSystemService(SensorManager::class.java) }
    private val accelerometer by lazy { sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }

    private var per1 = 0
    private var per2 = 0
    private var per3 = 0

    private val alpha = 0.25f
    private var filteredX = 0f
    private var filteredY = 0f
    private var filteredZ = 0f


    companion object {
        fun start(context: Context) {
            val intent = Intent(context, HorizontalMessureActivity::class.java).apply {
                //putExtra("type", type)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        setContentView(R.layout.fragment_hor)
        JumpTools.mOnListener?.start()
        initView()
        initClick()
    }

    private fun initClick() {
        ivClose.singleClick {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager?.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
            // 滤波
            filteredX += alpha * (event.values[0] - filteredX)
            filteredY += alpha * (event.values[1] - filteredY)
            filteredZ += alpha * (event.values[2] - filteredZ)

            val x = filteredX
            val y = filteredY
            val z = filteredZ


            // 水平
            val xAngle =
                Math.toDegrees(atan2(x.toDouble(), sqrt(y * y + z * z).toDouble())).toFloat()
            // 垂直
            val yAngle =
                Math.toDegrees(atan2(y.toDouble(), sqrt(x * x + z * z).toDouble())).toFloat()


            ivBall1.translationX = per1 * -xAngle
            ivBall2.translationY = per2 * yAngle
            ivBall3.translationX = per3 * -xAngle
            ivBall3.translationY = per3 * yAngle

            tv1.text = "水平\n" + "${xAngle.toInt()}°"
            tv2.text = "垂直\n" + "${yAngle.toInt()}°"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    private fun initView(){
        ivClose = findViewById(R.id.iv_close)
        ivBg1 = findViewById(R.id.iv_bg_1)
        ivBg2 = findViewById(R.id.iv_bg_2)
        ivBg3 = findViewById(R.id.iv_bg_3)
        ivBall1 = findViewById(R.id.iv_ball_1)
        ivBall2 = findViewById(R.id.iv_ball_2)
        ivBall3 = findViewById(R.id.iv_ball_3)
        tv1 = findViewById(R.id.tv_1)
        tv2 = findViewById(R.id.tv_2)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            per1 = ivBg1.width / 180
            per2 = ivBg2.height / 180
            per3 = ivBg3.width / 180
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        JumpTools.mOnListener?.destroy()
    }
}
