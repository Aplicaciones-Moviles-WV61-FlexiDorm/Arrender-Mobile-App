package pe.edu.upc.flexiarrendermobile.ui.room.roomdetail

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.factories.RoomRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomRequestBody
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomState
import pe.edu.upc.flexiarrendermobile.shared.MessageError

@Composable
fun RoomDetail(errorMessageModel: MutableState<String?>, finishAddRoom:()->Unit) {

    val registerRoomState= RegisterRoomState()


    Scaffold { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            LabeledTextField(
                label = { Text("Título") },
                value = registerRoomState.title.value,
                onValueChange = {
                    registerRoomState.title.value = it
                },
                placeholder = { Text("") }
            )

            LabeledTextField(
                label = { Text("Descripción") },
                value = registerRoomState.description.value,
                onValueChange = {
                    registerRoomState.description.value = it
                },
                placeholder = { Text("") }
            )

            LabeledTextField(
                label = { Text("Dirección") },
                value = registerRoomState.address.value,
                onValueChange = {
                    registerRoomState.address.value = it
                },
                placeholder = { Text("") }
            )
            LabeledTextField(
                label = { Text("Url de la imagen") },
                value = registerRoomState.imageUrl.value,
                onValueChange = {
                    registerRoomState.imageUrl.value = it
                },
                placeholder = { Text("") }
            )

            LabeledTextField(
                label = { Text("Precio") },
                value = registerRoomState.price.value,
                onValueChange = {
                    registerRoomState.price.value = it
                },
                placeholder = { Text("") }
            )

            LabeledTextField(
                label = { Text("Universidades cercanas") },
                value = registerRoomState.nearUniversities.value,
                onValueChange = {
                    registerRoomState.nearUniversities.value = it
                },
                placeholder = { Text("") }
            )


            Button(onClick = {
                // Validar los campos y enviar la solicitud de registro
                if(
                    registerRoomState.title.value.isEmpty()||
                    registerRoomState.description.value.isEmpty() ||
                    registerRoomState.address.value.isEmpty() ||
                    registerRoomState.imageUrl.value.isEmpty() ||
                    registerRoomState.price.value.isEmpty() ||
                    registerRoomState.nearUniversities.value.isEmpty()){

                    errorMessageModel.value= "Los campos no pueden estar vacios"


                    // Mostrar mensaje de error
                    return@Button
                }

                val arrenderRepository= ArrenderRepositoryFactory.getArrenderRepository("")
                val dataLocalArrender = arrenderRepository.getArrender()

                //Mapear el estado a la solicitud de registro
                val body= RegisterRoomRequestBody(
                    title = registerRoomState.title.value,
                    description = registerRoomState.description.value,
                    address = registerRoomState.address.value,
                    imageUrl = registerRoomState.imageUrl.value,
                    price = registerRoomState.price.value.toDouble(),
                    nearUniversities = registerRoomState.nearUniversities.value,
                    arrenderId = dataLocalArrender[0].id.toLong()
                )

                println( "Body:+$body")

                val roomRepositoryFactory= RoomRepositoryFactory.getRoomRepositoryFactory(dataLocalArrender[0].token)

                roomRepositoryFactory.registerRoom(body){apiResponse, errorCode, errorBody ->
                    if (apiResponse != null) {
                        println(
                            "Código de estado: $errorCode, Cuerpo de la respuesta: $apiResponse"
                        )
                    }else{
                        println(
                            "Código de estado: $errorCode, Cuerpo de la respuesta: $errorBody"
                        )
                    }
                }

                finishAddRoom()









            }) {
                Text(text = "Listo")

            }
            MessageError(errorMessageModel, "Espera!")



            // Otros campos de texto con etiquetas similares
        }
    }





}


@Composable
fun LabeledTextField(
    label: @Composable () -> Unit,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: @Composable (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        label()
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = placeholder ?: {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}