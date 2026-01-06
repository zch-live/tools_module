package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraManager
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.utils.FlashlightUtils
import com.tools.module.utils.JumpTools
import com.tools.module.utils.StatusBarUtil
import com.tools.module.utils.singleClick

/**
 * 工具-手电筒*/
class LightActivity : AppCompatActivity() {

    private lateinit var ivClose: ImageView
    private lateinit var ivCompass: ImageView

    var type = 0

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, LightActivity::class.java).apply {
                //putExtra("type", type)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        setContentView(R.layout.activity_light)
        JumpTools.mOnListener?.start()
        initView()
        initClick()
    }

    private fun initView() {
        ivClose = findViewById(R.id.iv_close)
        ivCompass = findViewById(R.id.iv_compass)
    }

    private fun initClick() {
        ivClose.singleClick {
            finish()
        }
        ivCompass.setOnClickListener {
            val b = !ivCompass.isSelected
            val showFlash = showFlash(b)
            if (showFlash) {
                ivCompass.isSelected = b
                if (b) {
                    ivCompass.setImageResource(R.mipmap.ic_light2)
                } else {
                    ivCompass.setImageResource(R.mipmap.ic_light1)
                }
            }
        }
    }

    private fun showFlash(show: Boolean): Boolean {
        if (!flashLightAvailable()) return false
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                val manager = getSystemService(CAMERA_SERVICE) as CameraManager
                manager.setTorchMode("0", show)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
                return false
            }
        } else {
            FlashlightUtils.setFlashlightStatus(show)
        }
        return true
    }

    private fun flashLightAvailable(): Boolean {
        return packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)
    }

    override fun onDestroy() {
        super.onDestroy()
        showFlash(false)
        JumpTools.mOnListener?.destroy()
    }

}