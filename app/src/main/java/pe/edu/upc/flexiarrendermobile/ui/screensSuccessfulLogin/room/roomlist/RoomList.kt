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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
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
            containerColor = Color(0xFF846CD9),
            contentColor = Color.White,
            onClick = {
            // Navegar a la pantalla de detalle de la habitación
                roomDetail()
        }) {
            Icon(Icons.Filled.Add, "Nueva habitación")
        }
    }) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(56.dp))

            val arrenderRepository = ArrenderRepositoryFactory.getArrenderRepository("")
            val dataLocalArrender = arrenderRepository.getArrender()

            //imirpimrla dataLocalArrender
            println("dataLocalArrender: $dataLocalArrender")
            // Mostrar la lista de habitaciones
            var roomListByArrender =
                RoomRepositoryFactory.getRoomRepositoryFactory(dataLocalArrender[0].token)

            roomListByArrender.getRoomsById(dataLocalArrender[0].id.toLong()) { apiResponseRoom, errorCode, status ->

                if (apiResponseRoom != null) {
                    println("apiResponseRoom: $apiResponseRoom")
                    roomList.value = apiResponseRoom.data
                } else {
                    // Mostrar mensaje de error

                    println("error: $status")
                }
            }
            if(roomList.value.isEmpty()){
                //empujar el texto hacia el centro
                Spacer(modifier = Modifier.height(300.dp))

                Text(
                    text = "Actualmente no tienes habitaciones registradas",
                    style= TextStyle( color = Color(0xFF846CD9), fontSize = 20.sp),
                    modifier=Modifier.padding(10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 17.sp
                )
            }else{
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


    // Mostrar la lista de habitaciones
    LazyColumn {
        items(roomListByArrender.value) { room ->


            Card(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .fillMaxHeight(0.5f)
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF846CD9), //Color de fondo de la tarjeta
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .height(120.dp) // Altura definida para el contenedor de la imagen
                            .fillMaxWidth()
                            .padding(bottom = 4.dp)
                    ) {
                        roomImage(url = room.imageUrl, size = 250.dp)
                    }
                    Text(text = room.title, style = TextStyle(color = Color.White), modifier = Modifier.padding(4.dp))
                    Text(text = "S/." + room.price.toString() + " x hora - "+ room.nearUniversities, style = TextStyle(color = Color.White), modifier = Modifier.padding(4.dp))
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            contentColor = Color(0xFF846CD9),
                            containerColor = Color.White
                        ),
                        modifier = Modifier.padding(),
                        onClick = {
                            // Navegar a la pantalla de detalle de la habitación junto con la información de la habitación

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
fun roomImage(url: String, size: Dp){
    GlideImage(


        imageModel={url},
        imageOptions= ImageOptions(contentScale= ContentScale.Crop),
        modifier=Modifier.fillMaxWidth(),
    )
}

@Preview(showBackground = true)
@Composable
fun roomImagePreview() {
    FlexiArrenderMobileTheme {
        roomImage(url = "https://www.google.com/images/branding/googlelogo/1x/googlelogo_color_272x92dp.png", size = 120.dp)
    }
}