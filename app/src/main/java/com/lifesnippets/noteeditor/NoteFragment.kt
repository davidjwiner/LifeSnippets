package com.lifesnippets.noteeditor

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.google.android.material.datepicker.MaterialDatePicker
import com.jetbrains.handson.mpp.lifesnippets.R
import com.jetbrains.handson.mpp.lifesnippets.databinding.NoteFragmentBinding

class NoteFragment : Fragment() {

    private var datePicker: MaterialDatePicker<Long>

    init {
        val datePickerBuilder = MaterialDatePicker.Builder.datePicker()
        datePicker = datePickerBuilder.build()
    }

    private val viewModel: NoteViewModel by viewModels {
        val existingNote = this.arguments?.containsKey("noteId")
        if (existingNote != null) {
            val noteId: Long =  this.requireArguments().get("noteId") as Long
            return@viewModels NoteViewModelFactory(requireActivity().application, noteId)
        } else {
            return@viewModels NoteViewModelFactory(requireActivity().application, -1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: NoteFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.note_fragment, container, false)
        viewModel.eventNoteSubmitted.observe(viewLifecycleOwner, {
            noteSubmitted()
        })
        binding.noteViewModel = viewModel
        binding.dateButton.setOnClickListener {
            datePicker.show(requireActivity().supportFragmentManager, datePicker.toString())
        }
        datePicker.addOnPositiveButtonClickListener {
            viewModel.updateDate(it)
        }
        binding.lifecycleOwner = this
        return binding.root

    }
    private fun noteSubmitted() {
        Toast.makeText(
            this.context,
            "Added note ${viewModel.note.value?.noteText}",
            Toast.LENGTH_SHORT
        ).show()
        Navigation.findNavController(this.requireView()).navigate(R.id.action_noteFragment_to_mainFragment)
    }

}
