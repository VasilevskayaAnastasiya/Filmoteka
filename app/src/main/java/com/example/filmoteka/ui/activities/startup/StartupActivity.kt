package com.example.mila.ui.activities.startup

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.filmoteka.ui.activities.main.MainActivity

class StartupActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // has to be called before super.onCreate
        installSplashScreen()

        super.onCreate(savedInstanceState)

        // don't render anything while API request isn't completed
        findViewById<View>(android.R.id.content).viewTreeObserver.addOnPreDrawListener { false }

    }
}