package com.lifesnippets.noteeditor

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lifesnippets.data.Note
import com.lifesnippets.data.NoteDatabase
import com.lifesnippets.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class NoteViewModel(application: Application, noteId: Long = -1) : ViewModel() {
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

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                if (noteId > 0) {
                    _note.postValue(repository.get(noteId))
                } else {
                    _note.postValue(Note(noteText = "", noteDate = Date())) // key is auto-generated
                }
            }
        }
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

    fun getDateFormatted() : String {
        val formatter = SimpleDateFormat("dd/MM/yyyy")
        note.value?.let {
            return@getDateFormatted formatter.format(it.noteDate)
        }
        return formatter.format(Date())
    }

    fun updateDate(epochDate: Long) {
        note.value?.noteDate = Date(epochDate)
    }
}