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
compile 'com.hebin:hxbrecyclerview:1.0.0'
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
 - <h3>点击事件</h3> 
在Adapter中实现了点击事件，那么在Activity中就可以执行点击事件的操作

```
class TestActivity : AppCompatActivity(), HxbRecyclerview.Listener {

    fun setList() {
        val hrMain = HxbRecyclerview(this)
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

 - 

 

[1]: https://github.com/XRecyclerView/XRecyclerView
[2]: https://github.com/supercwn/SuperRecycleView
[3]: https://github.com/CymChad/BaseRecyclerViewAdapterHelper
[4]: https://github.com/anzaizai/EasySwipeMenuLayout
[5]: https://github.com/lcodecorex/TwinklingRefreshLayout
[6]: https://github.com/Hebin320/HxbRecyclerview
