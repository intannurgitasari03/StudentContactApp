package com.example.studentcontactapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SearchFragment : Fragment() {

    private lateinit var rvNotes: RecyclerView
    private lateinit var adapter: NotesAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_notes, container, false)
        rvNotes = view.findViewById(R.id.rvNotes)
        rvNotes.layoutManager = LinearLayoutManager(requireContext())
        
        updateNotesList()
        
        return view
    }

    private fun updateNotesList() {
        val filenames = requireContext().fileList().filter {
            it.startsWith("note_") && it.endsWith(".txt")
        }.sortedDescending()

        adapter = NotesAdapter(requireContext(), filenames)
        rvNotes.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        updateNotesList()
        (activity as? MainActivity)?.supportActionBar?.title = "Saved Notes"
    }
}
