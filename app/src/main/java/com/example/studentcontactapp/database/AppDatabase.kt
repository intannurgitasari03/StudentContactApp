package com.example.studentcontactapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.studentcontactapp.database.dao.StudentDao
import com.example.studentcontactapp.database.entity.StudentEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [StudentEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun studentDao(): StudentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "student_database"
                )
                .addCallback(object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        INSTANCE?.let { database ->
                            CoroutineScope(Dispatchers.IO).launch {
                                val dao = database.studentDao()
                                dao.insertAll(listOf(
                                    StudentEntity(name = "Ahmad Fauzi", nim = "2024001", prodi = "T. Informatika", email = "ahmad@mail.com", semester = "5"),
                                    StudentEntity(name = "Budi Santoso", nim = "2024002", prodi = "T. Informatika", email = "budi@mail.com", semester = "3"),
                                    StudentEntity(name = "Clara Wijaya", nim = "2024003", prodi = "Sistem Informasi", email = "clara@mail.com", semester = "7")
                                ))
                            }
                        }
                    }
                })
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
