package com.tools.module.dialog

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cn.tools.module.R
import com.tools.module.bean.Constants.TRANS_AREA
import com.tools.module.bean.Constants.TRANS_LENGTH
import com.tools.module.bean.Constants.TRANS_SPEED
import com.tools.module.bean.Constants.TRANS_TEMPERATURE
import com.tools.module.bean.Constants.TRANS_TIME
import com.tools.module.bean.Constants.TRANS_VOLUME
import com.tools.module.bean.Constants.TRANS_WEIGHT
import com.tools.module.bean.Constants.UNIT_AREA
import com.tools.module.bean.Constants.UNIT_LENGTH
import com.tools.module.bean.Constants.UNIT_SPEED
import com.tools.module.bean.Constants.UNIT_TEMPERATURE
import com.tools.module.bean.Constants.UNIT_TIME
import com.tools.module.bean.Constants.UNIT_VOLUME
import com.tools.module.bean.Constants.UNIT_WEIGHT
import com.tools.module.bean.TransUnitBean
import com.tools.module.utils.singleClick
import com.tools.module.wheelview.WheelToolsView

/**
 * 单位选择
 */
class UnitPickerDialog(
    private val type: Int,
    private val current: TransUnitBean,
    val callback: (unit: TransUnitBean) -> Unit
) : DialogFragment() {

    private lateinit var wheelView: WheelToolsView
    private lateinit var tvTitle: TextView
    private lateinit var tvCancel: ImageView
    private lateinit var tvConfirm: TextView

    companion object {
        fun show(
            manager: FragmentManager,
            type: Int,
            current: TransUnitBean,
            callback: (unit: TransUnitBean) -> Unit
        ) {
            UnitPickerDialog(type, current, callback).show(manager, "UnitPickerDialog")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialog_unit_picker, container, false)
        wheelView = view.findViewById(R.id.wheel_view)
        tvTitle = view.findViewById(R.id.tv_title)
        tvCancel = view.findViewById(R.id.tv_cancel)
        tvConfirm = view.findViewById(R.id.tv_confirm)

        initView()
        initClick()
        isCancelable = false
        return view
    }


    private fun initView() {
        tvTitle.text = "选择单位"
        wheelView.apply {
            data = when (type) {
                TRANS_LENGTH -> UNIT_LENGTH
                TRANS_AREA -> UNIT_AREA
                TRANS_VOLUME -> UNIT_VOLUME
                TRANS_TEMPERATURE -> UNIT_TEMPERATURE
                TRANS_TIME -> UNIT_TIME
                TRANS_SPEED -> UNIT_SPEED
                TRANS_WEIGHT -> UNIT_WEIGHT
                else -> listOf<TransUnitBean>()
            }
            setDefaultValue(current)
            setFormatter {
                (it as TransUnitBean).run {
                    if (unit.isEmpty()) type else "$type($unit)"
                }
            }
        }
    }

    private fun initClick() {
        tvCancel.singleClick { dismiss() }
        tvConfirm.singleClick {
            callback(wheelView.getCurrentItem())
            dismiss()
        }
    }

    override fun onStart() {
        super.onStart()
        val win = dialog!!.window
        // 一定要设置Background，如果不设置，window属性设置无效
        win!!.setBackgroundDrawableResource(android.R.color.transparent)
        val dm = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(dm)
        val params = win.attributes
        params.gravity = Gravity.BOTTOM
        // 使用ViewGroup.LayoutParams，以便Dialog 宽度充满整个屏幕
        params.width = ViewGroup.LayoutParams.MATCH_PARENT
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT
//        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        win.attributes = params
    }
}