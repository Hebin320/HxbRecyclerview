@file:Suppress("ImplicitThis", "DEPRECATION")

package com.hebin

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Point
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.support.annotation.ColorInt
import android.support.annotation.DrawableRes
import android.support.v4.widget.NestedScrollView
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.LinearLayout
import android.widget.ScrollView
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.hebin.anko.setGone
import com.hebin.anko.setVisible
import com.lcodecore.tkrefreshlayout.IBottomView
import com.lcodecore.tkrefreshlayout.IHeaderView
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout
import kotlinx.android.synthetic.main.view_hxbrecyclerview.view.*
import org.jetbrains.anko.internals.AnkoInternals
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView


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
        this.trlHxb.setAutoLoadMore(true)
        val failView = LayoutInflater.from(context).inflate(R.layout.view_failed, null)
        val nomoreView = LayoutInflater.from(context).inflate(R.layout.view_nomore, null)
        val emptyView = LayoutInflater.from(context).inflate(R.layout.view_empty, null)
        val emptyLp = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
        emptyLp.gravity = Gravity.CENTER
        emptyLp.height = getShigh(context as Activity)
        emptyView.layoutParams = emptyLp
        llNomore.addView(nomoreView)
        llFailed.addView(failView)
        llEmpty.addView(emptyView)
        // 点击加载失败布局，分页加载
        llFailed.setOnClickListener {
            this.trlHxb.setEnableLoadmore(true)
            this.trlHxb.startLoadMore()
            this.trlHxb.setAutoLoadMore(true)
        }
        // 点击为空的布局，强制刷新列表
        llEmpty.setOnClickListener {
            this.trlHxb.setEnableRefresh(true)
            this.trlHxb.setEnableLoadmore(true)
            this.trlHxb.startRefresh()
        }
        setFirstLoadMore()
    }

    fun setFirstLoadMore() {
        rvHxb.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {

            override fun onGlobalLayout() {
                rvHxb.viewTreeObserver.removeGlobalOnLayoutListener(this)
                if (rvHxb.measuredHeight < (getShigh(context as Activity) - 70f - getStatusbar(context as Activity))) {
                    trlHxb.setEnableLoadmore(false)
                    trlHxb.setAutoLoadMore(false)
                } else {
                    trlHxb.setEnableLoadmore(true)
                    trlHxb.setAutoLoadMore(true)
                }
            }
        })
    }

    // 自定义为空的布局
    fun setEmptyView(view: View) {
        llEmpty.removeAllViews()
        llEmpty.addView(view)
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
        bottomView?.setIndicatorColor(color)
    }

    // 设置加载更多动画的样式
    fun setBottomIndicatorId(indicator: Int) {
        bottomView?.setIndicatorId(indicator)
    }

    // 设置加载更多的固定高度
    fun setBottomHeight(headHeight: Float) {
        this.trlHxb.setBottomHeight(headHeight)
    }

    // 设置加载更多图标的资源文件
    fun setBottomArrowResource(@DrawableRes resId: Int) {
        bottomView?.setArrowResource(resId)
    }

    // 设置加载更多的字体颜色
    fun setBottomTextColor(@ColorInt color: Int) {
        bottomView?.setTextColor(color)
    }

    // 设置加载更多上拉刷新的显示文字
    fun setBottomPullDownStr(pullDownStr1: String) {
        headView?.setPullDownStr(pullDownStr1)
    }

    // 设置加载更多刷新中的显示文字
    fun setBottomReleaseRefreshStr(releaseRefreshStr1: String) {
        bottomView?.setReleaseRefreshStr(releaseRefreshStr1)
    }

    // 设置加载更多刷新完成的显示文字
    fun setBottomRefreshingStr(refreshingStr1: String) {
        bottomView?.setRefreshingStr(refreshingStr1)
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
                listener.onRefresh()
            }

            override fun onLoadMore(refreshLayout: TwinklingRefreshLayout?) {
                super.onLoadMore(refreshLayout)
                llFailed.setGone()
                llNomore.setGone()
                llEmpty.setGone()
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
        setFirstLoadMore()
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
        this.trlHxb.setAutoLoadMore(false)
        handler.post { svHebin.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    // 没有更多
    fun setNomore() {
        loadMoreComplete()
        setEnableLoadmore(false)
        llEmpty.setGone()
        llFailed.setGone()
        llNomore.setVisible()
        this.trlHxb.setAutoLoadMore(false)
        handler.post { svHebin.fullScroll(ScrollView.FOCUS_DOWN) }
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
        this.trlHxb.setAutoLoadMore(true)
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

    /**
     * 获取屏幕高度
     */
    fun getShigh(activity: Activity): Int {
        var realHeight = 0
        try {
            val display = activity.windowManager.defaultDisplay
            val metrics = DisplayMetrics()
            display.getMetrics(metrics)
            if (Build.VERSION.SDK_INT >= 17) {
                val size = Point()
                display.getRealSize(size)
                realHeight = size.y
            } else if (Build.VERSION.SDK_INT < 17) {
                val mGetRawH = Display::class.java.getMethod("getRawHeight")
                realHeight = mGetRawH.invoke(display) as Int
            } else {
                realHeight = metrics.heightPixels
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return realHeight
    }

    /**
     * 获取状态栏高度
     */
    fun getStatusbar(activity: Activity): Int {
        var high = 0
        val rectangle = Rect()
        val window = activity.window
        window.decorView.getWindowVisibleDisplayFrame(rectangle)
        high = rectangle.top
        return high
    }

}
