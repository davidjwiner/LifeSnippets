package com.lifesnippets.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "note_table")
data class Note(
    @PrimaryKey(autoGenerate = true)
    val noteId: Long = 0L,

    @ColumnInfo(name = "note_text")
    var noteText: String,

    @ColumnInfo(name = "note_date")
    var noteDate: Date
)