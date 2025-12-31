package com.tools.module.utils

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.tools.module.activity.*
import com.tools.module.bean.Constants.TRANS_AREA
import com.tools.module.bean.Constants.TRANS_LENGTH
import com.tools.module.bean.Constants.TRANS_SPEED
import com.tools.module.bean.Constants.TRANS_TEMPERATURE
import com.tools.module.bean.Constants.TRANS_TIME
import com.tools.module.bean.Constants.TRANS_UPPERCASE
import com.tools.module.bean.Constants.TRANS_VOLUME
import com.tools.module.bean.Constants.TRANS_WEIGHT

object JumpTools {

    var mOnListener: ActivityListener? = null

    interface  ActivityListener {
        fun start()
        fun destroy()
    }
    /**
     * 输入关键词直接跳转
     * 如跳到放大镜界面，直接输入 放大镜、放大、镜
     * */
    @RequiresApi(Build.VERSION_CODES.N)
    @JvmStatic
    fun jump(c: Context, s: String,click: ActivityListener? = null){
        mOnListener = null
        mOnListener = click
        if (s.contains("大小写") || s.contains("大小写转换")){
            TransformActivity.start(c,TRANS_UPPERCASE)
        }
        if (s.contains("长度") || s.contains("长度转换")){
            TransformActivity.start(c,TRANS_LENGTH)
        }
        if (s.contains("面积") || s.contains("面积转换")){
            TransformActivity.start(c,TRANS_AREA)
        }
        if (s.contains("体积") || s.contains("体积转换")){
            TransformActivity.start(c,TRANS_VOLUME)
        }
        if (s.contains("温度") || s.contains("温度转换")){
            TransformActivity.start(c,TRANS_TEMPERATURE)
        }
        if (s.contains("速度") || s.contains("速度转换")){
            TransformActivity.start(c,TRANS_SPEED)
        }
        if (s.contains("时间") || s.contains("时间转换")){
            TransformActivity.start(c,TRANS_TIME)
        }
        if (s.contains("重量") || s.contains("重量转换")){
            TransformActivity.start(c,TRANS_WEIGHT)
        }
        if (s.contains("AA") || s.contains("AA收款")){
            AAActivity.start(c)
        }
        if (s.contains("汇率") || s.contains("汇率转换")){
            ZaActivity.start(c)
        }
        if (s.contains("BMI") || s.contains("BMI计算")){
            BmiActivity.start(c)
        }
        if (s.contains("称呼") || s.contains("称呼计算")){
            RelationActivity.start(c)
        }
    }

}