package tech.henriquedev.memorynotes.presentation

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.henriquedev.core.data.Note
import tech.henriquedev.core.repository.NoteRepository
import tech.henriquedev.core.usecase.AddNote
import tech.henriquedev.core.usecase.GetAllNotes
import tech.henriquedev.core.usecase.GetNote
import tech.henriquedev.core.usecase.RemoveNote
import tech.henriquedev.memorynotes.framework.RoomNoteDataSource
import tech.henriquedev.memorynotes.framework.UseCases

class NoteViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    val repository = NoteRepository(RoomNoteDataSource(application))

    val useCases = UseCases(
        AddNote(repository),
        GetAllNotes(repository),
        GetNote(repository),
        RemoveNote(repository)
    )

    val saved = MutableLiveData<Boolean>()

    fun saveNote(note: Note) {
        coroutineScope.launch {
            useCases.addNote(note)
            saved.postValue(true)
        }
    }
}