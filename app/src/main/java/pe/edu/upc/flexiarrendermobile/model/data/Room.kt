package pe.edu.upc.flexiarrendermobile.model.data

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

data class ApiResponseRoom(
    val message: String,
    val status: String,
    val data: Room
)


data class ApiResponseRoomList(
    val message: String,
    val status: String,
    val data: List<Room>
)


data class Room(
    val roomId: Int,
    val title:String,
    val description: String,
    val address: String,
    val imageUrl:String,
    val price: Double,
    val nearUniversities:String,
    val latitude: Double,
    val longitude: Double,
    val arrenderId: Long

)

data class RoomUpdateState(
    val roomId: MutableState<Int> = mutableStateOf(0),
    val title : MutableState<String> = mutableStateOf(""),
    val description : MutableState<String> = mutableStateOf(""),
    val address : MutableState<String> = mutableStateOf(""),
    val price : MutableState<String> = mutableStateOf(""),
    val nearUniversities : MutableState<String> = mutableStateOf(""),
    val arrenderId : MutableState<Long> = mutableStateOf(0),
    val latitude : MutableState<Double> = mutableStateOf(0.0),
    val longitude : MutableState<Double> = mutableStateOf(0.0),
    val imageUrl : MutableState<String> = mutableStateOf("")

)





data class RegisterRoomState(
    val title : MutableState<String> = mutableStateOf(""),
    val description : MutableState<String> = mutableStateOf(""),
    val address : MutableState<String> = mutableStateOf(""),
    val price : MutableState<String> = mutableStateOf(""),
    val nearUniversities : MutableState<String> = mutableStateOf(""),
    val arrenderId : MutableState<Int> = mutableStateOf(0),
    val latitude : MutableState<Double> = mutableStateOf(0.0),
    val longitude : MutableState<Double> = mutableStateOf(0.0),
)

data class RegisterRoomRequestBody(
    val title:String,
    val description: String,
    val address: String,
    val price: Double,
    val nearUniversities:String,
    val latitude: Double,
    val longitude: Double,
    val arrenderId: Long
)