package com.tools.module.utils

import com.tools.module.bean.Constants.CAL_TYPE_1
import com.tools.module.bean.Constants.CAL_TYPE_1X
import com.tools.module.bean.Constants.CAL_TYPE_2
import com.tools.module.bean.Constants.CAL_TYPE_3
import com.tools.module.bean.Constants.CAL_TYPE_4
import com.tools.module.bean.Constants.CAL_TYPE_5
import com.tools.module.bean.Constants.CAL_TYPE_6
import com.tools.module.bean.Constants.CAL_TYPE_7
import com.tools.module.bean.Constants.CAL_TYPE_8
import com.tools.module.bean.Constants.CAL_TYPE_9
import com.tools.module.bean.Constants.CAL_TYPE_BRACKETS1
import com.tools.module.bean.Constants.CAL_TYPE_BRACKETS2
import com.tools.module.bean.Constants.CAL_TYPE_COS
import com.tools.module.bean.Constants.CAL_TYPE_E
import com.tools.module.bean.Constants.CAL_TYPE_EX
import com.tools.module.bean.Constants.CAL_TYPE_LN
import com.tools.module.bean.Constants.CAL_TYPE_LOG
import com.tools.module.bean.Constants.CAL_TYPE_PI
import com.tools.module.bean.Constants.CAL_TYPE_SIN
import com.tools.module.bean.Constants.CAL_TYPE_SQUARE
import com.tools.module.bean.Constants.CAL_TYPE_TAN
import com.tools.module.bean.Constants.CAL_TYPE_0
import org.mariuszgromada.math.mxparser.Expression
import org.mariuszgromada.math.mxparser.mXparser
import java.text.DecimalFormat

/**
 * 计算集
 */
object CalculatorUtils {
    private const val CAL_ERROR = "计算错误"
    private var input: String = ""
    private var output: String = ""
    private var hasCalculated = false

    private val continueKeys by lazy {
        arrayOf(
            CAL_TYPE_0,
            CAL_TYPE_1,
            CAL_TYPE_2,
            CAL_TYPE_3,
            CAL_TYPE_4,
            CAL_TYPE_5,
            CAL_TYPE_6,
            CAL_TYPE_7,
            CAL_TYPE_8,
            CAL_TYPE_9,
            CAL_TYPE_E,
            CAL_TYPE_PI,
            CAL_TYPE_SIN,
            CAL_TYPE_COS,
            CAL_TYPE_TAN,
            CAL_TYPE_LOG,
            CAL_TYPE_LN,
            CAL_TYPE_BRACKETS1,
            CAL_TYPE_BRACKETS2,
            CAL_TYPE_SQUARE,
            CAL_TYPE_EX,
            CAL_TYPE_1X,
        )
    }

    fun getInput() = input
    fun hasCalculated() = hasCalculated

    fun reset() {
        input = ""
        output = ""
        hasCalculated = false
    }

    fun add(key: String): String {
        if (hasCalculated && output != CAL_ERROR) {
            if (continueKeys.contains(key)) {
                reset()
                input = key
            } else {
                hasCalculated = false
                input = output
                output = ""
                input += key
            }
        } else {
            input += key
        }
        return input
    }

    fun delete(): String {
        input = input.dropLast(1)
        return input
    }

    fun calculate(): String {
        mXparser.changeLanguageTo("zh")
        input = checkBrackets(input)
        hasCalculated = true
        output = try {
            val result = Expression(input).calculate()
            if (result.isNaN()) CAL_ERROR
            else DecimalFormat("0.#####").format(result)
        } catch (e: Exception) {
            CAL_ERROR
        }
        return output
    }

    private fun checkBrackets(input: String): String {
        val sb = StringBuilder(input)
        val s = input.count { it.toString() == CAL_TYPE_BRACKETS1 }
        val e = input.count { it.toString() == CAL_TYPE_BRACKETS2 }
        val diff = s - e
        if (diff > 0) {
            for (i in 0 until diff) {
                sb.append(CAL_TYPE_BRACKETS2)
            }
        }
        return sb.toString()
    }
}