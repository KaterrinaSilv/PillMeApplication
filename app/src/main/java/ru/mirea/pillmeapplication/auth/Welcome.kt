package ru.mirea.pillmeapplication.auth

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import ru.mirea.pillmeapplication.R
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
            Toast.makeText(
                baseContext,
                "Буньк!",
                Toast.LENGTH_SHORT,
            ).show()

            // Создаём уведомление
            val builder = NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.status)
                .setContentTitle("PILLME")
                .setContentText("Магний ")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val notificationManager = NotificationManagerCompat.from(this)
            notificationManager.notify(NOTIFICATION_ID, builder.build())
            Log.d(TAG, "Notify?")

        }
    }
}