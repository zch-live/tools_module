package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.bean.Constants.NO_CAMERA_PRE
import com.tools.module.dialog.CameraPreDialog
import com.tools.module.dialog.TipsDialog
import com.tools.module.utils.JumpTools
import com.tools.module.utils.SharedPreferencesUtils.getSPString
import com.tools.module.utils.SharedPreferencesUtils.saveSPString
import com.tools.module.utils.StatusBarUtil
import com.tools.module.utils.singleClick
import com.tools.module.wright.CameraPreview
import com.tools.module.wright.RotateDragView

/**
 * 工具-量角器*/
class ProtractorActivity : AppCompatActivity() {

    private lateinit var ivClose: ImageView
    private lateinit var tvSubmit: ImageView
    private lateinit var tvAngle: TextView
    private lateinit var cameraView: CameraPreview
    private lateinit var rotateView: RotateDragView

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, ProtractorActivity::class.java).apply {
                //putExtra("type", type)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        if (CameraPreDialog.checkCameraPermission(this)){
            creatView()
        }else{
            if (getSPString(this,NO_CAMERA_PRE).isNotEmpty()){
                TipsDialog.show("权限申请","相机权限已关闭，需获取相机权限用于量角器功能，是否前往设置打开？",this.supportFragmentManager){
                    if (it){
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        intent.setData(Uri.parse("package:${this.packageName}"))
                        startActivity(intent)
                    }
                    finish()
                }
            }else{
                CameraPreDialog.show(this.supportFragmentManager){
                    if (it){
                        creatView()
                    }else{
                        finish()
                    }
                }
            }
        }
    }

    private fun creatView(){
        setContentView(R.layout.activity_tools_protractor)
        JumpTools.mOnListener?.start()
        initView()
        initClick()
    }

    private fun initClick() {
        ivClose.singleClick {
            finish()
        }
        tvSubmit.singleClick {
            tvSubmit.isSelected = !tvSubmit.isSelected
            if (tvSubmit.isSelected) {
                rotateView.reset()
                tvAngle.text = "90°"
                rotateView.visibility = View.VISIBLE
                tvSubmit.setImageResource(R.mipmap.ic_lock_bg2)
                cameraView.stopCameraPreview()
            } else {
                rotateView.visibility = View.GONE
                tvSubmit.setImageResource(R.mipmap.ic_lock_bg)
                cameraView.showCameraPreview()
            }
        }
        rotateView.callback = {
            val angle = it + 90
            tvAngle.text = "${String.format("%.2f", angle)}°"
        }
    }

    private fun initView() {
        ivClose = findViewById(R.id.iv_close)
        tvSubmit = findViewById(R.id.tvSubmit)
        tvAngle = findViewById(R.id.tvAngle)
        cameraView = findViewById(R.id.cameraView)
        rotateView = findViewById(R.id.rotateView)
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
                saveSPString(this,NO_CAMERA_PRE,"yes")
                finish()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        JumpTools.mOnListener?.destroy()
    }

}