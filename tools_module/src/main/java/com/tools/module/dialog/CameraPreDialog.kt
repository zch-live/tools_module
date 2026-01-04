package com.tools.module.dialog

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import cn.tools.module.R


/**
 *相机权限弹窗
 */
class CameraPreDialog(private val callback: (isGet: Boolean) -> Unit) :
    DialogFragment() {

    private lateinit var tvMsg: TextView

    companion object {

        private var mDialog: CameraPreDialog? = null

        fun show(
            manager: FragmentManager,
            callback: (isGet: Boolean) -> Unit
        ) {
            mDialog = CameraPreDialog(callback)
            mDialog?.show(manager, "CameraPreDialog")
        }

        fun dismissAllowingStateLoss(){
            mDialog?.dismissAllowingStateLoss()
        }

        fun checkCameraPermission(context: Context): Boolean {
            // 检查是否已经获得摄像头权限
            return ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.dialong_camera, container, false)
        tvMsg = view.findViewById(R.id.tv_msg)
        initView()
        initClick()
        isCancelable = false
        return view
    }

    private fun initView(){
        if(checkCameraPermission(requireContext())){
             callback.invoke(true)
            dismiss()
        }else{
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.CAMERA),9897)
        }
    }

    private fun initClick() {

    }

    override fun onStart() {
        super.onStart()
        val win = dialog?.window
        win?.setBackgroundDrawableResource(android.R.color.transparent)
        val params = win?.attributes?.apply {
            width = ViewGroup.LayoutParams.MATCH_PARENT
            height = ViewGroup.LayoutParams.MATCH_PARENT
            gravity = Gravity.CENTER
        }
        win?.attributes = params
    }

}