package com.tools.module.dialog

import android.icu.text.DecimalFormat
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cn.tools.module.R
import com.tools.module.utils.singleClick

@RequiresApi(Build.VERSION_CODES.N)
class BmiResultDialog(val result: Double) : DialogFragment() {

    private lateinit var tvConfirm: TextView
    private lateinit var tvResult1: TextView
    private lateinit var tvResult2: TextView

    companion object {
        fun show(manager: FragmentManager, result: Double) {
            BmiResultDialog(result).show(manager, "BmiResultDialog")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_bmi_result, container, false)
        tvResult1 = view.findViewById(R.id.tv_result_1)
        tvResult2 = view.findViewById(R.id.tv_result_2)
        tvConfirm = view.findViewById(R.id.tv_confirm)

        initView()
        initClick()
        isCancelable = false
        return view
    }

    private fun initView() {
        tvResult1.text = DecimalFormat("0.0").format(result)
        tvResult2.text = "该得分为:" + when {
            result < 18.5 -> "偏低"
            result < 24 -> "正常"
            result < 28 -> "偏胖"
            else -> "肥胖"
        }
    }

    private fun initClick() {
        tvConfirm.singleClick { dismiss() }
    }

    override fun onStart() {
        super.onStart()
        val win = dialog?.window
        win?.setBackgroundDrawableResource(android.R.color.transparent)
        val params = win?.attributes?.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.WRAP_CONTENT
            gravity = Gravity.CENTER
        }
        win?.attributes = params
    }
}