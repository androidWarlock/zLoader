package com.ahmedmabrook.zloader.base.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.ahmedmabrook.zloader.R

/**
 * Created by Ahmed Mabrook - amostafa.mabrook@gmail.com
 *
 */

abstract class BaseActivity : AppCompatActivity()  {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
