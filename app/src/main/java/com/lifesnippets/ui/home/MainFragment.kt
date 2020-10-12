package com.lifesnippets.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Keep
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import com.jetbrains.handson.mpp.lifesnippets.R
import com.jetbrains.handson.mpp.lifesnippets.databinding.MainFragmentBinding
import com.lifesnippets.data.NoteDatabase

@Keep
class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val binding: MainFragmentBinding = DataBindingUtil.inflate(
            inflater, R.layout.main_fragment, container, false)
        val application = requireNotNull(this.activity).application

        // Create an instance of the ViewModel
        val dataSource = NoteDatabase.getDatabase(application).noteDao()
        val viewModelFactory = MainViewModelFactory(dataSource, application)

        val viewModel =
            ViewModelProviders.of(
                this, viewModelFactory).get(MainViewModel::class.java)

        binding.floatingActionButton2.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mainFragment_to_noteFragment)
        }

        val adapter = NoteAdapter()
        binding.noteList.adapter = adapter

        // TODO: Assign notes to observe the database from the viewmodel
        viewModel.notes.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.data = it
            }
        })

        binding.lifecycleOwner = this
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // TODO: Use the ViewModel
    }

}
