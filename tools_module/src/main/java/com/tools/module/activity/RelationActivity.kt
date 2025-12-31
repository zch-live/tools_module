package com.tools.module.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import cn.tools.module.R
import com.tools.module.bean.Constants.REL_TYPE_DAUGHTER
import com.tools.module.bean.Constants.REL_TYPE_FATHER
import com.tools.module.bean.Constants.REL_TYPE_HUSBAND
import com.tools.module.bean.Constants.REL_TYPE_LITTLE_BROTHER
import com.tools.module.bean.Constants.REL_TYPE_LITTLE_SISTER
import com.tools.module.bean.Constants.REL_TYPE_MOTHER
import com.tools.module.bean.Constants.REL_TYPE_OLD_BROTHER
import com.tools.module.bean.Constants.REL_TYPE_OLD_SISTER
import com.tools.module.bean.Constants.REL_TYPE_SON
import com.tools.module.bean.Constants.REL_TYPE_WIFE
import com.tools.module.relation.*
import com.tools.module.relation.RelationInterface.Companion.eachAppellation
import com.tools.module.utils.JumpTools
import com.tools.module.utils.StatusBarUtil
import com.tools.module.utils.singleClick

/**
 *
 * 称呼计算
 */
class RelationActivity : AppCompatActivity(), RelationInterface {

    private lateinit var ivClose: ImageView
    private lateinit var btnEqual: ImageView
    private lateinit var btnBack: ImageView
    private lateinit var btnWife: ImageView
    private lateinit var btnHusband: ImageView
    private lateinit var btnChange: ImageView
    private lateinit var tvResult: TextView
    private lateinit var btnAc: ImageView
    private lateinit var tvInput: TextView
    private lateinit var btnFather: ImageView
    private lateinit var btnMother: ImageView
    private lateinit var btnOldBother: ImageView
    private lateinit var btnLittleBother: ImageView
    private lateinit var btnOldSister: ImageView
    private lateinit var btnLittleSister: ImageView
    private lateinit var btnSon: ImageView
    private lateinit var btnDaughter: ImageView

    override var currentRelative = StringBuilder()
    override var backRelative = Stack<SqList>()
    override var sex = ""
    override var eachRelative = ""
    override var tempCurrentRelative = SqList(12).apply { add("") }

    companion object {
        const val MAX_SIZE = 10
        fun start(context: Context) {
            val intent = Intent(context, RelationActivity::class.java).apply {
            }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        StatusBarUtil.setTransparentForWindow(this)
        setContentView(R.layout.activity_relation)
        JumpTools.mOnListener?.start()
        RelationInterface.init()
        initView()
        initClick()
        initData()
    }


    private fun initClick() {
        ivClose.singleClick {
            finish()
        }
        btnEqual.singleClick {
            enableBtn(false, btnBack)
            enableBtn(true, btnWife, btnHusband, btnChange)
            val results: String = searchRelative()
            if (results.isEmpty()) tvResult.text = "关系有点远~"
            else tvResult.text = results
            currentRelative.delete(0, currentRelative.length)
            currentRelative.append("我")
            eachRelative = tempCurrentRelative.getItem(0) //将原始输入亲戚关系存入字符串
            tempCurrentRelative.clear()
            backRelative.clearStack()
        }
        btnAc.singleClick {
            enableBtn(false, btnChange)
            enableBtn(true, btnWife, btnHusband, btnBack, btnEqual)
            currentRelative.delete(0, currentRelative.length)
            currentRelative.append("我")
            tvInput.text = "我"
            tvResult.text = ""
            tempCurrentRelative.clear()
        }
        btnBack.singleClick {
            enableBtn(true, btnEqual)
            if (tempCurrentRelative.getItem(0)
                    .isNotEmpty() && !backRelative.isEmpty
            ) { //用户当前输入不为空且上一步亲戚关系不为空
                tempCurrentRelative = backRelative.pop()
            } else {
                tempCurrentRelative.clear()
            }
            if (currentRelative.length > 1) { //屏幕字符串回退“的xx”
                currentRelative.delete(currentRelative.length - 3, currentRelative.length)
            }
            tvInput.text = currentRelative
            if (tempCurrentRelative.length() != 0) { //回退后亲戚关系不为空，则需要判断当前最后一位亲戚的性别
                val temp = tempCurrentRelative.getItem(0)
                sex = if ((temp.indexOf(',') == -1)) temp
                else temp.substring(temp.lastIndexOf(',') + 1, temp.length)
                if (sex.isEmpty()) {
                    enableBtn(true, btnWife, btnHusband)
                } else if (isMale()) { //男性，禁用老公按钮，启用老婆按钮
                    enableBtn(true, btnWife)
                    enableBtn(false, btnHusband)
                } else { //女性，禁用老婆按钮，启用老公按钮
                    enableBtn(true, btnHusband)
                    enableBtn(false, btnWife)
                }
            }
        }
        btnChange.singleClick {
            var results = ""
            tvInput.text = "TA称呼我"
            val tempStack = Stack<String>()
            eachRelative += ","
            var position: Int
            while (eachRelative.isNotEmpty()) {
                position = eachRelative.indexOf(',')
                tempStack.push(eachRelative.substring(0, position))
                eachRelative = eachRelative.substring(position + 1, eachRelative.length)
            }
            var tempKey: String
            while (!tempStack.isEmpty) { //用弹栈模拟用户反向输入并进行实时互称转换以及化简
                tempKey = tempStack.pop()
                sex = tempStack.getTop() ?: ""
                if (sex.isNotEmpty()) { // 下一个元素非空，即可以判断性别
                    if (tempKey == "h" || tempKey == "w" || tempKey == "") {
                        tempCurrentRelative.allItemAppend(eachAppellation[tempKey])
                    } else if (isMale()) {
                        tempCurrentRelative.allItemAppend(eachAppellation[tempKey + "M"])
                    } else {
                        tempCurrentRelative.allItemAppend(eachAppellation[tempKey + "F"])
                    }
                    simplyRelative()
                } else  //栈空无法判断性别
                {
                    if (tempKey == "h" || tempKey == "w" || tempKey == "") {
                        tempCurrentRelative.allItemAppend(eachAppellation[tempKey])
                        simplyRelative()
                        results = searchRelative()
                    } else { //则两种情况M/F
                        val temp = SqList(tempCurrentRelative)
                        tempCurrentRelative.allItemAppend(eachAppellation[tempKey + "M"])
                        temp.allItemAppend(eachAppellation[tempKey + "F"])
                        tempCurrentRelative.combine(temp)
                        simplyRelative()
                        results = searchRelative()
                    }
                }
            }
            if (results.isNotEmpty()) tvResult.text = results
            else tvResult.text = "关系有点远~"
            enableBtn(false, btnEqual, btnChange)
            tempCurrentRelative.clear()
        }

        initRelativeClick()
    }

    /*
     * 亲戚按钮点击事件
     *
     * 互查按钮不可用；等号按钮点击时，启用互查按钮。
     * 男性按钮点击时，老公按钮不可用；女性按钮点击时，老婆按钮不可用。
     *
     */
    private fun initRelativeClick() {
        btnHusband.singleClick {
            commonRelationClick(REL_TYPE_HUSBAND) {
                enableBtn(false, btnHusband, btnChange)
                enableBtn(true, btnWife, btnBack)
            }
        }
        btnWife.singleClick {
            commonRelationClick(REL_TYPE_WIFE) {
                enableBtn(false, btnWife, btnChange)
                enableBtn(true, btnHusband, btnBack)
            }
        }
        btnFather.singleClick {
            commonRelationClick(REL_TYPE_FATHER) {
                enableBtn(false, btnHusband, btnChange)
                enableBtn(true, btnWife, btnBack)
            }
        }
        btnMother.singleClick {
            commonRelationClick(REL_TYPE_MOTHER) {
                enableBtn(false, btnWife, btnChange)
                enableBtn(true, btnHusband, btnBack)
            }
        }
        btnOldBother.singleClick {
            commonRelationClick(REL_TYPE_OLD_BROTHER) {
                enableBtn(false, btnHusband, btnChange)
                enableBtn(true, btnWife, btnBack)
            }
        }
        btnLittleBother.singleClick {
            commonRelationClick(REL_TYPE_LITTLE_BROTHER) {
                enableBtn(false, btnHusband, btnChange)
                enableBtn(true, btnWife, btnBack)
            }
        }
        btnOldSister.singleClick {
            commonRelationClick(REL_TYPE_OLD_SISTER) {
                enableBtn(false, btnWife, btnChange)
                enableBtn(true, btnHusband, btnBack)
            }
        }
        btnLittleSister.singleClick {
            commonRelationClick(REL_TYPE_LITTLE_SISTER) {
                enableBtn(false, btnWife, btnChange)
                enableBtn(true, btnHusband, btnBack)
            }
        }
        btnSon.singleClick {
            commonRelationClick(REL_TYPE_SON) {
                enableBtn(false, btnHusband, btnChange)
                enableBtn(true, btnWife, btnBack)
            }
        }
        btnDaughter.singleClick {
            commonRelationClick(REL_TYPE_DAUGHTER) {
                enableBtn(false, btnWife, btnChange)
                enableBtn(true, btnHusband, btnBack)
            }
        }

    }

    private fun commonRelationClick(type: Int, doIt: () -> Unit) {
        if (backRelative.stackSize() < MAX_SIZE) {
            backRelative.push(SqList(tempCurrentRelative))
            currentRelative.append(type.toRelDesc())
            updateResult()
            btnEqual.setEnabled(true)
            tempCurrentRelative.allItemAppend(type.toRelKey())
            simplyRelative()
            doIt()
        }
    }


    private fun enableBtn(enable: Boolean, vararg btn: ImageView) {
        btn.forEach { it.isEnabled = enable }
    }

    private fun updateResult() {
        tvInput.text = currentRelative.toString()
    }

    private fun initView() {
        ivClose = findViewById(R.id.iv_close)
        btnEqual = findViewById(R.id.btn_equal)
        btnBack = findViewById(R.id.btn_back)
        btnWife = findViewById(R.id.btn_wife)
        btnHusband = findViewById(R.id.btn_husband)
        btnChange = findViewById(R.id.btn_change)
        tvResult = findViewById(R.id.tv_result)
        btnAc = findViewById(R.id.btn_ac)
        tvInput = findViewById(R.id.tv_input)
        btnFather = findViewById(R.id.btn_father)
        btnMother = findViewById(R.id.btn_mother)
        btnOldBother = findViewById(R.id.btn_old_bother)
        btnLittleBother = findViewById(R.id.btn_little_bother)
        btnOldSister = findViewById(R.id.btn_old_sister)
        btnLittleSister = findViewById(R.id.btn_little_sister)
        btnSon = findViewById(R.id.btn_son)
        btnDaughter = findViewById(R.id.btn_daughter)

        enableBtn(false, btnBack, btnChange)
    }

    override fun onDestroy() {
        super.onDestroy()
        JumpTools.mOnListener?.destroy()
    }

}