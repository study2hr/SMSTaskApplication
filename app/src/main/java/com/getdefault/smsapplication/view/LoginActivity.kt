package com.getdefault.smsapplication.view

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.FacebookSdk
import com.facebook.login.LoginResult
import com.getdefault.smsapplication.R
import com.getdefault.smsapplication.databinding.ActivityLoginBinding
import com.getdefault.smsapplication.utils.Utils
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityLoginBinding

    //    Google Sign-in
    private lateinit var mGoogleSignInClient: GoogleSignInClient
    private val GOOGLE_SIGN_IN: Int = 100

    //    Facebook log-in
    private lateinit var callbackManager: CallbackManager
    private val FB_SIGN_IN: Int = 64206
    val SMS_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {

//        if (!Utils.isAllPermissionsGranted(this, arrayOf("Manifest.permission.RECEIVE_SMS"))) {
//        Utils.requestPermissions(this,arrayOf("Manifest.permission.RECEIVE_SMS"), SMS_REQUEST_CODE)
//        }

        if (checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.RECEIVE_SMS), SMS_REQUEST_CODE
            )
        }

        fbLoginInitialization()
        googleLoginInitialization()

        setUI()


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        Log.i("Request Code", "$requestCode")
        super.onActivityResult(requestCode, resultCode, data)

        when (requestCode) {
            GOOGLE_SIGN_IN -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                handleGoogleSignInResult(task)
            }
            FB_SIGN_IN -> {
                callbackManager.onActivityResult(requestCode, resultCode, data)
            }
        }

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.sign_in_button -> {
                val signInIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signInIntent, GOOGLE_SIGN_IN)
            }
        }

    }

    private fun handleGoogleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val token = account?.idToken

            Toast.makeText(this, "Signed in", Toast.LENGTH_SHORT).show()
            goToHomeActivity(token!!)

        } catch (e: ApiException) {
            Toast.makeText(this, "Sign in failed", Toast.LENGTH_SHORT).show()
            Log.i("STATUS", e.localizedMessage!!)
        }
    }

    private fun fbLoginInitialization() {
        FacebookSdk.sdkInitialize(this)
        callbackManager = CallbackManager.Factory.create()
    }

    private fun googleLoginInitialization() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.client))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun setUI() {
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        binding.signInButton.setOnClickListener(this)
        binding.loginButton.setReadPermissions(listOf("email"))
        binding.loginButton.registerCallback(callbackManager,
            object : FacebookCallback<LoginResult?> {
                override fun onSuccess(loginResult: LoginResult?) {
                    Log.i("FBLogin", "Success")
                    val token = loginResult?.accessToken?.token
                    Toast.makeText(this@LoginActivity, "Signed in", Toast.LENGTH_SHORT).show()

                    goToHomeActivity(token!!)
                }

                override fun onCancel() {
                    Toast.makeText(this@LoginActivity, "Sign-in cancelled", Toast.LENGTH_SHORT)
                        .show()

                    Log.i("FBLogin", "Cancelled")
                }

                override fun onError(exception: FacebookException) {
                    Toast.makeText(this@LoginActivity, "Signed in failed", Toast.LENGTH_SHORT)
                        .show()
                    Log.i("FBLogin Error", "Exception : ${exception.message}")
                }
            })
    }

    private fun goToHomeActivity(token: String) {
        Utils.setAuthToken(this, token)

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)

        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                    this, arrayOf(Manifest.permission.RECEIVE_SMS), SMS_REQUEST_CODE
                )
            }
//            else {
//                val intent = Intent(this, MainActivity::class.java)
//                startActivity(intent)
//            }
        }
    }

}
