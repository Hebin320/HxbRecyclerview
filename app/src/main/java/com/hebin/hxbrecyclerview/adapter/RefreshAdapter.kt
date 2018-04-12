package com.hebin.hxbrecyclerview.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hebin.hxbrecyclerview.R
import com.hebin.hxbrecyclerview.entity.TestEntity

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
class RefreshAdapter(layout: Int, var list: MutableList<TestEntity.ResultEntity>) : BaseQuickAdapter<TestEntity.ResultEntity, BaseViewHolder>(layout, list) {

    fun refresh(list: MutableList<TestEntity.ResultEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun convert(helper: BaseViewHolder, item: TestEntity.ResultEntity) {
        // 设置文本
        helper.setText(R.id.tvTitle, item.title)
                // 添加点击时间
                .addOnClickListener(R.id.tvTitle)
                // 添加长按时间
                .addOnLongClickListener(R.id.tvTitle)
    }
}