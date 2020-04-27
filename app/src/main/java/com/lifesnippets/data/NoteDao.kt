package com.lifesnippets.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query

@Dao
interface NoteDao {
    @Insert(onConflict = REPLACE)
    fun insert(note: Note)

    @Query("SELECT * FROM note_table WHERE noteId = :key")
    fun get(key: Long): LiveData<Note>

    @Query("SELECT * FROM note_table ORDER BY noteId DESC")
    fun getAllNotes(): LiveData<List<Note>>
}