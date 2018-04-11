package com.hebin.hxbrecyclerview

import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hebin.HxbRecyclerview
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity(), HxbRecyclerview.Listener {

    var adapter: HxbAdapter? = null
    var list: MutableList<TestEntity.ResultEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hrMain.setVerticalLinear()
        val title = arrayOf("s", "j")
        for (s in title) {
            val entity = TestEntity.ResultEntity()
            entity.title = s
            list.add(entity)
        }
        adapter = HxbAdapter(R.layout.adapter_test, list)
        hrMain.setAdapter(adapter!!)
        hrMain.setListener(this)
        hrMain.startRefresh()
    }

    override fun onRefresh() {
        Handler().postDelayed({
            list.clear()
            val title = arrayOf("s", "j")
            for (s in title) {
                val entity = TestEntity.ResultEntity()
                entity.title = s
                list.add(entity)
            }
            adapter?.refresh(list)
            count = 1
            hrMain.refreshComplete()
        }, 2000)
    }

    var count = 1

    override fun onLoadMore() {
        count++
        when (count) {
            2 -> {
                list.addAll(list)
                adapter?.refresh(list)
                hrMain.loadMoreComplete()
            }
            3 -> hrMain.loadFailed()
            4 -> {
                list.clear()
                adapter?.refresh(list)
                hrMain.setEmpty()
            }
            5 -> hrMain.setNomore()
        }
    }

    override fun onItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        super.onItemLongClick(adapter, view, position)
        toast("我是长按")
    }

}
