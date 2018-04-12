@file:Suppress("ImplicitThis", "DEPRECATION")

package com.hebin

import android.annotation.SuppressLint
import android.content.Context
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hebin.anko.setGone
import com.hebin.anko.setVisible
import com.lcodecore.tkrefreshlayout.IBottomView
import com.lcodecore.tkrefreshlayout.IHeaderView
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.view_empty.view.*
import kotlinx.android.synthetic.main.view_hxbrecyclerview.view.*
import org.jetbrains.anko.imageResource

/**
 * Author Hebin
 * <p>
 * created at 2018/4/11 15:22
 * <p>
 * blog: http://blog.csdn.net/hebin320320
 * <p>
 * GitHub: https://github.com/Hebin320
 * <p>
 * describe：
 */
class HxbRecyclerview @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    init {
        init()
    }

    private var quickAdapter: BaseQuickAdapter<*, *>? = null
    private var multiAdapter: BaseMultiItemQuickAdapter<*, *>? = null
    private var headView: HxbRefreshView? = null
    private var bottomView: HxbLoadView? = null


    @SuppressLint("InflateParams")
    private fun init() {
        addView(LayoutInflater.from(context).inflate(R.layout.view_hxbrecyclerview, null))
        headView = HxbRefreshView(context)
        bottomView = HxbLoadView(context)
        this.trlHxb.setHeaderView(headView)
        this.trlHxb.setHeaderHeight(60f)
        this.trlHxb.setBottomHeight(60f)
        this.trlHxb.setBottomView(bottomView)
        val failView = LayoutInflater.from(context).inflate(R.layout.view_failed, null)
        val nomoreView = LayoutInflater.from(context).inflate(R.layout.view_nomore, null)
        val emptyView = LayoutInflater.from(context).inflate(R.layout.view_empty, null)
        llNomore.addView(nomoreView)
        llFailed.addView(failView)
        llEmpty.addView(emptyView)
        // 点击加载失败布局，分页加载
        llFailed.setOnClickListener { this.trlHxb.startLoadMore() }
        // 点击为空的布局，强制刷新列表
        llEmpty.setOnClickListener { this.trlHxb.startRefresh() }
    }

    // 自定义为空的布局
    fun setEmptyView(view: View) {
        llFailed.removeAllViews()
        llFailed.addView(view)
    }

    // 自定义加载失败的布局
    fun setFailView(view: View) {
        llFailed.removeAllViews()
        llFailed.addView(view)
    }

    // 自定义没有更多的布局
    fun setNomoreView(view: View) {
        llNomore.removeAllViews()
        llNomore.addView(view)
    }


    // 设置布局样式
    fun setVerticalLinear() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        this.rvHxb.layoutManager = layoutManager
    }

    // 设置布局样式
    fun setHorizontalLinear() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        this.rvHxb.layoutManager = layoutManager
    }

    // 设置布局样式
    fun setGridLayout(size: Int) {
        val layoutManager = GridLayoutManager(context, size)
        this.rvHxb.layoutManager = layoutManager
    }

    // 设置adapter
    fun setAdapter(adapter: BaseQuickAdapter<*, *>?) {
        this.quickAdapter = adapter
        this.rvHxb.adapter = adapter
    }

    // 设置adapter
    fun setAdapter(adapter: BaseMultiItemQuickAdapter<*, *>?) {
        this.multiAdapter = adapter
        this.rvHxb.adapter = adapter
    }

    // 设置下拉刷新动画的颜色
    fun setIndicatorColor(color: Int) {
        headView?.setIndicatorColor(color)
    }

    // 设置下拉刷新动画的样式
    fun setIndicatorId(indicator: Int) {
        headView?.setIndicatorId(indicator)
    }

    // 设置下拉的最大高度
    fun setHeadHeight(headHeight: Float) {
        this.trlHxb.setHeaderHeight(headHeight)
    }

    // 设置下拉图标的资源文件
    fun setArrowResource(@DrawableRes resId: Int) {
        headView?.setArrowResource(resId)
    }

    // 设置下拉的字体颜色
    fun setTextColor(@ColorInt color: Int) {
        headView?.setTextColor(color)
    }

    // 设置下拉的文字
    fun setPullDownStr(pullDownStr1: String) {
        headView?.setPullDownStr(pullDownStr1)
    }

    // 设置下拉刷新完成的文字
    fun setReleaseRefreshStr(releaseRefreshStr1: String) {
        headView?.setReleaseRefreshStr(releaseRefreshStr1)
    }

    // 设置下拉正在刷新的文字
    fun setRefreshingStr(refreshingStr1: String) {
        headView?.setRefreshingStr(refreshingStr1)
    }

    // 自定义下拉刷新头部
    fun setHeadView(headView: IHeaderView) {
        this.trlHxb.setHeaderView(headView)
    }

    // 设置下拉刷新的最大越界高度
    fun setMaxHeadHeight(headHeight: Float) {
        this.trlHxb.setMaxHeadHeight(headHeight)
    }

    // 设置加载更多动画的颜色
    fun setBottomIndicatorColor(color: Int) {
        headView?.setIndicatorColor(color)
    }

    // 设置加载更多动画的样式
    fun setBottomIndicatorId(indicator: Int) {
        headView?.setIndicatorId(indicator)
    }

    // 设置加载更多的固定高度
    fun setBottomHeight(headHeight: Float) {
        this.trlHxb.setBottomHeight(headHeight)
    }

    // 设置加载更多图标的资源文件
    fun setBottomArrowResource(@DrawableRes resId: Int) {
        headView?.setArrowResource(resId)
    }

    // 设置加载更多的字体颜色
    fun setBottomTextColor(@ColorInt color: Int) {
        headView?.setTextColor(color)
    }

    // 设置加载更多上拉刷新的显示文字
    fun setBottomPullDownStr(pullDownStr1: String) {
        headView?.setPullDownStr(pullDownStr1)
    }

    // 设置加载更多刷新中的显示文字
    fun setBottomReleaseRefreshStr(releaseRefreshStr1: String) {
        headView?.setReleaseRefreshStr(releaseRefreshStr1)
    }

    // 设置加载更多刷新完成的显示文字
    fun setBottomRefreshingStr(refreshingStr1: String) {
        headView?.setRefreshingStr(refreshingStr1)
    }

    // 设置加载更多的最大高度
    fun setMaxBottomHeight(bottomHeight: Float) {
        this.trlHxb.setMaxHeadHeight(bottomHeight)
    }

    // 设置加载更多的布局
    fun setBottomView(bottomView: IBottomView) {
        this.trlHxb.setBottomView(bottomView)
    }

    fun setListener(listener: Listener) {
        // 刷新、加载更多
        this.trlHxb.setOnRefreshListener(object : RefreshListenerAdapter() {
            override fun onRefresh(refreshLayout: TwinklingRefreshLayout?) {
                super.onRefresh(refreshLayout)
                llFailed.setGone()
                llNomore.setGone()
                llEmpty.setGone()
                setEnableRefresh(true)
                setEnableLoadmore(true)
                listener.onRefresh()
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                llFailed.setGone()
                llNomore.setGone()
                llEmpty.setGone()
                setEnableLoadmore(true)
                listener.onLoadMore()
            }
        })
        //  点击事件、长按事件
        if (quickAdapter != null) {
            quickAdapter?.setOnItemChildClickListener { adapter, view, position -> listener.onItemClick(adapter, view, position) }
            quickAdapter?.onItemChildLongClickListener = BaseQuickAdapter.OnItemChildLongClickListener { adapter, view, position ->
                listener.onItemLongClick(adapter, view, position)
                false
            }
        }
        // 点击事件、长按事件
        if (multiAdapter != null) {
            multiAdapter?.setOnItemChildClickListener { adapter, view, position -> listener.onItemClick(adapter, view, position) }
            multiAdapter?.onItemChildLongClickListener = BaseQuickAdapter.OnItemChildLongClickListener { adapter, view, position ->
                listener.onItemLongClick(adapter, view, position)
                false
            }
        }
    }

    // 刷新完成
    fun refreshComplete() {
        this.trlHxb.finishRefreshing()
    }

    // 加载更多完成
    fun loadMoreComplete() {
        this.trlHxb.finishLoadmore()
    }

    // 开启或者关闭刷新
    fun setEnableRefresh(isEnable: Boolean) {
        this.trlHxb.setEnableRefresh(isEnable)
    }

    // 开启或者关闭加载更多
    fun setEnableLoadmore(isEnable: Boolean) {
        this.trlHxb.setEnableLoadmore(isEnable)
    }

    // 加载失败
    fun loadFailed() {
        loadMoreComplete()
        setEnableLoadmore(false)
        llEmpty.setGone()
        llFailed.setVisible()
        llNomore.setGone()
    }

    // 没有更多
    fun setNomore() {
        loadMoreComplete()
        setEnableLoadmore(false)
        llEmpty.setGone()
        llFailed.setGone()
        llNomore.setVisible()
    }

    // 显示空布局
    fun setEmpty() {
        loadMoreComplete()
        refreshComplete()
        setEnableRefresh(false)
        setEnableLoadmore(false)
        llFailed.setGone()
        llNomore.setGone()
        llEmpty.setVisible()
    }

    // 强制刷新
    fun startRefresh() {
        this.trlHxb.startRefresh()
    }

    // 强制 加载更多
    fun startLoadMore() {
        this.trlHxb.startLoadMore()
    }

    // 隐藏全部刷新布局
    fun setPureScrollModeOn() {
        this.trlHxb.setPureScrollModeOn()
    }

    // 悬浮式下拉刷新
    fun setFloatRefresh(isFloat: Boolean) {
        this.trlHxb.setFloatRefresh(isFloat)
    }


    interface Listener {
        fun onRefresh() {}
        fun onLoadMore() {}
        fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {}
        fun onItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {}
    }


}
