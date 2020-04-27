package com.lifesnippets.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lifesnippets.data.Note
import com.lifesnippets.data.NoteDatabase
import com.lifesnippets.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NoteViewModel (application: Application) : AndroidViewModel(application) {
    private val repository: NoteRepository

    private val _note = MutableLiveData<Note>()
    val note: LiveData<Note>
        get() = _note

    private val _eventNoteSubmitted = MutableLiveData<Boolean>()
    val eventNoteSubmitted: LiveData<Boolean>
        get() = _eventNoteSubmitted

    init {
        val noteDao = NoteDatabase.getDatabase(application).noteDao()
        repository = NoteRepository(noteDao)
        _note.value = Note(noteText = "") // key is auto-generated
    }

    fun onSubmit() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                Log.i("NoteViewModel", "Note content is ${note.value}")
                val noteToInsert = note.value ?: return@withContext
                insert(noteToInsert)
            }
        }
        _eventNoteSubmitted.value = true
    }

    private fun insert(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(note)
    }
}