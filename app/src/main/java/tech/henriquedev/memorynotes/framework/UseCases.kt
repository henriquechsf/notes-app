package tech.henriquedev.memorynotes.framework

import tech.henriquedev.core.usecase.AddNote
import tech.henriquedev.core.usecase.GetAllNotes
import tech.henriquedev.core.usecase.GetNote
import tech.henriquedev.core.usecase.RemoveNote

data class UseCases(
    val addNote: AddNote,
    val getAllNotes: GetAllNotes,
    val getNote: GetNote,
    val removeNote: RemoveNote
)
