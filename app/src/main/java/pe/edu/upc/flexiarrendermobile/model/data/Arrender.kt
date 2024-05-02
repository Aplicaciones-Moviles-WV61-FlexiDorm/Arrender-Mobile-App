package pe.edu.upc.flexiarrendermobile.model.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.util.Date

data class ApiResponse(
    val message: String,

    val status: String,

    val data: Arrender
)


data class Arrender(
    val userId: Number,
    val username: String,
    val firstname: String,
    val lastname: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val birthDate: String,
    val profilePicture: String,
    val gender: String,
    val token:String,
    val verifier: Boolean,
)


data class RequestSignUpArrenderState(
    var firstname: MutableState<String> = mutableStateOf(""),
   var lastname: MutableState<String> = mutableStateOf(""),
   var username: MutableState<String> = mutableStateOf(""),
   var phoneNumber: MutableState<String> = mutableStateOf(""),
   var email: MutableState<String> = mutableStateOf(""),
   var password: MutableState<String> = mutableStateOf(""),
    var address: MutableState<String> = mutableStateOf(""),
    var birthDate: MutableState<String> = mutableStateOf(""),
    var profilePicture: MutableState<String> = mutableStateOf("https://previews.123rf.com/images/yupiramos/yupiramos1709/yupiramos170930842/87002967-ilustraci%C3%B3n-de-vector-de-dibujos-animados-de-persona-de-personaje-joven-de-hombre-de-retrato.jpg"),//aca debo pasarle la iamgen
    var gender: MutableState<String> = mutableStateOf("")
)
data class RequestSignUpArrenderBody(
    var firstname: String,
    var lastname: String,
    var username: String,
    var phoneNumber: String,
    var email: String,
    var password: String,
    var address: String,
    var birthDate: String,
    var profilePicture: String,
    var gender: String
)






data class RequestSignInArrenderState(
    var email: MutableState<String> = mutableStateOf(""),
    var password: MutableState<String> = mutableStateOf("")
)

data class RequestSignInArrenderBody(
    var email: String,
    var password: String
)