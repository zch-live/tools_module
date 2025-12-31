package com.tools.module.utils

import com.tools.module.bean.TransUnitBean
import java.math.BigDecimal
import java.text.DecimalFormat

/**
 * Created by lsy
 * Date : 2024/11/20
 * Desc :
 */
object TransformUtils {
    private val cnNum = arrayOf("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖")
    private val cnIntRace = arrayOf("", "拾", "佰", "仟")
    private val cnIntUnits = arrayOf("", "万", "亿", "兆")
    private val cnDecUnits = arrayOf("角", "分")
    private val cnIntLast = "元"
    private val cnInteger = "整"

    /**
     * 金额大小写转换，将数字转换为汉字
     */
    fun transUppercase(money: Double): String {
        // 格式化金额，确保小数点后保留两位
        val formattedMoney = DecimalFormat("##0.00").format(BigDecimal(money))
        val parts = formattedMoney.split(".")
        val integerPart = parts[0]   // 整数部分
        val decimalPart = if (parts.size > 1) parts[1] else "00" // 小数部分

        // 转换整数部分
        val integerResult = if (integerPart == "0") {
            "零$cnIntLast"
        } else {
            val array = integerPart.toCharArray()
            val length = integerPart.length
            var result = ""
            var zeroCount = 0

            for (i in array.indices) {
                val num = array[i].toString().toInt()
                val pos = length - i - 1 // 当前位置
                val unitGroup = pos / 4  // 单位组（万、亿、兆）
                val unitPos = pos % 4    // 单位位置（千、百、十）

                if (num == 0) {
                    zeroCount++
                } else {
                    if (zeroCount > 0) {
                        result += cnNum[0] // 添加一个“零”
                    }
                    zeroCount = 0
                    result += cnNum[num] + cnIntRace[unitPos] // 当前数字和对应单位
                }

                // 每个单位组添加单位（万、亿、兆）
                if (unitPos == 0 && zeroCount < 4) {
                    result += cnIntUnits[unitGroup]
                }
            }
            result + cnIntLast
        }

        // 转换小数部分
        val decimalResult = if (decimalPart == "00") {
            cnInteger
        } else {
            val array = decimalPart.toCharArray()
            var result = ""

            for (i in array.indices) {
                val num = array[i].toString().toInt()
                if (num != 0) {
                    result += cnNum[num] + cnDecUnits[i]
                }
            }
            result
        }

        return integerResult + decimalResult
    }

    fun transValue(value: Double, origin: TransUnitBean, target: TransUnitBean): Double {
        return value * origin.ratio / target.ratio
    }

}