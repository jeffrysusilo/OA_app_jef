package com.example.sinauopencvkotlin.auth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.sinauopencvkotlin.R
import com.google.firebase.firestore.DocumentSnapshot

class DiscussionAdapter(
    private val discussions: List<DocumentSnapshot>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<DiscussionAdapter.DiscussionViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(discussion: DocumentSnapshot)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscussionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_discussion, parent, false)
        return DiscussionViewHolder(view)
    }

    override fun onBindViewHolder(holder: DiscussionViewHolder, position: Int) {
        val discussion = discussions[position]
        holder.bind(discussion)
        holder.itemView.setOnClickListener {
            listener.onItemClick(discussion)
        }
    }

    override fun getItemCount() = discussions.size

    class DiscussionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val title: TextView = itemView.findViewById(R.id.textView_title)
        private val content: TextView = itemView.findViewById(R.id.textView_content)

        fun bind(discussion: DocumentSnapshot) {
            title.text = discussion.getString("title")
            content.text = discussion.getString("content")
        }
    }
}
