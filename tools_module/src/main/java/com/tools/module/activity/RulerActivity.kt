package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.utils.JumpTools
import com.tools.module.utils.StatusBarUtil
import com.tools.module.utils.singleClick

/**
 * 工具-尺子*/
class RulerActivity : AppCompatActivity() {

    private lateinit var ivClose: ImageView

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, RulerActivity::class.java).apply {
                //putExtra("type", type)
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        setContentView(R.layout.activity_ruler)
        JumpTools.mOnListener?.start()
        initView()
        initClick()
    }

    private fun initView() {
        ivClose = findViewById(R.id.iv_close)
    }

    private fun initClick() {
        ivClose.singleClick {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        JumpTools.mOnListener?.destroy()
    }

}