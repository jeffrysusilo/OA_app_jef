package com.example.sinauopencvkotlin.auth

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sinauopencvkotlin.R
import com.google.firebase.firestore.FirebaseFirestore

class DiscussionDetailActivity : AppCompatActivity() {

    private lateinit var title: TextView
    private lateinit var content: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var commentInput: EditText
    private lateinit var sendButton: Button
    private val db = FirebaseFirestore.getInstance()
    private lateinit var discussionId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_discussion_detail)

        title = findViewById(R.id.textView_detail_title)
        content = findViewById(R.id.textView_detail_content)
        recyclerView = findViewById(R.id.recyclerView_comments)
        commentInput = findViewById(R.id.editText_comment)
        sendButton = findViewById(R.id.button_send_comment)

        discussionId = intent.getStringExtra("DISCUSSION_ID") ?: ""

        recyclerView.layoutManager = LinearLayoutManager(this)

        db.collection("Discussions").document(discussionId).get().addOnSuccessListener { document ->
            title.text = document.getString("title")
            content.text = document.getString("content")
        }

        db.collection("Discussions").document(discussionId).collection("Comments")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val comments = snapshot.documents
                    recyclerView.adapter = CommentAdapter(comments)
                }
            }

        sendButton.setOnClickListener {
            val commentText = commentInput.text.toString()
            if (commentText.isNotEmpty()) {
                val comment = hashMapOf(
                    "content" to commentText,
                    "authorId" to "UserID",
                    "timestamp" to System.currentTimeMillis().toString()
                )
                db.collection("Discussions").document(discussionId)
                    .collection("Comments").add(comment)
                commentInput.text.clear()
            }
        }
    }
}
