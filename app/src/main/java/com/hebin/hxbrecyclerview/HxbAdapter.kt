package com.hebin.hxbrecyclerview

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder

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
class HxbAdapter(layout: Int, var list: MutableList<TestEntity.ResultEntity>) : BaseQuickAdapter<TestEntity.ResultEntity, BaseViewHolder>(layout, list) {

    fun refresh(list: MutableList<TestEntity.ResultEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun convert(helper: BaseViewHolder, item: TestEntity.ResultEntity) {
        helper.addOnLongClickListener(R.id.llMain)
    }
}