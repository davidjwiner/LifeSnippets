package com.lifesnippets.data

import androidx.lifecycle.LiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    fun get(noteId: Long): LiveData<Note> {
        return noteDao.get(noteId)
    }

    fun insert(note: Note) {
        noteDao.insert(note)
    }

}
