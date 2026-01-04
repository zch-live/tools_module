package com.tools.module.dialog

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cn.tools.module.R
import com.tools.module.utils.singleClick

/**
 *通用弹窗
 */
class TipsDialog(val mT: String, val mC: String, private val callback: (isGet: Boolean) -> Unit) :
    DialogFragment() {

    private lateinit var titleLabel: TextView
    private lateinit var tvMsg: TextView
    private lateinit var tvCancel: TextView
    private lateinit var tvOk: TextView

    companion object {
        fun show(
            mT: String,
            mC: String,
            manager: FragmentManager,
            callback: (isGet: Boolean) -> Unit
        ) {
            TipsDialog(mT, mC, callback).show(
                manager,
                "TipsDialog"
            )
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialong_tips, container, false)
        titleLabel = view.findViewById(R.id.title_label)
        tvMsg = view.findViewById(R.id.tv_msg)
        tvCancel = view.findViewById(R.id.tv_cancel)
        tvOk = view.findViewById(R.id.tv_ok)

        titleLabel.text = mT
        tvMsg.text = mC
        initClick()
        isCancelable = false
        return view
    }

    private fun initClick() {
        tvCancel.singleClick {
            callback.invoke(false)
            dismiss()
        }
        tvOk.singleClick {
            callback.invoke(true)
            dismiss()
        }
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