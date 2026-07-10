package com.example.studentcontactapp

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class NotesAdapter(
    private val context: Context, 
    private val fileNames: List<String>
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvFileName: TextView = view.findViewById(R.id.tvFileName)
        val tvFileSize: TextView = view.findViewById(R.id.tvFileSize)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_note, parent, false)
        return NoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val fileName = fileNames[position]
        val file = File(context.filesDir, fileName)
        
        // Format nama file: note_2024001.txt -> 2024001
        val nim = fileName.replace("note_", "").replace(".txt", "")
        
        holder.tvFileName.text = fileName
        holder.tvFileSize.text = "Size: ${file.length()} bytes"

        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra("nim", nim)
            // Nama akan dicari di DetailActivity atau di-load dari file
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = fileNames.size
}
