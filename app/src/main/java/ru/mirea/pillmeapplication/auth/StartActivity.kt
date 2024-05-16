package ru.mirea.pillmeapplication.auth

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import ru.mirea.pillmeapplication.MainActivity
import ru.mirea.pillmeapplication.databinding.ActivityStartBinding

class StartActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding : ActivityStartBinding

    private lateinit var etEmail : EditText
    private lateinit var etPassword : EditText
    private lateinit var btnCreateNew : Button
    private lateinit var btnSignUp : Button

    private var email: String? = null
    private var password: String? = null

    val TAG = MainActivity::class.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase Auth
        auth = Firebase.auth
        init()
        btnCreateNew.setOnClickListener {
            if(valid()){
                Log.d(TAG, "Имейл $email Пас ворд $password")
                createNewAccount(email!!, password!!, this)
            } else {
                Toast.makeText(this, "Данные введены неверно", Toast.LENGTH_LONG).show()
            }
        }


        btnSignUp.setOnClickListener {
            if(valid()){
                Log.d(TAG, "Имейл $email Пас ворд $password")
                signUp(email!!, password!!, this)
            } else {
                Toast.makeText(this, "Данные введены неверно", Toast.LENGTH_LONG).show()
            }
        }


    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        if (currentUser != null) {
            reload(currentUser)
        }
    }
    private fun init(){
        etEmail = binding.etEmail
        etPassword = binding.etPassword
        btnCreateNew = binding.btnSignIn
        btnSignUp = binding.btnSignUp

    }

    private fun valid():Boolean {
        if (etEmail.text.toString() != null && etPassword.text.toString() != null){
            email = etEmail.text.toString()
            password = etPassword.text.toString()

            return true
        } else {
            return false
        }
    }
    private fun reload(currentUser: FirebaseUser) {
        Log.d(TAG, "reload")
        val user = getCurrentUser(currentUser)
        updateUI(user)
    }

    private fun createNewAccount(email: String, password: String, context: Context){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    Log.d(TAG, "current user email = " + (user?.email ?: null))
                    val intent = Intent(this, Intro ::class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun updateUI(user: AppUser?) {
        Log.d(TAG, "updateUI")
    }

    private fun signUp(email: String, password: String, context: Context){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithEmail:success")
                    val intent = Intent(this, Welcome :: class.java)
                    startActivity(intent)

                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        baseContext,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()

                }
            }

    }
    private fun getCurrentUser(currentUser: FirebaseUser): AppUser? {
        val user = Firebase.auth.currentUser
        user?.let {
            // Name, email address, and profile photo Url
            val name = it.displayName
            val email = it.email
            val photoUrl = it.photoUrl

            // Check if user's email is verified
            val emailVerified = it.isEmailVerified

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid = it.uid
            val appUser = AppUser(uid, name.toString(), email.toString())
            return appUser
        }
        return null
    }
}