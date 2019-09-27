package com.fivestars.fivestarsadbhelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fivestars.fivestarsadbhelper.util.AdbUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        set_button.setOnClickListener {
            val batteryLevel: String = battery_text_input.text.toString()
            batteryLevel.toIntOrNull()?.run {
                AdbUtil.setBatteryLevel(this)
            }
        }
    }
}
