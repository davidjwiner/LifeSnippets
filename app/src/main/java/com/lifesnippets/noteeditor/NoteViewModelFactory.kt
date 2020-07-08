package com.lifesnippets.noteeditor

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NoteViewModelFactory(
    private val application: Application,
    private val noteId: Long) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NoteViewModel::class.java)) {
            return NoteViewModel(application, noteId) as T
        }
        throw IllegalArgumentException("Unknown ViewModelFactory class")
    }
}
