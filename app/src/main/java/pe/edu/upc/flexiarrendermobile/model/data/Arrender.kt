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
    val firstname: String,
    val lastname: String,
    val username: String,
    val phoneNumber: String,
    val email: String,
    val address: String,
    val birthDate: Date,
    val profilePicture: String,
    val gender: String,
    val verifier: Boolean,
)


data class RequestSignUpArrender(
    var firstname: MutableState<String> = mutableStateOf(""),
    var lastname: MutableState<String> = mutableStateOf(""),
    var username: MutableState<String> = mutableStateOf(""),
    var phoneNumber: MutableState<String> = mutableStateOf(""),
    var email: MutableState<String> = mutableStateOf(""),
    var password: MutableState<String> = mutableStateOf(""),
    var address: MutableState<String> = mutableStateOf(""),
    var birthDate: MutableState<Date> = mutableStateOf(Date()),
    var profilePicture: MutableState<String> = mutableStateOf(""),
    var gender: MutableState<String> = mutableStateOf("")
)
