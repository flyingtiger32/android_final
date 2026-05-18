package ad.cfp.androidpobprofinal2.data

import com.google.firebase.firestore.DocumentId

data class Cataleg(
    val nom:String="",
    val categoria:String="",
    val descripcio:String="",
    val valor:Double=0.0,
    var favorite: Boolean=false,
    @DocumentId
    val id:String="",
)