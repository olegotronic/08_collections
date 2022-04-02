import org.junit.Test
import org.junit.Assert.*

class NoteServiceTest {

    @Test
    fun add() {

        // arrange
        val noteService = NoteService()
        val firstNote = Note(title = "Title 1", text = "First note")
        val secondNote = Note(title = "Title 2", text = "Second note")

        // act
        val addedFirstNote = noteService.add(firstNote)
        val addedSecondNote = noteService.add(secondNote)
        val result = addedFirstNote.id

        // assert
        assertNotEquals(0, result)

    }

    @Test
    fun createComment() {

        // arrange
        val noteService = NoteService()
        val note = noteService.add(Note(text = "First note"))

        val noteId = note.id

        // act
        noteService.createComment(noteId = noteId, comment = NoteComment(text = "First comment"))

        // assert
        assertTrue(true)

    }

    @Test(expected = NoteNotFoundException::class)
    fun delete_ExistingId() {

        // arrange
        val noteService = NoteService()
        val note = noteService.add(Note(text = "First note"))
        val noteId = note.id

        // act
        noteService.createComment(noteId = noteId, comment = NoteComment(text = "First comment"))
        noteService.delete(noteId = noteId)

        val noteEdited = noteService.getById(noteId = noteId)

    }

    @Test(expected = NoteNotFoundException::class)
    fun delete_NotExistingId() {

        // arrange
        val noteService = NoteService()
        val note = noteService.add(Note(text = "First note"))
        val noteId = note.id + 1

        // act
        noteService.createComment(noteId = noteId, comment = NoteComment(text = "First comment"))
        noteService.delete(noteId = noteId)

    }

    @Test
    fun deleteComment_ExistingId() {

        // arrange
        val noteService = NoteService()
        val note = noteService.add(Note(text = "First note"))
        val noteId = note.id

        val comment = noteService.createComment(noteId = noteId, comment = NoteComment(text = "First comment"))
        val commentId = comment.id

        // act
        val commentsById1 = noteService.getComments(noteId = noteId)
        val result1 = commentsById1.size

        noteService.deleteComment(noteId = noteId, commentId = commentId)
        val commentsById0 = noteService.getComments(noteId = noteId)
        val result0 = commentsById0.size

        // assert
        assertEquals(1, result1)
        assertEquals(0, result0)

    }

    @Test(expected = NoteCommentNotFoundException::class)
    fun deleteComment_NotExistingId() {

        // arrange
        val noteService = NoteService()
        val note = noteService.add(Note(text = "First note"))
        val noteId = note.id

        val comment = noteService.createComment(noteId = noteId, comment = NoteComment(text = "First comment"))
        val commentId = comment.id + 1

        // act
        noteService.deleteComment(noteId = noteId, commentId = commentId)

    }

    @Test
    fun edit_ExistingId() {

        // arrange
        val noteService = NoteService()
        val newNote = Note(title = "Title 1", text = "Text 1")
        val note = noteService.add(newNote)
        val noteId = note.id

        // act
        noteService.edit(noteId = noteId, title = "Title edit", text = "Text edit")
        val noteEdited = noteService.getById(noteId = noteId)

        // assert
        assertEquals("Title edit", noteEdited.title)
        assertEquals("Text edit", noteEdited.text)

    }

    @Test(expected = NoteNotFoundException::class)
    fun edit_NotExistingId() {

        // arrange
        val noteService = NoteService()
        val newNote = Note(title = "Title 1", text = "Text 1")
        val note = noteService.add(newNote)
        val noteId = note.id + 1

        // act
        noteService.edit(noteId = noteId, title = "Title edit", text = "Text edit")
        val noteEdited = noteService.getById(noteId = noteId)

    }

    @Test
    fun editComment() {

        // arrange
        val noteService = NoteService()
        val note = noteService.add(Note(text = "First note"))
        val noteId = note.id

        val comment = noteService.createComment(noteId = noteId, comment = NoteComment(text = "First comment"))
        val commentId = comment.id

        // act
        noteService.editComment(noteId = noteId, commentId = commentId, text = "Edited comment")
        val commentsById = noteService.getComments(noteId = noteId)

        // assert
        assertEquals("Edited comment", commentsById[0].text)

    }

    @Test
    fun restoreComment() {

        // arrange
        val noteService = NoteService()
        val note = noteService.add(Note(text = "First note"))
        val noteId = note.id

        val comment = noteService.createComment(noteId = noteId, comment = NoteComment(text = "First comment"))
        val commentId = comment.id

        // act
        noteService.deleteComment(noteId = noteId, commentId = commentId)
        val commentsById0 = noteService.getComments(noteId = noteId)
        val result0 = commentsById0.size

        noteService.restoreComment(noteId = noteId, commentId = commentId)
        val commentsById1 = noteService.getComments(noteId = noteId)
        val result1 = commentsById1.size

        // assert
        assertEquals(1, result1)
        assertEquals(0, result0)

    }

}