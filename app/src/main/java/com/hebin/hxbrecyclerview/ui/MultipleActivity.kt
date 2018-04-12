package com.hebin.hxbrecyclerview.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.hebin.anko.setMultiple
import com.hebin.hxbrecyclerview.R
import com.hebin.hxbrecyclerview.adapter.MultipleAdapter
import kotlinx.android.synthetic.main.activity_recyclerview.*

class MultipleActivity : AppCompatActivity() {

    var adapter: MultipleAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recyclerview)
        setList()
    }

    private fun setList() {
        // 垂直线性布局
        hrMain.setVerticalLinear()
        adapter = MultipleAdapter(setMultiple(3))
        hrMain.setAdapter(adapter)
        // 开启纯净模式，所有的刷新布局都不显示
        hrMain.setPureScrollModeOn()
    }

}
