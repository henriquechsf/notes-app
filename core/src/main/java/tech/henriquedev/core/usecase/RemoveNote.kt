package tech.henriquedev.core.usecase

import tech.henriquedev.core.data.Note
import tech.henriquedev.core.repository.NoteRepository

class RemoveNote(private val noteRepository: NoteRepository) {
    suspend operator fun invoke(note: Note) = noteRepository.removeNote(note)
}