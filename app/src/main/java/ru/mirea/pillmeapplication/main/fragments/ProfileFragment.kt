package ru.mirea.pillmeapplication.main.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import ru.mirea.pillmeapplication.R

class ProfileFragment : Fragment() {
    private lateinit var tvProfileName: TextView
    private lateinit var auth: FirebaseAuth

    private val TAG: String = this::class.java.name
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        auth = Firebase.auth
//        val user = auth.currentUser
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        tvProfileName = view.findViewById(R.id.tvProfileName)
        FirebaseApp.initializeApp(context!!)
        val db = FirebaseFirestore.getInstance()

        auth = Firebase.auth
        val user = auth.currentUser
        if (user != null) {
            db.collection("users").document(user.uid!!).get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val name = document.getString("name")
                        tvProfileName.setText(name)
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

        return view
    }


}