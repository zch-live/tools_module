package com.tools.module.bean

/**
 * Desc : 常量集
 */
object Constants {
    const val CAL_TYPE_0 = "0"
    const val CAL_TYPE_1 = "1"
    const val CAL_TYPE_2 = "2"
    const val CAL_TYPE_3 = "3"
    const val CAL_TYPE_4 = "4"
    const val CAL_TYPE_5 = "5"
    const val CAL_TYPE_6 = "6"
    const val CAL_TYPE_7 = "7"
    const val CAL_TYPE_8 = "8"
    const val CAL_TYPE_9 = "9"
    const val CAL_TYPE_POINT = "."
    const val CAL_TYPE_E = "e"
    const val CAL_TYPE_PI = "π"

    const val CAL_TYPE_X2 = "^2"
    const val CAL_TYPE_ANGLE = ""
    const val CAL_TYPE_SIN = "sin("
    const val CAL_TYPE_COS = "cos("
    const val CAL_TYPE_TAN = "tan("
    const val CAL_TYPE_XY = "^"
    const val CAL_TYPE_LOG = "log2("
    const val CAL_TYPE_LN = "ln("
    const val CAL_TYPE_BRACKETS1 = "("
    const val CAL_TYPE_BRACKETS2 = ")"
    const val CAL_TYPE_SQUARE = "√"
    const val CAL_TYPE_EX = "e^"
    const val CAL_TYPE_1X = "1÷"
    const val CAL_TYPE_CHANGE = "change"

    const val CAL_TYPE_ADD = "+"
    const val CAL_TYPE_SUBTRACT = "-"
    const val CAL_TYPE_MULTIPLY = "×"
    const val CAL_TYPE_DIVIDE = "÷"
    const val CAL_TYPE_PERCENT = "%"
    const val CAL_TYPE_RESULT = "="
    const val CAL_TYPE_AC = "AC"
    const val CAL_TYPE_DEL = "DEL"

    const val REL_TYPE_FATHER = 200
    const val REL_TYPE_MOTHER = 201
    const val REL_TYPE_HUSBAND = 202
    const val REL_TYPE_WIFE = 203
    const val REL_TYPE_OLD_BROTHER = 204
    const val REL_TYPE_LITTLE_BROTHER = 205
    const val REL_TYPE_OLD_SISTER = 206
    const val REL_TYPE_LITTLE_SISTER = 207
    const val REL_TYPE_SON = 208
    const val REL_TYPE_DAUGHTER = 209

    const val KEY_SWITCH_SOUND = "key_switch_sound"
    const val KEY_SWITCH_VIBRATE = "key_switch_vibrate"
    const val KEY_SWITCH_CHANGE = "key_switch_change"

    const val KB_C_0 = "0"
    const val KB_C_1 = "1"
    const val KB_C_2 = "2"
    const val KB_C_3 = "3"
    const val KB_C_4 = "4"
    const val KB_C_5 = "5"
    const val KB_C_6 = "6"
    const val KB_C_7 = "7"
    const val KB_C_8 = "8"
    const val KB_C_9 = "9"
    const val KB_C_POINT = "."
    const val KB_C_AC = "kb_c_ac"
    const val KB_C_DEL = "kb_c_del"
    const val KB_C_EQUAL = "kb_c_equal"

    const val TRANS_UPPERCASE = 3001 //大小写转换
    const val TRANS_LENGTH = 3002 //长度转换
    const val TRANS_AREA = 3003 //面积转换
    const val TRANS_VOLUME = 3004 //体积转换
    const val TRANS_TEMPERATURE = 3005 //温度转换
    const val TRANS_SPEED = 3006 //速度转换
    const val TRANS_TIME = 3007 //时间转换
    const val TRANS_WEIGHT = 3008 //重量转换
    const val TRANS_EXCHANGE_RATE = 3009

    val UNIT_UPPERCASE by lazy {
        listOf(
            TransUnitBean("小写", "", 666.0),
            TransUnitBean("大写", "", 999.0),
        )
    }
    val UNIT_LENGTH by lazy {
        listOf(
            TransUnitBean("千米", "km", 1000.0),      // 千米 = 1000 米
            TransUnitBean("米", "m", 1.0),           // 米 = 1 米
            TransUnitBean("分米", "dm", 0.1),        // 分米 = 0.1 米
            TransUnitBean("厘米", "cm", 0.01),       // 厘米 = 0.01 米
            TransUnitBean("毫米", "mm", 0.001),      // 毫米 = 0.001 米
            TransUnitBean("微米", "um", 1e-6),       // 微米 = 0.000001 米
            TransUnitBean("纳米", "nm", 1e-9),       // 纳米 = 0.000000001 米
            TransUnitBean("皮米", "pm", 1e-12),      // 皮米 = 0.000000000001 米
            TransUnitBean("光年", "", 9.4607e15),    // 光年 ≈ 9.4607 × 10^15 米
            TransUnitBean("天文单位", "", 1.496e11),  // 天文单位 ≈ 1.496 × 10^11 米
            TransUnitBean("英尺", "ft", 0.3048),     // 英尺 ≈ 0.3048 米
            TransUnitBean("码", "yd", 0.9144),       // 码 ≈ 0.9144 米
            TransUnitBean("英里", "mile", 1609.34),  // 英里 ≈ 1609.34 米
            TransUnitBean("海里", "kt", 1852.0),     // 海里 ≈ 1852 米
            TransUnitBean("英寻", "fa", 1.8288),     // 英寻 ≈ 1.8288 米
            TransUnitBean("弗隆", "", 201.168),      // 弗隆 ≈ 201.168 米
            TransUnitBean("密耳", "", 0.0000254),    // 密耳 ≈ 0.0000254 米
            TransUnitBean("里", "", 500.0),          // 里 = 500 米
            TransUnitBean("丈", "", 10.0),           // 丈 = 10 米
            TransUnitBean("尺", "", 1.0 / 3.0),      // 尺 ≈ 0.333 米
            TransUnitBean("寸", "", 1.0 / 30.0),     // 寸 ≈ 0.0333 米
            TransUnitBean("分", "", 1.0 / 300.0),    // 分 ≈ 0.00333 米
            TransUnitBean("厘", "", 1.0 / 3000.0)    // 厘 ≈ 0.000333 米
        )
    }
    val UNIT_AREA by lazy {
        listOf(
            TransUnitBean("平方千米", "km²", 1000000.0),   // 平方千米 = 1,000,000 平方米
            TransUnitBean("平方米", "m²", 1.0),           // 平方米 = 1 平方米
            TransUnitBean("公顷", "ha", 10000.0),         // 公顷 = 10,000 平方米
            TransUnitBean("公亩", "a", 100.0),            // 公亩 = 100 平方米
            TransUnitBean("平方分米", "dm²", 0.01),        // 平方分米 = 0.01 平方米
            TransUnitBean("平方厘米", "cm²", 0.0001),      // 平方厘米 = 0.0001 平方米
            TransUnitBean("平方毫米", "mm²", 0.000001),    // 平方毫米 = 0.000001 平方米
            TransUnitBean("英亩", "", 4046.86),           // 英亩 = 4046.86 平方米
            TransUnitBean("平方英里", "mi²", 2589988.11),  // 平方英里 = 2,589,988.11 平方米
            TransUnitBean("平方码", "yd²", 0.836127),      // 平方码 = 0.836127 平方米
            TransUnitBean("平方英尺", "ft²", 0.092903),    // 平方英尺 = 0.092903 平方米
            TransUnitBean("平方英寸", "in²", 0.00064516),  // 平方英寸 = 0.00064516 平方米
            TransUnitBean("平方竿", "", 25.2929),          // 平方竿 = 25.2929 平方米
            TransUnitBean("顷", "", 66666.6667),          // 顷 = 66,666.6667 平方米
            TransUnitBean("亩", "", 666.6667),            // 亩 = 666.6667 平方米
            TransUnitBean("分", "", 66.6667),             // 分 = 66.6667 平方米
            TransUnitBean("平方尺", "", 0.092903),         // 平方尺 = 0.092903 平方米
            TransUnitBean("平方寸", "", 0.00092903)        // 平方寸 = 0.00092903 平方米
        )
    }
    val UNIT_VOLUME by lazy {
        listOf(
            TransUnitBean("立方千米", "km³", 1000000000.0),   // 立方千米 = 1,000,000,000 立方米
            TransUnitBean("立方米", "m³", 1.0),               // 立方米 = 1 立方米
            TransUnitBean("立方分米", "dm³", 0.001),          // 立方分米 = 0.001 立方米
            TransUnitBean("立方厘米", "cm³", 0.000001),       // 立方厘米 = 0.000001 立方米
            TransUnitBean("立方毫米", "mm³", 0.000000001),    // 立方毫米 = 0.000000001 立方米
            TransUnitBean("升", "L", 0.001),                 // 升 = 0.001 立方米
            TransUnitBean("公升", "dL", 0.0001),             // 公升 = 0.0001 立方米
            TransUnitBean("毫升", "mL", 0.000001),           // 毫升 = 0.000001 立方米
            TransUnitBean("微升", "µL", 0.000000001),        // 微升 = 0.000000001 立方米
            TransUnitBean("公石", "hL", 0.1),                // 公石 = 0.1 立方米
            TransUnitBean("立方英尺", "ft³", 0.0283168466),  // 立方英尺 = 0.0283168466 立方米
            TransUnitBean("立方码", "yd³", 0.764554858),     // 立方码 = 0.764554858 立方米
            TransUnitBean("亩英尺", "", 1233.481838),        // 亩英尺 = 1233.481838 立方米
            TransUnitBean("英制加仑", "", 0.00454609),       // 英制加仑 = 0.00454609 立方米
            TransUnitBean("美制加仑", "", 0.0037854118),     // 美制加仑 = 0.0037854118 立方米
            TransUnitBean("英制液体盘司", "", 0.0000284131), // 英制液体盘司 = 0.0000284131 立方米
            TransUnitBean("美制液体盘司", "", 0.0000295735)  // 美制液体盘司 = 0.0000295735 立方米
        )
    }
    val UNIT_TEMPERATURE by lazy {
        listOf(
            TransUnitBean("华氏度", "°F", 5.0 / 9.0),   // 华氏度 = 5/9 开氏度
            TransUnitBean("摄氏度", "°C", 1.0),         // 摄氏度 = 1 开氏度
            TransUnitBean("开氏度", "K", 1.0),          // 开氏度 = 1 开氏度
            TransUnitBean("兰氏度", "°Ra", 5.0 / 9.0),  // 兰氏度 = 5/9 开氏度
            TransUnitBean("英氏度", "°Re", 1.25)        // 英氏度 = 1.25 开氏度
        )
    }
    val UNIT_SPEED by lazy {
        listOf(
            TransUnitBean("米/秒", "m/s", 3.6),              // 米/秒 = 3.6 千米/时
            TransUnitBean("千米/时", "km/h", 1.0),           // 千米/时 = 1 千米/时
            TransUnitBean("千米/秒", "km/s", 3600.0),        // 千米/秒 = 3600 千米/时
            TransUnitBean("光速", "", 1079252848.8),        // 光速 = 1079252848.8 千米/时
            TransUnitBean("马赫", "", 1225.044),              // 马赫 = 1225.044 千米/时
            TransUnitBean("英里/时", "mi/h", 1.609344),      // 英里/时 = 1.609344 千米/时
            TransUnitBean("英寸/时", "in/h", 0.0000254)      // 英寸/时 = 0.0000254 千米/时
        )
    }
    val UNIT_TIME by lazy {
        listOf(
            TransUnitBean("分", "min", 60.0),            // 分 = 60 秒
            TransUnitBean("秒", "s", 1.0),               // 秒 = 1 秒
            TransUnitBean("小时", "hour", 3600.0),       // 小时 = 3600 秒
            TransUnitBean("天", "day", 86400.0),         // 天 = 86400 秒
            TransUnitBean("周", "week", 604800.0),       // 周 = 604800 秒
            TransUnitBean("月", "month", 2629800.0),     // 月 = 2629800 秒（平均值）
            TransUnitBean("年", "year", 31557600.0),     // 年 = 31557600 秒（平均值）
            TransUnitBean("皮秒", "ps", 1e-12),          // 皮秒 = 1e-12 秒
            TransUnitBean("纳秒", "ns", 1e-9),           // 纳秒 = 1e-9 秒
            TransUnitBean("微秒", "μs", 1e-6),           // 微秒 = 1e-6 秒
            TransUnitBean("毫秒", "ms", 1e-3),           // 毫秒 = 1e-3 秒
            TransUnitBean("回归年", "", 31556925.25),     // 回归年 = 31556925.25 秒
            TransUnitBean("格里年", "", 31556952.0),      // 格里年 = 31556952 秒
            TransUnitBean("儒略年", "", 31557600.0),      // 儒略年 = 31557600 秒
            TransUnitBean("十年", "", 315576000.0),       // 十年 = 315576000 秒
            TransUnitBean("世纪", "", 3155760000.0),      // 世纪 = 3155760000 秒
            TransUnitBean("千年", "", 31557600000.0)      // 千年 = 31557600000 秒
        )
    }
    val UNIT_WEIGHT by lazy {
        listOf(
            TransUnitBean("吨", "t", 1000.0),             // 吨 = 1000 千克
            TransUnitBean("千克", "kg", 1.0),             // 千克 = 1 千克
            TransUnitBean("克", "g", 0.001),              // 克 = 0.001 千克
            TransUnitBean("毫克", "mg", 1e-6),            // 毫克 = 1e-6 千克
            TransUnitBean("公担", "", 100.0),             // 公担 = 100 千克
            TransUnitBean("克拉", "ct", 0.0002),         // 克拉 = 0.0002 千克
            TransUnitBean("磅", "lb", 0.45359237),       // 磅 = 0.45359237 千克
            TransUnitBean("盎司", "oz", 0.0283495231),   // 盎司 = 0.0283495231 千克
            TransUnitBean("格令", "gr", 6.479891e-5),    // 格令 = 6.479891e-5 千克
            TransUnitBean("长吨", "", 1016.0469088),      // 长吨 = 1016.0469088 千克
            TransUnitBean("短吨", "", 907.18474),         // 短吨 = 907.18474 千克
            TransUnitBean("英担", "", 50.80234544),        // 英担 = 50.80234544 千克
            TransUnitBean("美担", "", 45.359237),         // 美担 = 45.359237 千克
            TransUnitBean("美石", "", 6.35029318),        // 美石 = 6.35029318 千克
            TransUnitBean("打兰", "", 0.0017718451953125), // 打兰 = 0.0017718451953125 千克
            TransUnitBean("金衡磅", "", 0.3732417216),     // 金衡磅 = 0.3732417216 千克
            TransUnitBean("金衡盎司", "", 0.0311034768),    // 金衡盎司 = 0.0311034768 千克
            TransUnitBean("英钱", "", 0.00155517384),     // 英钱 = 0.00155517384 千克
            TransUnitBean("担", "", 50.0),               // 担 = 50 千克
            TransUnitBean("斤", "", 0.5),                 // 斤 = 0.5 千克
            TransUnitBean("两", "", 0.05),               // 两 = 0.05 千克
            TransUnitBean("钱", "", 0.005)               // 钱 = 0.005 千克
        )
    }

    val UNIT_EXCHANGE_RATE by lazy {
        listOf(
            TransUnitBean("人民币", "", 1.0),
            TransUnitBean("美元", "", 7.331),
            TransUnitBean("欧元", "", 7.564),
            TransUnitBean("日元", "", 0.0463),
            TransUnitBean("韩元", "", 0.005),
            TransUnitBean("港币", "", 0.9425),
            TransUnitBean("英镑", "", 9.127),
            TransUnitBean("瑞士法郎", "", 8.038),
            TransUnitBean("新加坡元", "", 5.36),
        )
    }
}

data class TransUnitBean(val type: String, val unit: String, val ratio: Double)