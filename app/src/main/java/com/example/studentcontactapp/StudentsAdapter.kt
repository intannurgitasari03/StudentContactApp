package com.example.studentcontactapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.studentcontactapp.database.entity.StudentEntity
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView

class StudentsAdapter(
    private val students: List<StudentEntity>,
    private val onEditClick: (StudentEntity) -> Unit,
    private val onDeleteClick: (StudentEntity) -> Unit,
    private val onItemClick: (StudentEntity) -> Unit
) : RecyclerView.Adapter<StudentsAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvInitial: TextView = view.findViewById(R.id.tvInitial)
        val tvName: TextView = view.findViewById(R.id.tvName)
        val tvNim: TextView = view.findViewById(R.id.tvNim)
        val cvAvatar: MaterialCardView = view.findViewById(R.id.cvAvatar)
        val btnEdit: MaterialButton = view.findViewById(R.id.btnEdit)
        val btnDelete: MaterialButton = view.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_student, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val student = students[position]
        holder.tvName.text = student.name
        holder.tvNim.text = student.nim
        
        // Ambil inisial (misal: "Ahmad Fauzi" -> "AF")
        val initials = student.name.split(" ")
            .mapNotNull { it.firstOrNull()?.toString() }
            .take(2)
            .joinToString("")
            .uppercase()
        holder.tvInitial.text = initials

        // Warna avatar berdasarkan inisial agar mirip screenshot
        val color = when (initials) {
            "AF" -> holder.itemView.context.getColor(R.color.circleAF)
            "BS" -> holder.itemView.context.getColor(R.color.circleBS)
            "CW" -> holder.itemView.context.getColor(R.color.circleCW)
            else -> holder.itemView.context.getColor(R.color.primaryBlue)
        }
        holder.cvAvatar.setCardBackgroundColor(color)

        holder.btnEdit.setOnClickListener { onEditClick(student) }
        holder.btnDelete.setOnClickListener { onDeleteClick(student) }
        holder.itemView.setOnClickListener { onItemClick(student) }
    }

    override fun getItemCount() = students.size
}
