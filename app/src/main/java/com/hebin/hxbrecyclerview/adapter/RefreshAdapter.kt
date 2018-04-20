package com.hebin.hxbrecyclerview.adapter

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.hebin.BaseHxbAdapter
import com.hebin.HxbRecyclerview
import com.hebin.hxbrecyclerview.R
import com.hebin.hxbrecyclerview.entity.TestEntity
import kotlinx.android.synthetic.main.activity_recyclerview.*

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
class RefreshAdapter(layout: Int, var list: MutableList<TestEntity.ResultEntity>)
    : BaseHxbAdapter<TestEntity.ResultEntity, BaseViewHolder>(layout, list) {

    fun refresh(list: MutableList<TestEntity.ResultEntity>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun convert(holder: BaseViewHolder, item: TestEntity.ResultEntity, position: Int) {
        // 设置文本
        holder.setText(R.id.tvTitle, item.title)
                // 添加点击事件
                .addOnClickListener(R.id.tvTitle)
                // 添加长按事件
                .addOnLongClickListener(R.id.tvTitle)
    }

}
