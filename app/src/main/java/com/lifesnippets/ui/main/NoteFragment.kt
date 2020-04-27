package com.lifesnippets.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.jetbrains.handson.mpp.lifesnippets.R
import com.jetbrains.handson.mpp.lifesnippets.databinding.NoteFragmentBinding

class NoteFragment : Fragment() {

    companion object {
        fun newInstance() = NoteFragment()
    }

    private lateinit var viewModel: NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: NoteFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.note_fragment, container, false)
        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
        binding.lifecycleOwner = this.viewLifecycleOwner
        viewModel.eventNoteSubmitted.observe(viewLifecycleOwner, Observer<Boolean> { hasSubmitted ->
            noteSubmitted()
        })
        binding.noteViewModel = viewModel

        return binding.root
    }

    private fun noteSubmitted() {
        Toast.makeText(
            this.context,
            "Added note ${viewModel.note.value?.noteText}",
            Toast.LENGTH_SHORT
        ).show()
        Navigation.findNavController(this.view!!).navigate(R.id.action_noteFragment_to_mainFragment)
    }

}