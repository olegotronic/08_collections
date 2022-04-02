class NoteNotFoundException(message: String) : RuntimeException(message)
class NoteCommentNotFoundException(message: String) : RuntimeException(message)

class NoteService {

    private val notes = mutableListOf<Note>()
    private val comments = mutableListOf<NoteComment>()

    fun add(note: Note): Note {
        val newId = if (notes.isNotEmpty()) notes.last().id + 1 else 1
        notes += note.copy(id = newId)
        return notes.last()
    }

    fun createComment(noteId: Int, comment: NoteComment): NoteComment {
        for (note in notes) {
            if (note.id != noteId) continue

            val newCommentId = if (comments.isNotEmpty()) comments.last().id + 1 else 1
            comments.add(comment.copy(id = newCommentId, ownerId = noteId))
            return comments.last()
        }
        throw NoteNotFoundException("Note not found (id = $noteId)")
    }

    fun delete(noteId: Int) {
        for (note in notes) {
            if (note.id != noteId) continue

            notes.remove(note)

            val commentsToDelete = mutableListOf<NoteComment>()
            for (comment in comments) {
                if (comment.ownerId != noteId) continue
                commentsToDelete.add(comment)
            }

            for (commentToDelete in commentsToDelete) {
                comments.remove(commentToDelete)
            }

            return
        }
        throw NoteNotFoundException("Note not found (id = $noteId)")
    }

    fun deleteComment(noteId: Int, commentId: Int) {
        for (note in notes) {
            if (note.id != noteId) continue

            for (comment in comments) {
                if (comment.id != commentId || comment.ownerId != noteId || comment.isDeleted) continue

                comment.isDeleted = true
                return
            }
            throw NoteCommentNotFoundException("Comment not found (id = $commentId)")
        }
        throw NoteNotFoundException("Note not found (id = $noteId)")
    }

    fun edit(noteId: Int, title: String, text: String) {
        for (note in notes) {
            if (note.id != noteId) continue

            notes[notes.indexOf(note)] = note.copy(title = title, text = text)
            return
        }
        throw NoteNotFoundException("Note not found (id = $noteId)")
    }

    fun editComment(noteId: Int, commentId: Int, text: String) {
        for (note in notes) {
            if (note.id != noteId) continue

            for (comment in comments) {
                if (comment.id != commentId || comment.ownerId != noteId) continue

                if (comment.isDeleted) throw NoteCommentNotFoundException("Comment not found (id = $commentId)")

                comments[comments.indexOf(comment)] = comment.copy(text = text)
                return
            }
            throw NoteCommentNotFoundException("Comment not found (id = $commentId)")
        }
        throw NoteNotFoundException("Note not found (id = $noteId)")
    }

    fun get(noteIds: Array<Int>): MutableList<Note> {
        val notesByIds = mutableListOf<Note>()
        for (note in notes) {
            if (note.id !in noteIds) continue

            notesByIds.add(note)
        }

        return notesByIds
    }

    fun getById(noteId: Int): Note {
        for (note in notes) {
            if (note.id != noteId) continue
            return note
        }
        throw NoteNotFoundException("Note not found (id = $noteId)")
    }

    fun getComments(noteId: Int): MutableList<NoteComment> {
        val commentsById = mutableListOf<NoteComment>()

        for (note in notes) {
            if (note.id != noteId) continue

            for (comment in comments) {
                if (comment.ownerId != noteId || comment.isDeleted) continue

                commentsById.add(comment)
            }
            return commentsById
        }
        throw NoteNotFoundException("Note not found (id = $noteId)")
    }

    fun restoreComment(noteId: Int, commentId: Int) {
        for (note in notes) {
            if (note.id != noteId) continue

            for (comment in comments) {
                if (comment.id != commentId || comment.ownerId != noteId) continue
                if (!comment.isDeleted) throw NoteCommentNotFoundException("Deleted comment not found (id = $commentId)")

                comment.isDeleted = false
                return
            }
            throw NoteCommentNotFoundException("Comment not found (id = $commentId)")
        }
        throw NoteNotFoundException("Note not found (id = $noteId)")
    }

}