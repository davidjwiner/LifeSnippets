package com.lifesnippets.noteeditor

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.lifesnippets.data.Note
import com.lifesnippets.data.NoteDatabase
import com.lifesnippets.data.NoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.DateFormat
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

    val dateFormatted : LiveData<String> = Transformations.map(_note) {
        val formatter = DateFormat.getDateInstance()
        formatter.timeZone = TimeZone.getTimeZone("UTC")
        it?.let {
            formatter.format(it.noteDate)
        }
    }

    // TODO: figure out a better way to do this
    fun updateDate(epochDate: Long) {
        _note.value?.let {
            _note.value = Note(it.noteId, it.noteText, Date(epochDate))
        }
    }
}