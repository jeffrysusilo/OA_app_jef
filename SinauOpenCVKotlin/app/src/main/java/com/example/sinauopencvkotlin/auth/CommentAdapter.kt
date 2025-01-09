package com.example.sinauopencvkotlin.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sinauopencvkotlin.R
import com.google.firebase.firestore.DocumentSnapshot

class CommentAdapter(private val comments: List<DocumentSnapshot>) :
    RecyclerView.Adapter<CommentAdapter.CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
        return CommentViewHolder(view)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        val comment = comments[position]
        holder.bind(comment)
    }

    override fun getItemCount() = comments.size

    class CommentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val content: TextView = itemView.findViewById(R.id.textView_comment_content)
        private val author: TextView = itemView.findViewById(R.id.textView_comment_author)
        private val timestamp: TextView = itemView.findViewById(R.id.textView_comment_timestamp)

        fun bind(comment: DocumentSnapshot) {
            content.text = comment.getString("content")
            author.text = comment.getString("authorId")
            timestamp.text = comment.getString("timestamp")
        }
    }
}
