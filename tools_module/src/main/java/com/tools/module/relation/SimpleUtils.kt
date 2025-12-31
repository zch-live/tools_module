package com.tools.module.relation

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


fun Int.toRelDesc() = when (this) {
    REL_TYPE_FATHER -> "的父亲"
    REL_TYPE_MOTHER -> "的母亲"
    REL_TYPE_HUSBAND -> "的丈夫"
    REL_TYPE_WIFE -> "的妻子"
    REL_TYPE_OLD_BROTHER -> "的哥哥"
    REL_TYPE_LITTLE_BROTHER -> "的弟弟"
    REL_TYPE_OLD_SISTER -> "的姐姐"
    REL_TYPE_LITTLE_SISTER -> "的妹妹"
    REL_TYPE_SON -> "的儿子"
    REL_TYPE_DAUGHTER -> "的女儿"
    else -> ""
}

fun Int.toRelKey() = when (this) {
    REL_TYPE_FATHER -> "f"
    REL_TYPE_MOTHER -> "m"
    REL_TYPE_HUSBAND -> "h"
    REL_TYPE_WIFE -> "w"
    REL_TYPE_OLD_BROTHER -> "ob"
    REL_TYPE_LITTLE_BROTHER -> "lb"
    REL_TYPE_OLD_SISTER -> "os"
    REL_TYPE_LITTLE_SISTER -> "ls"
    REL_TYPE_SON -> "s"
    REL_TYPE_DAUGHTER -> "d"
    else -> ""
}