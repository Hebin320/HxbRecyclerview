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
        emptyView.ivEmpty.imageResource = R.drawable.ic_downgrey
        llNomore.addView(nomoreView)
        llFailed.addView(failView)
        llEmpty.addView(emptyView)
        llFailed.setOnClickListener { this.trlHxb.startLoadMore() }
        llEmpty.setOnClickListener { this.trlHxb.startRefresh() }
    }

    fun setEmptyView(view: View) {
        llFailed.removeAllViews()
        llFailed.addView(view)
    }

    fun setFailView(view: View) {
        llFailed.removeAllViews()
        llFailed.addView(view)
    }

    fun setNomoreView(view: View) {
        llNomore.removeAllViews()
        llNomore.addView(view)
    }


    fun setVerticalLinear() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        this.rvHxb.layoutManager = layoutManager
    }

    fun setHorizontalLinear() {
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.HORIZONTAL
        this.rvHxb.layoutManager = layoutManager
    }

    fun setGridLayout(size: Int) {
        val layoutManager = GridLayoutManager(context, size)
        this.rvHxb.layoutManager = layoutManager
    }

    fun setAdapter(adapter: BaseQuickAdapter<*, *>) {
        this.quickAdapter = adapter
        this.rvHxb.adapter = adapter
    }

    fun setAdapter(adapter: BaseMultiItemQuickAdapter<*, *>) {
        this.multiAdapter = adapter
        this.rvHxb.adapter = adapter
    }

    fun setIndicatorColor(color: Int) {
        headView?.setIndicatorColor(color)
    }

    fun setIndicatorId(indicator: Int) {
        headView?.setIndicatorId(indicator)
    }

    fun setHeadHeight(headHeight: Float) {
        this.trlHxb.setHeaderHeight(headHeight)
    }

    fun setArrowResource(@DrawableRes resId: Int) {
        headView?.setArrowResource(resId)
    }

    fun setTextColor(@ColorInt color: Int) {
        headView?.setTextColor(color)
    }

    fun setPullDownStr(pullDownStr1: String) {
        headView?.setPullDownStr(pullDownStr1)
    }

    fun setReleaseRefreshStr(releaseRefreshStr1: String) {
        headView?.setReleaseRefreshStr(releaseRefreshStr1)
    }

    fun setRefreshingStr(refreshingStr1: String) {
        headView?.setRefreshingStr(refreshingStr1)
    }

    fun setHeadView(headView: IHeaderView) {
        this.trlHxb.setHeaderView(headView)
    }

    fun setMaxHeadHeight(headHeight: Float) {
        this.trlHxb.setMaxHeadHeight(headHeight)
    }

    fun setBottomIndicatorColor(color: Int) {
        headView?.setIndicatorColor(color)
    }

    fun setBottomIndicatorId(indicator: Int) {
        headView?.setIndicatorId(indicator)
    }

    fun setBottomHeight(headHeight: Float) {
        this.trlHxb.setBottomHeight(headHeight)
    }

    fun setBottomArrowResource(@DrawableRes resId: Int) {
        headView?.setArrowResource(resId)
    }

    fun setBottomTextColor(@ColorInt color: Int) {
        headView?.setTextColor(color)
    }

    fun setBottomPullDownStr(pullDownStr1: String) {
        headView?.setPullDownStr(pullDownStr1)
    }

    fun setBottomReleaseRefreshStr(releaseRefreshStr1: String) {
        headView?.setReleaseRefreshStr(releaseRefreshStr1)
    }

    fun setBottomRefreshingStr(refreshingStr1: String) {
        headView?.setRefreshingStr(refreshingStr1)
    }

    fun setMaxBottomHeight(bottomHeight: Float) {
        this.trlHxb.setMaxHeadHeight(bottomHeight)
    }

    fun setBottomView(bottomView: IBottomView) {
        this.trlHxb.setBottomView(bottomView)
    }

    fun setListener(listener: Listener) {
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
        if (quickAdapter != null) {
            quickAdapter?.setOnItemChildClickListener { adapter, view, position -> listener.onItemClick(adapter, view, position) }
            quickAdapter?.onItemChildLongClickListener = BaseQuickAdapter.OnItemChildLongClickListener { adapter, view, position ->
                listener.onItemLongClick(adapter, view, position)
                false
            }
        }
        if (multiAdapter != null) {
            multiAdapter?.setOnItemChildClickListener { adapter, view, position -> listener.onItemClick(adapter, view, position) }
            multiAdapter?.onItemChildLongClickListener = BaseQuickAdapter.OnItemChildLongClickListener { adapter, view, position ->
                listener.onItemLongClick(adapter, view, position)
                false
            }
        }
    }

    fun refreshComplete() {
        this.trlHxb.finishRefreshing()
    }

    fun loadMoreComplete() {
        this.trlHxb.finishLoadmore()
    }

    fun setEnableRefresh(isEnable: Boolean) {
        this.trlHxb.setEnableRefresh(isEnable)
    }

    fun setEnableLoadmore(isEnable: Boolean) {
        this.trlHxb.setEnableLoadmore(isEnable)
    }

    fun loadFailed() {
        loadMoreComplete()
        setEnableLoadmore(false)
        llEmpty.setGone()
        llFailed.setVisible()
        llNomore.setGone()
    }

    fun setNomore() {
        loadMoreComplete()
        setEnableLoadmore(false)
        llEmpty.setGone()
        llFailed.setGone()
        llNomore.setVisible()
    }

    fun setEmpty() {
        loadMoreComplete()
        refreshComplete()
        setEnableRefresh(false)
        setEnableLoadmore(false)
        llFailed.setGone()
        llNomore.setGone()
        llEmpty.setVisible()
    }

    fun startRefresh() {
        this.trlHxb.startRefresh()
    }

    fun startLoadMore() {
        this.trlHxb.startLoadMore()
    }

    fun setPureScrollModeOn() {
        this.trlHxb.setPureScrollModeOn()
    }

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
