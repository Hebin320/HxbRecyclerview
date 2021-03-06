package com.hebin

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity

/**
 * Author Hebin
 * <p>
 * created at 2018/4/17
 * <p>
 * blog: http://blog.csdn.net/hebin320320
 * <p>
 * GitHub: https://github.com/Hebin320
 * <p>
 * describe：
 */
abstract class BaseHxbMultiAdapter<T : MultiItemEntity?, K : BaseViewHolder>(data: MutableList<T>?) : BaseMultiItemQuickAdapter<T, K>(data) {

    override fun convert(helper: K, item: T) {
        convert(helper, item, helper.layoutPosition)
    }

    protected abstract fun convert(holder: BaseViewHolder, item: T, position: Int)
}