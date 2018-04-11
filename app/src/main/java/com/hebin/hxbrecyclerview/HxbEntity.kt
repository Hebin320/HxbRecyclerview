package com.hebin.hxbrecyclerview

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
    data class TestEntity(val result: ResultEntity) {
        data class ResultEntity(var title: String){
            constructor():this("")
        }
    }