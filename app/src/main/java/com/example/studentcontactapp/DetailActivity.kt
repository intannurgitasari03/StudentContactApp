package com.example.studentcontactapp

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.example.studentcontactapp.database.AppDatabase
import com.example.studentcontactapp.utils.SettingsManager
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.launch
import java.io.File

class DetailActivity : AppCompatActivity() {

    private lateinit var etNote: EditText
    private lateinit var tvStatus: TextView
    private lateinit var tvName: TextView
    private lateinit var tvNimMajor: TextView
    private lateinit var tvInitials: TextView
    private lateinit var cvInitials: MaterialCardView
    private var filename: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        val settingsManager = SettingsManager(this)
        if (settingsManager.isDarkMode()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        tvName = findViewById(R.id.tvName)
        tvNimMajor = findViewById(R.id.tvNimMajor)
        tvInitials = findViewById(R.id.tvInitials)
        cvInitials = findViewById(R.id.cvInitials)
        etNote = findViewById(R.id.etNote)
        tvStatus = findViewById(R.id.tvStatus)
        val btnSave = findViewById<MaterialButton>(R.id.btnSave)
        val btnLoad = findViewById<MaterialButton>(R.id.btnLoad)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener { finish() }

        val nim = intent.getStringExtra("nim") ?: ""
        val nameIntent = intent.getStringExtra("name")
        val majorIntent = intent.getStringExtra("major")

        if (nameIntent != null) {
            updateUI(nameIntent, nim, majorIntent ?: "")
        } else {
            loadStudentFromDb(nim)
        }

        filename = "note_$nim.txt"

        btnSave.setOnClickListener { saveToFile() }
        btnLoad.setOnClickListener { loadFromFile() }
        
        loadFromFile()
    }

    private fun loadStudentFromDb(nim: String) {
        val db = AppDatabase.getInstance(this)
        lifecycleScope.launch {
            val student = db.studentDao().getStudentByNim(nim)
            student?.let {
                updateUI(it.name, it.nim, it.prodi)
            } ?: run {
                tvName.text = "Mahasiswa"
                tvNimMajor.text = nim
                tvInitials.text = "?"
            }
        }
    }

    private fun updateUI(name: String, nim: String, major: String) {
        tvName.text = name
        tvNimMajor.text = "$nim · $major"
        
        val initials = name.split(" ").filter { it.isNotEmpty() }.take(2)
            .map { it.first() }.joinToString("").uppercase()
        tvInitials.text = initials
        
        cvInitials.setCardBackgroundColor(when(initials) {
            "AF" -> getColor(R.color.circleAF)
            "BS" -> getColor(R.color.circleBS)
            "CW" -> getColor(R.color.circleCW)
            else -> getColor(R.color.primaryBlue)
        })
    }

    private fun saveToFile() {
        val content = etNote.text.toString()
        try {
            openFileOutput(filename, MODE_PRIVATE).use { it.write(content.toByteArray()) }
            updateStatus()
            Toast.makeText(this, "Catatan disimpan", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Gagal menyimpan", Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadFromFile() {
        try {
            val file = File(filesDir, filename)
            if (file.exists()) {
                etNote.setText(file.readText())
                updateStatus()
            } else {
                tvStatus.visibility = View.GONE
            }
        } catch (e: Exception) {
            tvStatus.visibility = View.GONE
        }
    }

    private fun updateStatus() {
        val file = File(filesDir, filename)
        if (file.exists()) {

            tvStatus.text = "✓ Tersimpan (${file.length()} bytes)"
            tvStatus.visibility = View.VISIBLE
            tvStatus.setTextColor(getColor(android.R.color.holo_green_dark))
        }
    }
}
