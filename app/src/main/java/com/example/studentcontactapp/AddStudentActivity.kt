package com.example.studentcontactapp

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.database.entity.StudentEntity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import kotlinx.coroutines.launch

class AddStudentActivity : BaseActivity() {

    private lateinit var etName: EditText
    private lateinit var etNim: EditText
    private lateinit var spProdi: Spinner
    private lateinit var etEmail: EditText
    private lateinit var etSemester: EditText
    private lateinit var btnSave: MaterialButton
    private lateinit var toolbar: MaterialToolbar

    private var studentId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_student)

        etName = findViewById(R.id.etName)
        etNim = findViewById(R.id.etNim)
        spProdi = findViewById(R.id.spProdi)
        etEmail = findViewById(R.id.etEmail)
        etSemester = findViewById(R.id.etSemester)
        btnSave = findViewById(R.id.btnSave)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val prodiOptions = arrayOf("Pilih Prodi", "T. Informatika", "Sistem Informasi", "Teknik Elektro", "Manajemen")
        val adapter = object : ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, prodiOptions) {
            override fun isEnabled(position: Int): Boolean = position != 0
            override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getDropDownView(position, convertView, parent) as TextView
                if (position == 0) view.setTextColor(getColor(R.color.textSecondary))
                else view.setTextColor(getColor(R.color.textPrimary))
                return view
            }
        }
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spProdi.adapter = adapter

        studentId = intent.getIntExtra("STUDENT_ID", -1)

        if (studentId != -1) {
            toolbar.title = "Edit Mahasiswa"
            loadStudentData()
        }

        btnSave.setOnClickListener {
            saveStudent()
        }
    }

    private fun loadStudentData() {
        val db = AppDatabase.getInstance(this)
        lifecycleScope.launch {
            val student = db.studentDao().getStudentById(studentId)
            student?.let {
                etName.setText(it.name)
                etNim.setText(it.nim)
                etEmail.setText(it.email)
                etSemester.setText(it.semester)
                
                val prodiOptions = arrayOf("Pilih Prodi", "T. Informatika", "Sistem Informasi", "Teknik Elektro", "Manajemen")
                val index = prodiOptions.indexOf(it.prodi)
                if (index != -1) spProdi.setSelection(index)
            }
        }
    }

    private fun saveStudent() {
        val name = etName.text.toString()
        val nim = etNim.text.toString()
        val prodi = spProdi.selectedItem.toString()
        val email = etEmail.text.toString()
        val semester = etSemester.text.toString()

        if (name.isEmpty() || nim.isEmpty() || spProdi.selectedItemPosition == 0) {
            Toast.makeText(this, "Mohon lengkapi data", Toast.LENGTH_SHORT).show()
            return
        }

        val db = AppDatabase.getInstance(this)
        lifecycleScope.launch {
            val student = StudentEntity(
                id = if (studentId == -1) 0 else studentId,
                name = name,
                nim = nim,
                prodi = prodi,
                email = email,
                semester = semester
            )

            if (studentId == -1) db.studentDao().insert(student)
            else db.studentDao().update(student)
            
            finish()
        }
    }
}
