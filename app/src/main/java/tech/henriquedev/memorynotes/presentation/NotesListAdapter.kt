package tech.henriquedev.memorynotes.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import tech.henriquedev.core.data.Note
import tech.henriquedev.memorynotes.R
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class NotesListAdapter(var notes: ArrayList<Note>): RecyclerView.Adapter<NotesListAdapter.NoteViewHolder>() {

    fun updateNotes(newNotes: List<Note>) {
        notes.clear()
        notes.addAll(newNotes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder = NoteViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
    )

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) = holder.bind(notes[position])

    override fun getItemCount(): Int = notes.size

    inner class NoteViewHolder(view: View): RecyclerView.ViewHolder(view) {
        private val layout = view.findViewById<CardView>(R.id.item_note_layout)
        private val noteTitle = view.findViewById<TextView>(R.id.text_title)
        private val noteContent = view.findViewById<TextView>(R.id.text_content)
        private val noteDate = view.findViewById<TextView>(R.id.text_date)

        fun bind(note: Note) {
            noteTitle.text = note.title
            noteContent.text = note.content

            val sdf = SimpleDateFormat("MMM dd, HH:mm:ss")
            val resultDate = Date(note.updateTime)
            noteDate.text = "Last updated: ${sdf.format(resultDate)}"
        }
    }
}