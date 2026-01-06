package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ImageView
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatSeekBar
import cn.tools.module.R
import com.tools.module.bean.Constants
import com.tools.module.dialog.CameraPreDialog
import com.tools.module.dialog.TipsDialog
import com.tools.module.utils.JumpTools
import com.tools.module.utils.SharedPreferencesUtils
import com.tools.module.utils.StatusBarUtil
import com.tools.module.utils.singleClick
import com.tools.module.wright.CameraPreview

/**
 * 工具-放大镜*/
class MagnifierActivity : AppCompatActivity() {

    private lateinit var ivClose: ImageView
    private var cameraView: CameraPreview? = null
    private lateinit var progress: AppCompatSeekBar

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MagnifierActivity::class.java).apply {
                //putExtra("type", type)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        if (CameraPreDialog.checkCameraPermission(this)) {
            creatView()
        } else {
            if (SharedPreferencesUtils.getSPString(this, Constants.NO_CAMERA_PRE).isNotEmpty()) {
                TipsDialog.show(
                    "权限申请",
                    "相机权限已关闭，需获取相机权限用于放大镜功能，是否前往设置打开？",
                    this.supportFragmentManager
                ) {
                    if (it) {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.setData(Uri.parse("package:${this.packageName}"))
                        startActivity(intent)
                    }
                    finish()
                }
            } else {
                CameraPreDialog.show(this.supportFragmentManager) {
                    if (it) {
                        creatView()
                    } else {
                        finish()
                    }
                }
            }
        }
    }

    private fun creatView() {
        setContentView(R.layout.activity_tools_magnifier)
        JumpTools.mOnListener?.start()
        initView()
        initClick()
    }


    private fun initView() {
        ivClose = findViewById(R.id.iv_close)
        cameraView = findViewById(R.id.cameraView)
        progress = findViewById(R.id.progress)

        cameraView?.showCameraPreview()

        val scaleList = cameraView?.initScaleList()
        if (scaleList != null && scaleList.size > 0) {
            progress.max = scaleList.size - 1
        }
        progress.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                val current = seekBar.progress
                cameraView?.setScale(current)
            }
        })

    }

    private fun initClick() {
        ivClose.singleClick {
            finish()
        }
    }

    override fun onStop() {
        super.onStop()
        if (cameraView != null){
            cameraView?.stopCameraPreview()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 9897) {
            CameraPreDialog.dismissAllowingStateLoss()
            // 如果请求被授予
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 权限被授予，执行与摄像头相关的操作
                creatView()
            } else {
                // 权限被拒绝，提示用户
                SharedPreferencesUtils.saveSPString(this, Constants.NO_CAMERA_PRE, "yes")
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        JumpTools.mOnListener?.destroy()
    }
}