package pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.room.roomlist

import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.factories.RoomRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.Room
import pe.edu.upc.flexiarrendermobile.model.data.RoomUpdateState
import pe.edu.upc.flexiarrendermobile.ui.theme.FlexiArrenderMobileTheme

@Composable
fun RoomList(roomDetail: () -> Unit, roomNewOrSelected: RoomUpdateState) {

    var roomList = remember {
        mutableStateOf(listOf<Room>())
    }

    Scaffold(
        containerColor = Color.White,
        floatingActionButton = {
            FloatingActionButton(
                containerColor = Color(0xFF6C63FF),
                contentColor = Color.White,
                onClick = {
                    roomDetail()
                }
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Nueva habitación")
            }
        }
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

            println("dataLocalArrender: $dataLocalArrender")

            val roomListByArrender =
                RoomRepositoryFactory.getRoomRepositoryFactory(dataLocalArrender[0].token)
            roomListByArrender.getRoomsById(dataLocalArrender[0].id.toLong()) { apiResponseRoom, errorCode, status ->
                if (apiResponseRoom != null) {
                    println("apiResponseRoom: $apiResponseRoom")
                    roomList.value = apiResponseRoom.data
                } else {
                    println("error: $status")
                }
            }

            if (roomList.value.isEmpty()) {
                Spacer(modifier = Modifier.height(300.dp))
                Text(
                    text = "Actualmente no tienes habitaciones registradas",
                    style = TextStyle(color = Color(0xFF6C63FF), fontSize = 20.sp),
                    modifier = Modifier.padding(10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp
                )
            } else {
                RoomListByArrender(roomList, roomNewOrSelected, roomDetail)
            }
        }
    }
}

@Composable
fun RoomListByArrender(
    roomListByArrender: MutableState<List<Room>>,
    roomNewOrSelected: RoomUpdateState,
    roomDetail: () -> Unit
) {
    LazyColumn {
        items(roomListByArrender.value) { room ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White,
                ),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .height(150.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                    ) {
                        roomImage(url = room.imageUrl, size = 150.dp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = room.title,
                        style = TextStyle(color = Color(0xFF6C63FF), fontSize = 18.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(top = 8.dp, bottom = 4.dp)
                    )

                    Text(
                        text = "S/. ${room.price} x hora - ${room.nearUniversities}",
                        style = TextStyle(color = Color.Gray),
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Button(
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color(0xFF6C63FF),
                            containerColor = Color.White
                        ),
                        onClick = {
                            roomNewOrSelected.roomId.value = room.roomId
                            roomNewOrSelected.title.value = room.title
                            roomNewOrSelected.description.value = room.description
                            roomNewOrSelected.address.value = room.address
                            roomNewOrSelected.price.value = room.price.toString()
                            roomNewOrSelected.nearUniversities.value = room.nearUniversities
                            roomNewOrSelected.arrenderId.value = room.arrenderId
                            roomNewOrSelected.latitude.value = room.latitude
                            roomNewOrSelected.longitude.value = room.longitude
                            roomNewOrSelected.imageUrl.value = room.imageUrl
                            roomDetail()
                        }
                    ) {
                        Text(text = "Editar")
                    }
                }
            }
        }
    }
}

@Composable
fun roomImage(url: String, size: Dp) {
    GlideImage(
        imageModel = { url },
        imageOptions = ImageOptions(contentScale = ContentScale.Crop),
        modifier = Modifier
            .fillMaxWidth()
            .height(size)
            .clip(RoundedCornerShape(16.dp))
    )
}

