package com.hebin.hxbrecyclerview.adapter

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hebin.entity.MultipleItem
import com.hebin.hxbrecyclerview.R

/**
 * Author Hebin
 * <p>
 * created at 2018/4/12
 * <p>
 * blog: http://blog.csdn.net/hebin320320
 * <p>
 * GitHub: https://github.com/Hebin320
 * <p>
 * describe：
 */
class MultipleAdapter(var list: MutableList<MultipleItem>) : BaseMultiItemQuickAdapter<MultipleItem, BaseViewHolder>(list) {

    init {
        addItemType(MultipleItem.ONE, R.layout.adapter_multiple_01)
        addItemType(MultipleItem.TWO, R.layout.adapter_multiple_02)
        addItemType(MultipleItem.THREE, R.layout.adapter_multiple_03)
    }


    override fun convert(helper: BaseViewHolder, item: MultipleItem) {
        when (item.itemType) {
            MultipleItem.ONE -> {
                helper.addOnClickListener(R.id.ivEmpty).addOnLongClickListener(R.id.ivEmpty)
            }
        }
    }
}