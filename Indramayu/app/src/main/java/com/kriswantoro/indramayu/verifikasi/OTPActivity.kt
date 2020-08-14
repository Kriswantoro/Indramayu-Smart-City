package com.kriswantoro.indramayu.verifikasi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.tasks.TaskExecutors
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.kriswantoro.indramayu.MainActivity
import com.kriswantoro.indramayu.R
import kotlinx.android.synthetic.main.activity_o_t_p.*
import java.util.concurrent.TimeUnit

class OTPActivity : AppCompatActivity() {

    private var verificationId: String? = null
    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_o_t_p)

        mAuth = FirebaseAuth.getInstance()

        val phoneNumber = intent.getStringExtra("phoneNumber")
        sendVerificationCode(phoneNumber)

        // save phone number
        val prefs = applicationContext.getSharedPreferences(
            "USER_PREF",
            Context.MODE_PRIVATE
        )
        val editor = prefs.edit()
        editor.putString("phoneNumber", phoneNumber)
        editor.apply()

        buttonSignIn.setOnClickListener {

            val code = editTextCode.text.toString().trim { it <= ' ' }

            if (code.isEmpty() || code.length < 6) {
                editTextCode.error = "Enter code..."
                editTextCode.requestFocus()
                return@setOnClickListener
            }
            verifyCode(code)

        }

        buttonSignIn.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
    private fun sendVerificationCode(number: String) {

//        progressBar.setVisibility(View.VISIBLE)
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            number,
            60,
            TimeUnit.SECONDS,
            TaskExecutors.MAIN_THREAD,
            mCallBack
        )

//        progressBar.setVisibility(View.GONE)


    }
    private fun verifyCode(code: String) {

        val credential = PhoneAuthProvider.getCredential(verificationId.toString(), code)
        signInWithCredential(credential)

    }
    private fun signInWithCredential(credential: PhoneAuthCredential) {

        mAuth!!.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
//                    val session = UserSession(applicationContext)
//                    session.isLogin = true
                    val intent = Intent(this, MainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    this.startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        task.exception!!.message,
                        Toast.LENGTH_LONG
                    ).show()
//                    progressBar.setVisibility(View.GONE)
                }
            }

    }

    private val mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(
                s: String,
                forceResendingToken: PhoneAuthProvider.ForceResendingToken
            ) {
                super.onCodeSent(s, forceResendingToken)
                verificationId = s
            }

            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                val code = phoneAuthCredential.smsCode
                if (code != null) {
                    editTextCode.setText(code)
                    verifyCode(code)
                }
            }

            override fun onVerificationFailed(e: FirebaseException) {
                Toast.makeText(this@OTPActivity, e.message, Toast.LENGTH_LONG).show()
//                progressBar.setVisibility(View.GONE)
            }
        }
}

