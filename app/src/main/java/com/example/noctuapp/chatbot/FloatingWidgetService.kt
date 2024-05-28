// FloatingWidgetService.kt
package com.example.noctuapp.chatbot

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.view.WindowManager
import com.example.noctuapp.chatbot.*


class FloatingWidgetService : Service() {

    private lateinit var windowManager: WindowManager

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()
        windowManager = getSystemService(WINDOW_SERVICE) as WindowManager
    }

    override fun onDestroy() {
        super.onDestroy()
        // Add code to remove the floating widget if needed
    }
}
