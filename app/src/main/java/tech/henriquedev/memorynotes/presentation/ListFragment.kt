package tech.henriquedev.memorynotes.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import tech.henriquedev.memorynotes.R
import tech.henriquedev.memorynotes.databinding.FragmentListBinding
import tech.henriquedev.memorynotes.databinding.FragmentNoteBinding
import tech.henriquedev.memorynotes.framework.ListViewModel

class ListFragment : Fragment() {
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    private val notesListAdapter = NotesListAdapter(arrayListOf())
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.notesListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = notesListAdapter
        }

        binding.addNote.setOnClickListener { goToNoteDetails() }

        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.notes.observe(viewLifecycleOwner, Observer { notes ->
            binding.loadingView.visibility = View.GONE
            binding.notesListView.visibility = View.VISIBLE
            notesListAdapter.updateNotes(notes.sortedByDescending { it.updateTime })
        })
    }

    private fun goToNoteDetails(id: Long = 0L) {
        val action = ListFragmentDirections.actionGoToNote()
        Navigation.findNavController(binding.notesListView).navigate(action)
    }

    override fun onResume() {
        super.onResume()
        viewModel.getNotes()
    }
}