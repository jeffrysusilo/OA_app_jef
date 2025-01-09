package com.example.sinauopencvkotlin.auth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sinauopencvkotlin.R
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ForumActivity : AppCompatActivity(), DiscussionAdapter.OnItemClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: DiscussionAdapter
    private val db = FirebaseFirestore.getInstance()
    private lateinit var discussionsListener: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forum)

        recyclerView = findViewById(R.id.recyclerViewDiscussions)
        recyclerView.layoutManager = LinearLayoutManager(this)

        loadDiscussions()

        val buttonAddDiscussion = findViewById<Button>(R.id.buttonAddDiscussion)
        buttonAddDiscussion.setOnClickListener {
            val intent = Intent(this, AddDiscussionActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadDiscussions() {
        // Mendengarkan perubahan data secara real-time pada collection Discussions
        discussionsListener = db.collection("Discussions")
            .addSnapshotListener { snapshots, e ->
                if (e != null) {
                    // Jika ada error, tampilkan error atau lakukan log
                    return@addSnapshotListener
                }

                if (snapshots != null) {
                    val discussions = snapshots.documents
                    adapter = DiscussionAdapter(discussions, this)
                    recyclerView.adapter = adapter
                }
            }
    }

    override fun onItemClick(discussion: DocumentSnapshot) {
        val discussionId = discussion.id
        val intent = Intent(this, DiscussionDetailActivity::class.java)
        intent.putExtra("DISCUSSION_ID", discussionId)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        discussionsListener.remove() // Menghapus listener saat activity dihancurkan
    }
}
