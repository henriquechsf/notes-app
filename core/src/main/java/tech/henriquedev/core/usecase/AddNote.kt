package tech.henriquedev.core.usecase

import tech.henriquedev.core.data.Note
import tech.henriquedev.core.repository.NoteRepository

class AddNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.addNote(note)
}