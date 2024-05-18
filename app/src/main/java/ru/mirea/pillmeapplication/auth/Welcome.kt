package ru.mirea.pillmeapplication.auth

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import ru.mirea.pillmeapplication.MainActivity
import ru.mirea.pillmeapplication.databinding.ActivityWelcomeBinding

class Welcome : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityWelcomeBinding

    private lateinit var tvWelcome: TextView
    private lateinit var btnCont: Button


    companion object {
        const val NOTIFICATION_ID = 101
        const val CHANNEL_ID = "channelID"
    }

    val TAG = this::class.java.simpleName

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        FirebaseApp.initializeApp(this)
        val db = FirebaseFirestore.getInstance()

        auth = Firebase.auth
        val user = auth.currentUser

        tvWelcome = binding.tvWelcome
        btnCont = binding.btnContinue



        if (user != null) {
            db.collection("users").document(user.uid!!).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.getString("name")
                        tvWelcome.setText("Добро пожаловать, $name")
                    } else {
                        Log.e(TAG, "пользователь в базе данных не найден")
                    }
                }
                .addOnFailureListener { e ->
                    // Ошибка при получении данных пользователя
                }
        } else {
            Log.e(TAG, "PROBLEMA при нахождении пользователя регистрации")
        }

        btnCont.setOnClickListener {
            checkNotificationPermission()
        }

    }


    private fun checkNotificationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS)
            != PackageManager.PERMISSION_GRANTED
        ) {

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS), 12
            )

        } else {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}