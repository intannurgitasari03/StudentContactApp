package com.example.studentcontactapp

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.entity.StudentEntity
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {

    private lateinit var adapter: StudentsAdapter
    private val studentList = mutableListOf<StudentEntity>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val rvStudents = view.findViewById<RecyclerView>(R.id.rvStudents)
        val fabAdd = view.findViewById<FloatingActionButton>(R.id.fabAdd)

        adapter = StudentsAdapter(
            studentList,
            onEditClick = { student ->
                val intent = Intent(requireContext(), AddStudentActivity::class.java)
                intent.putExtra("STUDENT_ID", student.id)
                startActivity(intent)
            },
            onDeleteClick = { student ->
                showDeleteDialog(student)
            },
            onItemClick = { student ->
                val intent = Intent(requireContext(), DetailActivity::class.java)
                intent.putExtra("name", student.name)
                intent.putExtra("nim", student.nim)
                intent.putExtra("major", student.prodi)
                startActivity(intent)
            }
        )

        rvStudents.layoutManager = LinearLayoutManager(requireContext())
        rvStudents.adapter = adapter

        fabAdd.setOnClickListener {
            val intent = Intent(requireContext(), AddStudentActivity::class.java)
            startActivity(intent)
        }

        observeStudents()

        return view
    }

    private fun observeStudents() {
        val db = AppDatabase.getInstance(requireContext())
        lifecycleScope.launch {
            db.studentDao().getAllStudents().collectLatest { students ->
                studentList.clear()
                studentList.addAll(students)
                adapter.notifyDataSetChanged()
            }
        }
    }

    private fun showDeleteDialog(student: StudentEntity) {
        val builder = AlertDialog.Builder(requireContext())
        val dialogView = layoutInflater.inflate(R.layout.dialog_delete, null)
        builder.setView(dialogView)

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        val btnHapus = dialogView.findViewById<View>(R.id.btnHapus)
        val btnBatal = dialogView.findViewById<View>(R.id.btnBatal)
        val tvMessage = dialogView.findViewById<TextView>(R.id.tvDeleteMessage)
        
        tvMessage.text = "Hapus \"${student.name}\"? Tindakan ini tidak dapat dibatalkan."

        btnHapus.setOnClickListener {
            lifecycleScope.launch {
                AppDatabase.getInstance(requireContext()).studentDao().deleteById(student.id)
                dialog.dismiss()
            }
        }

        btnBatal.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show()
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.supportActionBar?.title = "Student Directory"
    }
}
