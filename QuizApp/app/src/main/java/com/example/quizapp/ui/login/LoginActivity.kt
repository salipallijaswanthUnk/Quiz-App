package com.example.quizapp.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.quizapp.databinding.ActivityLoginBinding
import com.example.quizapp.ui.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class LoginActivity : AppCompatActivity() {
    private lateinit var b: ActivityLoginBinding
    private val auth by lazy { FirebaseAuth.getInstance() }
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
        try {
            val account = task.getResult(ApiException::class.java)
            val idToken = account.idToken
            if (idToken != null) {
                val credential = GoogleAuthProvider.getCredential(idToken, null)
                auth.signInWithCredential(credential).addOnCompleteListener { authTask ->
                    if (authTask.isSuccessful) {
                        openHome()
                    } else {
                        Toast.makeText(this, "Firebase Auth failed: ${authTask.exception?.localizedMessage}. Continuing as guest...", Toast.LENGTH_LONG).show()
                        openHome()
                    }
                }
            } else {
                Toast.makeText(this, "Google Sign-In completed without ID Token. Continuing as guest...", Toast.LENGTH_LONG).show()
                openHome()
            }
        } catch (e: Exception) {
            val isCancelled = e is ApiException && e.statusCode == 12501
            if (isCancelled) {
                Toast.makeText(this, "Sign-in cancelled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Google Sign-In requires setup in Firebase Console. Continuing as guest...", Toast.LENGTH_LONG).show()
                openHome()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        b = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(b.root)

        if (auth.currentUser != null) {
            openHome()
            return
        }

        b.googleSignInButton.setOnClickListener { signIn() }
        b.guestButton.setOnClickListener { openHome() }
    }

    private fun signIn() {
        val clientId = getString(com.example.quizapp.R.string.default_web_client_id)
        val options = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(clientId)
            .requestEmail()
            .build()
        launcher.launch(GoogleSignIn.getClient(this, options).signInIntent)
    }

    private fun openHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
