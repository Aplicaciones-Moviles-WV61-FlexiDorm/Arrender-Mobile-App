package pe.edu.upc.flexiarrendermobile.ui.room.roomlist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun RoomList(addRoom:()->Unit) {


        Scaffold(floatingActionButton = {
            FloatingActionButton(onClick = {
                // Navegar a la pantalla de detalle de la habitación
                addRoom()
            }) {
                Icon(Icons.Filled.Add, "Nueva habitación")
            }
        }) {

            Column(modifier = Modifier.padding(it)) {
                // Mostrar la lista de habitaciones
            }
        }

}


