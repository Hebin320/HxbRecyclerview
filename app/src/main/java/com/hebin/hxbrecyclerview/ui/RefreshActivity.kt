package com.hebin.hxbrecyclerview.ui

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hebin.HxbRecyclerview
import com.hebin.hxbrecyclerview.R
import com.hebin.hxbrecyclerview.adapter.RefreshAdapter
import com.hebin.hxbrecyclerview.entity.TestEntity
import com.hebin.pullDownStr
import com.hebin.textColor
import kotlinx.android.synthetic.main.activity_recyclerview.*
import org.jetbrains.anko.toast


class RefreshActivity : AppCompatActivity(), HxbRecyclerview.Listener {

    var count = 1
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
        adapter = RefreshAdapter(R.layout.adapter_title, list)
        hrMain.setAdapter(adapter)
        // 监听事件 包含多个方法，可实现，也可以不实现
        hrMain.setListener(this)
        // 强制刷新
//        hrMain.startRefresh()
        // 强制分页加载
//        hrMain.startLoadMore()


    }

    private fun setData() {
        val title = arrayOf("标题1", "标题2", "标题3", "标题4", "标题1", "标题2", "标题3", "标题4")
        for (s in title) {
            val entity = TestEntity.ResultEntity()
            entity.title = s
            list.add(entity)
        }
    }

    // 下拉刷新
    override fun onRefresh() {
        Handler().postDelayed({
            count = if (count == 4) {
                list.clear()
                adapter?.refresh(list)
                hrMain.setEmpty()
                1
            } else {
                list.clear()
                setData()
                adapter?.refresh(list)
                // 完成下拉刷新
                hrMain.refreshComplete()
                1
            }
        }, 2000)
    }


    override fun onLoadMore() {
        count++
        Handler().postDelayed({
            when (count) {
                2 -> {
                    list.addAll(list)
                    adapter?.refresh(list)
                    hrMain.loadMoreComplete()
                }
                3 -> hrMain.loadFailed()
                4 -> hrMain.setNomore()
            }
        }, 2000)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        super.onItemClick(adapter, view, position)
        when (view.id) {
            R.id.tvTitle -> toast("我是点击事件$position")
        }
    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        super.onItemLongClick(adapter, view, position)
        toast("我是长按事件$position")
    }

}
