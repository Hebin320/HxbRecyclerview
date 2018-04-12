package com.hebin.hxbrecyclerview.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hebin.hxbrecyclerview.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        onClick()
    }

    private fun onClick() {
        btnFresh.setOnClickListener { startActivity<RefreshActivity>() }
        btnMultiple.setOnClickListener { startActivity<MultipleActivity>() }
        btnSwipe.setOnClickListener { startActivity<SwipeActivity>() }
    }
}
