## [更新日志][10]

----------

## 前言
Recyclerview下拉、上拉刷新的第三方库已经有很多了，用过[XRecyclerview][1]跟[SuperRecyclerview][2]，之前用XRecyclerview的时候，就遇到过在安卓5.0以下的部分手机，会出现不显示的情况，后来就换了SuperRecyclerview，用了一段时间后，发现在下拉刷新的时候，如果实现list.clear()则会闪退，找了好久没找到原因。后面就用了其他几个第三方库；

 1. [BaseRecyclerViewAdapterHelper][3]
 2. [EasySwipeMenuLayout][4]
 3. [TwinklingRefreshLayout][5]
 
这三个库相当的强大，具体使用可以查看上面的传送门，用这些库实现一个多功能的Recyclerview是没问题的，但是每个库都有自己的优点以及缺点，而且每个库都有自己的方法，如果直接使用，你就会发现，实现功能的时候，会继承各种各样的方法，比较杂，比较乱。
**基于以上的问题，结合这几个库的优点，并且把一些公用的方法整合在一起，于是乎，就衍生出这个项目**

## 项目导入

 - 项目已经上传到GitHub上，想看源码的，这儿有[传送门][6]
 - 当然项目也已经上传到jcenter上了，只需在gradle添加即可使用这个库
  

```
compile 'com.hebin:hxbrecyclerview:1.0.2'
```
## 项目使用
 - <h3>布局使用</h3> 
 

```
 <com.hebin.HxbRecyclerview
        android:id="@+id/hrMain"
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
```

 - <h3>使用Adapter</h3> 

```
class RefreshAdapter(layout: Int, var list: MutableList<TestEntity.ResultEntity>)
    : BaseHxbAdapter<TestEntity.ResultEntity, BaseViewHolder>(layout, list) {

    override fun convert(holder: BaseViewHolder, item: TestEntity.ResultEntity, position: Int) {
        // 设置文本
        holder.setText(R.id.tvTitle, item.title)
                // 添加点击事件
                .addOnClickListener(R.id.tvTitle)
                // 添加长按事件
                .addOnLongClickListener(R.id.tvTitle)
    }

}
```
 - <h3>设置布局样式</h3>
 

```
// 设置垂直线性布局
hrMain.setVerticalLinear()
// 设置水平线性布局
hrMain.setHorizontalLinear()
// 设置九宫格布局
hrMain.setGridLayout(3)
```

 - <h3>点击事件</h3> 
在Adapter中实现了点击事件，那么在Activity中就可以执行点击事件的操作

```
class TestActivity : AppCompatActivity(), HxbRecyclerview.Listener {

    fun setList() {
        val hrMain = HxbRecyclerview(this)
        
        val adapter = RefreshAdapter(R.layout.adapter_refresh,mList)
        hrMain.setListener(this)
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        super.onItemClick(adapter, view, position)
    }
}
```
Listener 接口实现了几个方法，只要在Activity中继承了HxbRecyclerview.Listener，都可以实现，当然，如果没用到也可以不写，没有强制要求重写全部方法；

```
 interface Listener {
        fun onRefresh() {}
        fun onLoadMore() {}
        fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {}
        fun onItemLongClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {}
    }
```

 - <h3>下拉刷新、上拉刷新</h3>
 只要在Activity中继承了HxbRecyclerview.Listener，即可通过onRefresh和onLoadMore方法实现下拉、上拉刷新
 

```
hrMain.setListener(this)

 // 下拉刷新
    override fun onRefresh() {
    }
 // 上拉加载更多
    override fun onLoadMore() {
    }
```
刷新（加载更多）之后，可实现的几个方法
```
// 完成下拉刷新
hrMain.refreshComplete()
// 完成上拉加载
hrMain.loadMoreComplete()
// 上拉加载失败
hrMain.loadFailed()
// 没有更多数据
hrMain.setNomore()
```
当然，下拉刷新的样式、上拉加载的样式是可以自定义的；下拉、上拉中使用的动画效果来源于[AVLoadingIndicatorView][7]，想要简单的更换样式，可以参考这个库；

```
// 更换下拉动画样式
hrMain.setIndicatorId(AVLoadingIndicatorView.BallPulseRise)
// 更换下拉动画的颜色
hrMain.setIndicatorColor(Color.parseColor("#F00000"))
// 更换上拉动画样式
hrMain.setBottomIndicatorId(AVLoadingIndicatorView.BallPulseRise)
// 更换上拉动画的颜色
hrMain.setBottomIndicatorColor(Color.parseColor("#F00000"))
```
除了动画可以自定义，下拉里面的其他东西都是可以自定义的

```
// 设置下拉箭头图片
hrMain.setArrowResource(resId)
// 设置下拉文字颜色
hrMain.setTextColor(color)
// 设置下拉的文字
hrMain.setPullDownStr(str)
// 设置下拉刷新完成的文字
hrMain.setPullDownStr(str)
// 设置下拉正在刷新的文字
hrMain.setPullDownStr(str)
```
当然，如果你不喜欢这个下拉头部，你也可以自定义一个下拉头部，只要继承**IHeaderView** 即可，具体怎样实现可以参考[HxbRefreshView][8]和[HxbLoadView][9]，最后别忘了设置

```
// 自定义下拉刷新布局
hrMain.setHeadView(HxbRefreshView(context))
// 自定义上拉刷新布局
hrMain.setBottomView(HxbLoadView(context))
```
如果请求返回数据为空，列表也可以通过setEmpty来显示空布局，空布局点击的时候，默认执行onRefresh的操作

```
 hrMain.setEmpty()
```
当然，空布局、上拉加载失败布局、上拉没有更多数据布局都是可以自定义的，通过以下几个方法实现

```
// 数据为空的布局
hrMain.setEmptyView(view)
// 加载失败的布局
hrMain.setFailView(view)
// 没有更多数据的布局
hrMain.setNomoreView(view)
```
还可以设置上拉、下拉越界的最大高度，以及刷新时的固定高度

```
// 下拉刷新的固定高度
hrMain.setHeadHeight(60f)
// 下拉刷新的越界最大高度
hrMain.setMaxHeadHeight(120f)
```
其他刷新功能

```
// 开启或者关闭刷新
hrMain.setEnableRefresh(true)
// 开启或者关闭加载更多
hrMain.setEnableLoadmore(true)
// 强制刷新
hrMain.startRefresh()
// 强制加载更多
hrMain.startLoadMore()
// 隐藏全部刷新布局
hrMain.setPureScrollModeOn
// 悬浮式下拉刷新
hrMain.setFloatRefresh
```

 - <h3>多布局列表</h3>
在开发过程中往往会遇到，一个列表里面，不仅仅只有一种样式，在这里展示一种较为简易的实现方式，这种实现方式只是在[BaseRecyclerViewAdapterHelper][3]的基础上做简化，详细使用可以参考文档

```
class MultipleAdapter(var list: MutableList<MultipleItem>) : BaseHxbMultiAdapter<MultipleItem, BaseViewHolder>(list) {

    init {
        addItemType(MultipleItem.ONE, R.layout.adapter_multiple_01)
        addItemType(MultipleItem.TWO, R.layout.adapter_multiple_02)
        addItemType(MultipleItem.THREE, R.layout.adapter_multiple_03)
    }


    override fun convert(holder: BaseViewHolder, item: MultipleItem, position: Int) {
        when (item.itemType) {
            MultipleItem.ONE -> {
                holder.addOnClickListener(R.id.ivEmpty)
                        .addOnLongClickListener(R.id.ivEmpty)
            }
            MultipleItem.TWO -> {
            }
            MultipleItem.THREE -> {
            }
        }
    }

}
```
然后在Activity中实现也非常简单；

```
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

```

 - <h3>仿QQ的侧滑弹出删除</h3>
 这个功能是应用了[EasySwipeMenuLayout][4]，详细使用可参考文档，在这里简单说一下，其实使用非常简单，在需要在你列表的item的布局里面添加代码即可；
 

```
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:id="@+id/ll_root"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_marginBottom="15dp"
    android:clickable="true"
    android:orientation="vertical">

    <com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout
        android:id="@+id/es"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        app:contentView="@+id/content"
        app:leftMenuView="@+id/left"
        app:rightMenuView="@+id/right">

        <LinearLayout
            android:id="@+id/left"
            android:layout_width="100dp"
            android:layout_height="wrap_content"
            android:background="@android:color/holo_blue_dark"
            android:orientation="horizontal"
            android:padding="20dp">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:clickable="true"
                android:text="分享" />

        </LinearLayout>

        <LinearLayout
            android:id="@+id/content"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:background="#cccccc"
            android:orientation="vertical"
            android:padding="20dp">

            <TextView
                android:id="@+id/tvTitle"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="内容区域" />

        </LinearLayout>

        <LinearLayout
            android:id="@+id/right"

            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:background="@android:color/holo_red_light"
            android:orientation="horizontal">

            <TextView
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="@android:color/holo_blue_bright"
                android:clickable="true"
                android:padding="20dp"
                android:text="删除" />

            <TextView
                android:id="@+id/right_menu_2"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:background="@android:color/holo_orange_dark"
                android:clickable="true"
                android:padding="20dp"
                android:text="收藏" />

        </LinearLayout>
    </com.guanaj.easyswipemenulibrary.EasySwipeMenuLayout>
</LinearLayout>
```

 - <h3>其他使用</h3>
[BaseRecyclerViewAdapterHelper][3]这个库还实现了很多方法，比如说动画效果、树状列表等等，具体使用，请参考官方文档。

## 写在后面的话
非常感谢这些优秀的库，给我很多启发以及帮助，有什么写得不好或者不懂的，或者其他什么问题的，欢迎给我反馈。 
 

[1]: https://github.com/XRecyclerView/XRecyclerView
[2]: https://github.com/supercwn/SuperRecycleView
[3]: https://github.com/CymChad/BaseRecyclerViewAdapterHelper
[4]: https://github.com/anzaizai/EasySwipeMenuLayout
[5]: https://github.com/lcodecorex/TwinklingRefreshLayout
[6]: https://github.com/Hebin320/HxbRecyclerview
[7]: https://github.com/81813780/AVLoadingIndicatorView
[8]: https://github.com/Hebin320/HxbRecyclerview/blob/master/hxbrecyclerview/src/main/java/com/hebin/HxbRefreshView.kt
[9]: https://github.com/Hebin320/HxbRecyclerview/blob/master/hxbrecyclerview/src/main/java/com/hebin/HxbLoadView.kt
[10]: https://github.com/Hebin320/HxbRecyclerview/edit/master/UpdateLog.md
