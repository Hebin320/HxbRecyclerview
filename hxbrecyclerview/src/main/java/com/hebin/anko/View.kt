@file:Suppress("NOTHING_TO_INLINE")

package com.hebin.anko

import android.view.View
import com.hebin.entity.MultipleItem

/**
 * Author Hebin
 * <p>
 * created at 2018/4/11
 * <p>
 * blog: http://blog.csdn.net/hebin320320
 * <p>
 * GitHub: https://github.com/Hebin320
 * <p>
 * describe：
 */

inline fun View.setGone() {
    this.visibility = View.GONE
}

inline fun View.setVisible() {
    this.visibility = View.VISIBLE
}

inline fun View.setInvisible() {
    this.visibility = View.INVISIBLE
}

/**
 *  Recyclerview 多布局数据源设置
 * */
inline fun setMultiple(num: Int): MutableList<MultipleItem> {
    val mList: MutableList<MultipleItem> = ArrayList()
    if (num > 0) {
        for (i in 1..num) {
            mList.add(MultipleItem(i))
        }
    }
    return mList
}