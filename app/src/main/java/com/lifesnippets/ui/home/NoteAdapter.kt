package com.lifesnippets.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.jetbrains.handson.mpp.lifesnippets.R
import com.lifesnippets.data.Note

class NoteAdapter: RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    var data = listOf<Note>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }
    class ViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val noteText: TextView = itemView.findViewById(R.id.note_string)
        private val editImage: ImageView = itemView.findViewById(R.id.edit_image)

        fun bind(item: Note) {
            noteText.text = item.noteText
            if (noteText.text.isNotEmpty()) {
                editImage.setImageResource(R.drawable.pencil)
            }
            editImage.setOnClickListener {view ->
                val bundle = bundleOf("noteId" to item.noteId)
                view.findNavController().navigate(R.id.action_mainFragment_to_noteFragment, bundle)
            }
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = layoutInflater
                    .inflate(R.layout.list_item_note, parent, false)

                return ViewHolder(view)
            }
        }
    }
}