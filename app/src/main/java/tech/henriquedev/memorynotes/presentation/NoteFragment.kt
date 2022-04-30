package tech.henriquedev.memorynotes.presentation

import android.inputmethodservice.InputMethodService
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import tech.henriquedev.core.data.Note
import tech.henriquedev.memorynotes.R
import tech.henriquedev.memorynotes.databinding.FragmentNoteBinding
import tech.henriquedev.memorynotes.framework.NoteViewModel

class NoteFragment : Fragment() {
    private var _binding: FragmentNoteBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: NoteViewModel
    private var currentNote = Note(title = "", content = "", creationTime = 0L, updateTime = 0L)

    private var noteId = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(NoteViewModel::class.java)

        arguments?.let {
            noteId = NoteFragmentArgs.fromBundle(it).noteId
        }

        if (noteId != 0L) {
            viewModel.getNote(noteId)
        }

        binding.fabCheck.setOnClickListener {
            if (binding.edtTitle.text.toString() != "" || binding.edtContent.text.toString() != "") {
                val time = System.currentTimeMillis()
                currentNote.title = binding.edtTitle.text.toString()
                currentNote.content = binding.edtContent.text.toString()
                currentNote.updateTime = time
                if (currentNote.id == 0L) {
                    currentNote.creationTime = time
                }
                viewModel.saveNote(currentNote)
            } else {
                Navigation.findNavController(it).popBackStack()
            }
        }

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.saved.observe(viewLifecycleOwner, Observer { isSaved ->
            if (isSaved) {
                Toast.makeText(context, "Sucesso!", Toast.LENGTH_SHORT).show()
                hideKeyBoard()
                Navigation.findNavController(binding.edtTitle).popBackStack()
            } else {
                Toast.makeText(context, "Algo deu errado, tente novamente.", Toast.LENGTH_SHORT).show()
            }
        })

        viewModel.currentNote.observe(viewLifecycleOwner, Observer { note ->
            note?.let {
                currentNote = it
                binding.edtTitle.setText(it.title, TextView.BufferType.EDITABLE)
                binding.edtContent.setText(it.content, TextView.BufferType.EDITABLE)
            }
        })
    }

    private fun hideKeyBoard() {
        val imm = context?.getSystemService(InputMethodService.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.edtTitle.windowToken, 0)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.note_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.delete_note -> {
                if (context != null && noteId != 0L) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Remover nota")
                        .setMessage("Tem certeza que deseja remover a nota?")
                        .setPositiveButton("Sim") { dialogInterface, i ->
                            viewModel.deleteNote(currentNote)
                        }
                        .setNegativeButton("Não") { dialogInterface, i -> }
                        .create()
                        .show()
                }
            }
        }

        return true
    }
}