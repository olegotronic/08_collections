data class Note(
    val id: Int = 0,
    val ownerId: Int = 0,
    val title: String = "",
    val text: String = "",
    val date: Int = 0,
    var comments: Int = 0,
    val readComments: Int = 0,
    val viewUrl: String = "",
    val privacyView: Array<String>? = null,
    val privacyComment: Array<String>? = null,
    val canComment: Boolean = false,
    val textWiki: String = "",
    val isDeleted: Boolean = false,
)

data class NoteComment(
    val id : Int = 0,
    val ownerId: Int = 0,
    val count: Int = 0,
    val text: String = "",
    val canPost: Boolean = true,
    val groupsCanPost: Boolean = false,
    val canClose: Boolean = true,
    val canOpen: Boolean = true,
    var isDeleted: Boolean = false,
)
