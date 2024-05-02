package pe.edu.upc.flexiarrendermobile.model.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ApiResponseRoom(
    val message: String,
    val status: String,
    val data: Room
)

data class Room(
    val roomId: Int,
    val title:String,
    val description: String,
    val address: String,
    val imageUrl:String,
    val price: Double,
    val nearUniversities:String,
    val arrenderId: Int

)



data class RegisterRoomState(
    val title : MutableState<String> = mutableStateOf(""),
    val description : MutableState<String> = mutableStateOf(""),
    val address : MutableState<String> = mutableStateOf(""),
    val imageUrl : MutableState<String> = mutableStateOf(""),
    val price : MutableState<String> = mutableStateOf(""),
    val nearUniversities : MutableState<String> = mutableStateOf(""),
    val arrenderId : MutableState<Int> = mutableStateOf(0)


)

data class RegisterRoomRequestBody(
    val title:String,
    val description: String,
    val address: String,
    val imageUrl:String,
    val price: Double,
    val nearUniversities:String,
    val arrenderId: Long
)