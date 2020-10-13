package com.lifesnippets.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.vipulasri.timelineview.TimelineView
import com.jetbrains.handson.mpp.lifesnippets.R
import com.lifesnippets.data.Note
import java.text.DateFormat
import java.util.*

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

    override fun getItemViewType(position: Int): Int {
        return TimelineView.getTimeLineViewType(position, itemCount)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val noteText: TextView = itemView.findViewById(R.id.note_string)
        private val noteDate: TextView = itemView.findViewById(R.id.date_text)
        private val editImage: ImageView = itemView.findViewById(R.id.edit_image)

        fun bind(item: Note) {
            noteText.text = item.noteText
            val formatter = DateFormat.getDateInstance()
            formatter.timeZone = TimeZone.getTimeZone("UTC")
            noteDate.text = formatter.format(item.noteDate)
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