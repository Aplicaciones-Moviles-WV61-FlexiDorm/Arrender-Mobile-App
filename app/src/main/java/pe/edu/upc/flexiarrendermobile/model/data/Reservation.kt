package pe.edu.upc.flexiarrendermobile.model.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ApiResponseReservationList(
    val message: String,
    val status: String,
    val data: List<Reservation>
)

data class Reservation(
    val reservationId: Int,
    val date: String,
    val phone: String,
    val email: String,
    val observation: String,
    val totalPrice: Double,
    val hourInit: String,
    val hourFinal: String,
    val room: Int
)

//estado de reservaton
data class ReservationState(
    val reservationId: MutableState<Int> = mutableStateOf(0),
    val date: MutableState<String> = mutableStateOf(""),
    val phone: MutableState<String> = mutableStateOf(""),
    val email: MutableState<String> = mutableStateOf(""),
    val observation: MutableState<String> = mutableStateOf(""),
    val totalPrice: MutableState<Double> = mutableStateOf(0.0),
    val hourInit: MutableState<String> = mutableStateOf(""),
    val hourFinal: MutableState<String> = mutableStateOf(""),
    val roomTitle: MutableState<String> = mutableStateOf(""),

)