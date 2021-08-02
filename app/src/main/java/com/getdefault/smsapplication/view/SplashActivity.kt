package com.getdefault.smsapplication.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.getdefault.smsapplication.R
import com.getdefault.smsapplication.databinding.ActivitySplashBinding
import com.getdefault.smsapplication.viewmodel.SplashViewModel

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding
    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        init()
    }

    private fun init() {
        setViewModel()
    }

    private fun setViewModel() {
        splashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

        splashViewModel.isLoggedIn.observe(this, Observer {
            goToNextActivity(it)
        })

        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed({
            splashViewModel.mRedirect()

        }, 3000)
    }

    private fun goToNextActivity(flag: Boolean) {
        if (flag) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        } else {
            val intent = Intent(this, LoginActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            finish()
        }

    }
}