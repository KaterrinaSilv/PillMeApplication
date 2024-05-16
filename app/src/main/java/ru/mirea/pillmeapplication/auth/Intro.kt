package ru.mirea.pillmeapplication.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import ru.mirea.pillmeapplication.databinding.ActivityIntroBinding

class Intro : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityIntroBinding

    private lateinit var etName: EditText
    private lateinit var btnCont: Button

    private var name: String? = null
    private var email: String? = null
    private var userID: String? = null

    val TAG = this::class.java.simpleName;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        val db = FirebaseFirestore.getInstance()

        etName = binding.etNameIntro
        btnCont = binding.btnContinueIntro

//        val user = intent.getParcelableExtra<FirebaseUser>("user")
        auth = Firebase.auth
        val user = auth.currentUser

        btnCont.setOnClickListener {
            name = etName.text.toString()
            if (user != null && name != null) {
                email = user.email
                userID = user.uid
                Log.d(TAG, "email = $email, id = $userID")
                // Добавить нового пользователя
                val userData = mapOf(
                    "name" to name, "email" to email
                )
                db.collection("users").document(userID!!).set(userData)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Log.d(TAG, "User ADD")
                            val intent = Intent(this, Welcome::class.java)
                            startActivity(intent)

                        } else {
                            Log.e(TAG, "PROBLEMA!")
                        }
                    }
            } else {
                Toast.makeText(
                    baseContext,
                    "Данные введены неверно",
                    Toast.LENGTH_SHORT,
                ).show()
            }
        }
    }
}