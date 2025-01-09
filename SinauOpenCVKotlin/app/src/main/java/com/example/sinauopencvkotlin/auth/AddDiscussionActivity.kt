package com.example.sinauopencvkotlin.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.example.sinauopencvkotlin.R

class AddDiscussionActivity : AppCompatActivity() {

    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var buttonSubmitDiscussion: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_discussion)

        // Inisialisasi view
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextContent = findViewById(R.id.editTextContent)
        buttonSubmitDiscussion = findViewById(R.id.buttonSubmitDiscussion)

        // Set onClickListener untuk tombol submit
        buttonSubmitDiscussion.setOnClickListener {
            val title = editTextTitle.text.toString().trim()
            val content = editTextContent.text.toString().trim()

            // Validasi input
            if (title.isEmpty()) {
                editTextTitle.error = "Title is required"
                return@setOnClickListener
            }
            if (content.isEmpty()) {
                editTextContent.error = "Content is required"
                return@setOnClickListener
            }

            // Buat objek diskusi baru
            val discussion = hashMapOf(
                "title" to title,
                "content" to content,
                "creatorId" to "userID123",
                "timestamp" to System.currentTimeMillis()
            )

            // Tambahkan diskusi ke Firestore
            db.collection("Discussions")
                .add(discussion)
                .addOnSuccessListener {
                    Toast.makeText(this, "Discussion added successfully", Toast.LENGTH_SHORT).show()
                    finish() // Tutup Activity setelah sukses menambahkan
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to add discussion: ${e.message}", Toast.LENGTH_SHORT).show()
                }
        }
    }
}
