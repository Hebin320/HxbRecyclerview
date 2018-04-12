package com.hebin.hxbrecyclerview.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hebin.hxbrecyclerview.R
import com.hebin.hxbrecyclerview.adapter.RefreshAdapter
import com.hebin.hxbrecyclerview.entity.TestEntity
import kotlinx.android.synthetic.main.activity_recyclerview.*

class SwipeActivity : AppCompatActivity() {

    var adapter: RefreshAdapter? = null
    var list: MutableList<TestEntity.ResultEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        setList()
    }

    private fun setList() {
        // 设置垂直线性布局
        hrMain.setVerticalLinear()
        setData()
        adapter = RefreshAdapter(R.layout.adapter_swpie_menu, list)
        hrMain.setAdapter(adapter)
        // 开启纯净模式，所有的刷新布局都不显示
        hrMain.setPureScrollModeOn()
    }

    private fun setData() {
        val title = arrayOf("标题1", "标题2", "标题3", "标题4")
        for (s in title) {
            val entity = TestEntity.ResultEntity()
            entity.title = s
            list.add(entity)
        }
    }

}
