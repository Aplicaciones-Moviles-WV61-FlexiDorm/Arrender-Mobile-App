package pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.reservations.reservationlist

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.factories.ReservationRepositoryFactory
import pe.edu.upc.flexiarrendermobile.factories.RoomRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.Reservation
import pe.edu.upc.flexiarrendermobile.model.data.ReservationState
import pe.edu.upc.flexiarrendermobile.model.data.Room
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun ReservationList(reservationSelected: ReservationState, reservationDetail: () -> Unit) {

    var reservationList = remember {
        mutableStateOf(listOf<Reservation>())
    }
    var roomList = remember {
        mutableStateOf(listOf<Room>())
    }

    Scaffold(
        containerColor = Color.White,
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            val arrenderRepository = ArrenderRepositoryFactory.getArrenderRepository("")
            val dataLocalArrender = arrenderRepository.getArrender()

            var roomListByArrender =
                RoomRepositoryFactory.getRoomRepositoryFactory(dataLocalArrender[0].token)
            roomListByArrender.getRoomsById(dataLocalArrender[0].id.toLong()) { apiResponseRoom, errorCode, status ->
                if (apiResponseRoom != null) {
                    roomList.value = apiResponseRoom.data
                    println("Habitaciones: ${roomList.value}")
                } else {
                    roomList.value = listOf()
                    println("Error al obtener las habitaciones")
                }
            }

            val reservationListByArrender = ReservationRepositoryFactory.getReservationRepositoryFactory(dataLocalArrender[0].token)
            reservationListByArrender.getReservationsByArrenderId(dataLocalArrender[0].id.toLong()) { apiResponseReservation, errorCode, status ->
                if (apiResponseReservation != null) {
                    reservationList.value = apiResponseReservation.data
                    println("Reservas: ${reservationList.value}")
                } else {
                    reservationList.value = listOf()
                    println("Error al obtener las reservas")
                }
            }

            if (reservationList.value.isEmpty()) {
                println("No hay reservas")
                Text(
                    text = "Actualmente tus habitaciones no tienen reservas",
                    style = TextStyle(color = Color(0xFF6C63FF), fontSize = 20.sp),
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp
                )
            } else {
                ReservationListByArrender(reservationList, roomList, reservationSelected, reservationDetail)
            }
        }
    }
}

@Composable
fun ReservationListByArrender(
    reservationListByArrender: MutableState<List<Reservation>>,
    roomList: MutableState<List<Room>>,
    reservationSelected: ReservationState,
    reservationDetail: () -> Unit
) {
    LazyColumn {
        items(reservationListByArrender.value) { reservation ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    // Filtrar el id de la habitación en la lista de habitaciones
                    val room = roomList.value.find { it.roomId == reservation.room }

                    Text(
                        text = "Tu habitación:",
                        style = TextStyle(
                            color = Color(0xFF6C63FF),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

                    room?.let {
                        Text(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            text = it.title,
                            style = TextStyle(
                                color = Color.Gray,
                                fontSize = 18.sp
                            )
                        )
                    }

                    val inputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX"
                    val outputFormat = "yyyy-MM-dd HH:mm"
                    val formattedDate = parseAndFormatDate(reservation.date, inputFormat, outputFormat)

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        text = "Se realizó:"
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = formattedDate,
                        style = TextStyle(color = Color.Gray)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "Desde: ${reservation.hourInit}",
                        style = TextStyle(color = Color.Gray)
                    )
                    Text(
                        text = "Hasta: ${reservation.hourFinal}",
                        style = TextStyle(color = Color.Gray)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        text = "Precio total:"
                    )
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "S/. ${reservation.totalPrice}",
                        style = TextStyle(color = Color.Gray)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            reservationSelected.reservationId.value = reservation.reservationId
                            reservationSelected.roomTitle.value = room?.title.toString()
                            reservationSelected.date.value = formattedDate
                            reservationSelected.hourInit.value = reservation.hourInit
                            reservationSelected.hourFinal.value = reservation.hourFinal
                            reservationSelected.totalPrice.value = reservation.totalPrice
                            reservationSelected.email.value = reservation.email
                            reservationSelected.phone.value = reservation.phone
                            reservationSelected.observation.value = reservation.observation
                            reservationDetail()
                        },
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color.White,
                            containerColor = Color(0xFF6C63FF)
                        )
                    ) {
                        Text("Ver detalle")
                    }
                }
            }
        }
    }
}

@Composable
fun BackgroundShapes() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        // Draw a gradient circle at the top left
        translate(left = -width * 0.5f, top = -height * 0.5f) {
            drawIntoCanvas { canvas ->
                val paint = Paint().apply {
                    shader = RadialGradientShader(
                        colors = listOf(Color(0xFF6C63FF), Color.Transparent),
                        center = Offset(width / 2, height / 2),
                        radius = height / 1.5f
                    )
                }
                canvas.drawCircle(Offset(width / 2, height / 2), height / 1.5f, paint)
            }
        }

        // Draw a wave pattern at the bottom
        val wavePath = Path().apply {
            moveTo(0f, height)
            cubicTo(
                width * 0.25f, height * 0.8f,
                width * 0.75f, height * 1.2f,
                width, height
            )
            lineTo(width, height)
            close()
        }
        drawPath(
            path = wavePath,
            brush = Brush.linearGradient(
                colors = listOf(Color(0xFF6C63FF), Color.Transparent)
            )
        )
    }
}





fun parseAndFormatDate(dateString: String, inputFormat: String, outputFormat: String): String {
    return try {
        val inputDateFormat = SimpleDateFormat(inputFormat, Locale.getDefault())
        val date = inputDateFormat.parse(dateString)
        val outputDateFormat = SimpleDateFormat(outputFormat, Locale.getDefault())
        date?.let { outputDateFormat.format(it) } ?: "Fecha no válida"
    } catch (e: Exception) {
        "Fecha no válida"
    }
}

