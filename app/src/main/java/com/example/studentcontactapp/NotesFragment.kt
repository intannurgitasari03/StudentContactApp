package com.example.studentcontactapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

class NotesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        val rvNotes = view.findViewById<RecyclerView>(R.id.rvNotes)

        val filenames = requireContext().fileList().filter { 
            it.startsWith("note_") && it.endsWith(".txt")
        }

        rvNotes.adapter = NotesAdapter(requireContext(), filenames)

        return view
    }
}
