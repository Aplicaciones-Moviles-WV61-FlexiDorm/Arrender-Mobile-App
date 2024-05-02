package pe.edu.upc.flexiarrendermobile.ui.room.roomlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.factories.RoomRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.Room


@Composable
fun RoomList(addRoom:()->Unit) {

    var roomList=remember{
        mutableStateOf(listOf<Room>())
    }


        Scaffold(floatingActionButton = {
            FloatingActionButton(onClick = {
                // Navegar a la pantalla de detalle de la habitación
                addRoom()
            }) {
                Icon(Icons.Filled.Add, "Nueva habitación")
            }
        }) {

            Column(modifier = Modifier.padding(it)) {
                val arrenderRepository= ArrenderRepositoryFactory.getArrenderRepository("")
                val dataLocalArrender = arrenderRepository.getArrender()
                // Mostrar la lista de habitaciones
                var roomListByArrender= RoomRepositoryFactory.getRoomRepositoryFactory(dataLocalArrender[0].token)

                roomListByArrender.getRoomsById(dataLocalArrender[0].id.toLong()){  apiResponseRoom, errorCode, status ->

                    if (apiResponseRoom != null) {
                        println( "apiResponseRoom: $apiResponseRoom")
                        roomList.value=apiResponseRoom.data
                    } else {
                        // Mostrar mensaje de error
                        println( "error: $status")
                    }
                }

                RoomListByArrender(roomList)
            }
        }

}


@Composable
fun RoomListByArrender(roomListByArrender:MutableState<List<Room>>) {
    // Mostrar la lista de habitaciones
    LazyColumn {
        items(roomListByArrender.value) { room ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column {
                    Text(text = room.title)
                    Text(text = room.description)
                    Text(text = room.address)
                    Text(text = room.price.toString())
                    Text(text = room.nearUniversities)
                }
            }
        }
    }
}