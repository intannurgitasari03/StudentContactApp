package com.example.studentcontactapp.utils

import android.content.Context
import java.io.File

class FileHelper {
    companion object {
        fun saveNote(context: Context, studentNim: String, content: String) {
            val fileName = "note_$studentNim.txt"
            context.openFileOutput(fileName, Context.MODE_PRIVATE).use {
                it.write(content.toByteArray())
            }
        }

        fun loadNote(context: Context, studentNim: String): String {
            val fileName = "note_$studentNim.txt"
            val file = File(context.filesDir, fileName)
            if (!file.exists()) return ""
            return context.openFileInput(fileName).bufferedReader().use { it.readText() }
        }

        fun deleteNote(context: Context, studentNim: String) {
            val fileName = "note_$studentNim.txt"
            val file = File(context.filesDir, fileName)
            if (file.exists()) {
                file.delete()
            }
        }

        fun isNoteExists(context: Context, studentNim: String): Boolean {
            val fileName = "note_$studentNim.txt"
            return File(context.filesDir, fileName).exists()
        }

        fun getNoteSize(context: Context, studentNim: String): Long {
            val fileName = "note_$studentNim.txt"
            return File(context.filesDir, fileName).length()
        }
    }
}
