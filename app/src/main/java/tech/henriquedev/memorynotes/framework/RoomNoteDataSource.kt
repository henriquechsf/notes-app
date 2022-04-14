package tech.henriquedev.memorynotes.framework

import android.content.Context
import tech.henriquedev.core.data.Note
import tech.henriquedev.core.repository.NoteDataSource
import tech.henriquedev.memorynotes.framework.db.DatabaseService
import tech.henriquedev.memorynotes.framework.db.NoteEntity

class RoomNoteDataSource(context: Context) : NoteDataSource {
    val noteDao = DatabaseService.getInstance(context).noteDao()

    override suspend fun add(note: Note) = noteDao.addNoteEntity(NoteEntity.fromNote(note))

    override suspend fun get(id: Long): Note? = noteDao.getNoteEntity(id)?.toNote()

    override suspend fun getAll(): List<Note> = noteDao.getAllNoteEntities().map { it.toNote() }

    override suspend fun remove(note: Note) = noteDao.deleteNoteEntity(NoteEntity.fromNote(note))
}