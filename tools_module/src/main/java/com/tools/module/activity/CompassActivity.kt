package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.utils.JumpTools
import com.tools.module.utils.StatusBarUtil
import com.tools.module.utils.singleClick
import kotlin.math.roundToInt

/**
 * 工具-指南针*/
class CompassActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var ivClose: ImageView
    private lateinit var ivCompass: ImageView
    private lateinit var tvCompass1: TextView


    private val sensorManager by lazy { getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    private val accelerometer by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) }
    private val magnetometer by lazy { sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD) }

    private var gravity: FloatArray? = null
    private var geomagnetic: FloatArray? = null

    private var isLock = false

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, CompassActivity::class.java).apply {
            }
            context.startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_UI)
        sensorManager.registerListener(this, magnetometer, SensorManager.SENSOR_DELAY_UI)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        setContentView(R.layout.activity_compass)
        JumpTools.mOnListener?.start()
        initView()
        initClick()
    }

    private fun initView() {
        ivClose = findViewById(R.id.iv_close)
        ivCompass = findViewById(R.id.iv_compass)
        tvCompass1 = findViewById(R.id.tv_compass_1)
    }

    private fun initClick() {
        ivClose.singleClick {
            finish()
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        when (event.sensor.type) {
            Sensor.TYPE_ACCELEROMETER -> gravity = event.values
            Sensor.TYPE_MAGNETIC_FIELD -> geomagnetic = event.values
        }

        if (gravity != null && geomagnetic != null) {
            val R = FloatArray(9)
            val I = FloatArray(9)

            if (SensorManager.getRotationMatrix(R, I, gravity, geomagnetic)) {
                val orientation = FloatArray(3)
                SensorManager.getOrientation(R, orientation)
                val azimuth = Math.toDegrees(orientation[0].toDouble()).toFloat()

                val normalizedAzimuth =
                    if (azimuth < 0) azimuth + 360 else azimuth // 将角度规范为 0° 到 360°
                val roundedAzimuth = normalizedAzimuth.roundToInt()

                if (isLock) return
                tvCompass1.text = "$roundedAzimuth°${getDirectionName(roundedAzimuth)}"
                /*tvCompass2.text = "${getBaguaDirection(roundedAzimuth)}".substring(0, 2)
                tvCompass3.text = "${getBaguaDirection(roundedAzimuth)}".substring(
                    2,
                    getBaguaDirection(roundedAzimuth).length
                )*/
                try {
                    //tvCc.text = "磁场: ${DecimalFormat("0.00").format(geomagnetic!![2])}UT"
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                ivCompass.rotation = -roundedAzimuth.toFloat()
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

    private fun getDirectionName(angle: Int): String {
        return when (angle) {
            in 337..360, in 0..22 -> "北"
            in 23..67 -> "东北"
            in 68..112 -> "东"
            in 113..157 -> "东南"
            in 158..202 -> "南"
            in 203..247 -> "西南"
            in 248..292 -> "西"
            in 293..336 -> "西北"
            else -> "未知"
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        JumpTools.mOnListener?.destroy()
    }

}