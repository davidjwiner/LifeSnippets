package com.lifesnippets.data

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepository @Inject constructor(
    private val noteDao: NoteDao
) {
    suspend fun get(noteId: Long): Note {
        return noteDao.get(noteId)
    }

    fun insert(note: Note) {
        noteDao.insert(note)
    }

}
