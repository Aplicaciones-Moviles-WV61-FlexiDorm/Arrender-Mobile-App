package pe.edu.upc.flexiarrendermobile.ui.room.roomlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.factories.RoomRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderState
import pe.edu.upc.flexiarrendermobile.model.data.Room
import pe.edu.upc.flexiarrendermobile.ui.signup.SignUpSignUpFirstStep
import pe.edu.upc.flexiarrendermobile.ui.theme.FlexiArrenderMobileTheme


@Composable
fun RoomList(addRoom: () -> Unit) {

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
            addRoom()
        }) {
            Icon(Icons.Filled.Add, "Nueva habitación")
        }
    }) {
        Column(
            modifier = Modifier.padding(it).fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

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
                Text(
                    text = "No hay habitaciones",
                    style= TextStyle( color = Color(0xFF846CD9), fontSize = 20.sp),
                    modifier=Modifier.padding(10.dp)
                )
            }else{
                RoomListByArrender(roomList)
            }

        }

    }

}


@Composable
fun RoomListByArrender(roomListByArrender: MutableState<List<Room>>) {
    // Mostrar la lista de habitaciones
    LazyColumn {
        items(roomListByArrender.value) { room ->
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color(0xFF846CD9), //Card background color
                )
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    roomImage(url = room.imageUrl, size = 120.dp)
                    Text(text = room.title,style= TextStyle( color = Color.White), modifier=Modifier.padding(4.dp))
                    Text(text = "S/."+room.price.toString()+" x hora",style= TextStyle( color = Color.White), modifier=Modifier.padding(4.dp))
                    Text(text = "Cerca a "+room.nearUniversities,style= TextStyle( color = Color.White), modifier=Modifier.padding(4.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RoomListPreview() {
    FlexiArrenderMobileTheme {
        RoomList(addRoom = {})
    }
}


/*
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
                Text(text = "No hay habitaciones")
            }else{
                RoomListByArrender(roomList)
            }
*/


@Composable
fun roomImage(url: String, size: Dp){
    GlideImage(
        imageModel={url},
        imageOptions= ImageOptions(contentScale= ContentScale.Crop),
        modifier=Modifier.size(size)
    )
}