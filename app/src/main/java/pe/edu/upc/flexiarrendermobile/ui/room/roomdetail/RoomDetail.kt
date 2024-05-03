package pe.edu.upc.flexiarrendermobile.ui.room.roomdetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.factories.RoomRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomRequestBody
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomState
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderState
import pe.edu.upc.flexiarrendermobile.shared.MessageError
import pe.edu.upc.flexiarrendermobile.ui.signup.SignUpSignUpFirstStep
import pe.edu.upc.flexiarrendermobile.ui.theme.FlexiArrenderMobileTheme

@Composable
fun RoomDetail(errorMessageModel: MutableState<String?>, finishAddRoom:()->Unit) {

    val registerRoomState= RegisterRoomState()


    Scaffold { paddingValues ->
        Card(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize(),
            colors = CardDefaults.cardColors(
                containerColor = Color.White, //Card background color
            ),
            shape = RoundedCornerShape(0.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {


                Text(
                    text = "Agreguems una habitacion",
                    modifier=Modifier.padding(10.dp),
                    style = TextStyle(color = Color(0xFF846CD9), fontSize=20.sp, fontWeight = FontWeight.Bold)
                )


                LabeledTextField(
                    label = { Text("Título",color=Color(0xFF846CD9)) },
                    value = registerRoomState.title.value,
                    onValueChange = {
                        registerRoomState.title.value = it
                    },
                    placeholder = { Text("") },
                    modifier = Modifier.padding(10.dp),
                )


                LabeledTextField(
                    label = { Text("Descripción",color=Color(0xFF846CD9)) },
                    value = registerRoomState.description.value,
                    onValueChange = {
                        registerRoomState.description.value = it
                    },
                    placeholder = { Text("") },
                    modifier = Modifier.padding(10.dp)
                )

                LabeledTextField(
                    label = { Text("Dirección",color=Color(0xFF846CD9)) },
                    value = registerRoomState.address.value,
                    onValueChange = {
                        registerRoomState.address.value = it
                    },
                    placeholder = { Text("") },
                    modifier = Modifier.padding(10.dp)
                )
                LabeledTextField(
                    label = { Text("Url de la imagen",color=Color(0xFF846CD9)) },
                    value = registerRoomState.imageUrl.value,
                    onValueChange = {
                        registerRoomState.imageUrl.value = it
                    },
                    placeholder = { Text("") },
                    modifier = Modifier.padding(10.dp)
                )

                LabeledTextField(
                    label = { Text("Precio",color=Color(0xFF846CD9)) },
                    value = registerRoomState.price.value,
                    onValueChange = {
                        registerRoomState.price.value = it
                    },
                    placeholder = { Text("") },
                    modifier = Modifier.padding(10.dp)
                )

                LabeledTextField(
                    label = { Text("Universidades cercanas",color=Color(0xFF846CD9)) },
                    value = registerRoomState.nearUniversities.value,
                    onValueChange = {
                        registerRoomState.nearUniversities.value = it
                    },
                    placeholder = { Text("") },
                    modifier = Modifier.padding(10.dp)
                )


                Button(
                    colors= ButtonDefaults.buttonColors(contentColor =Color.White, containerColor = Color(0xFF846CD9)),
                    modifier=Modifier.padding(),
                    onClick = {
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

                    println( "Token en detail: ${dataLocalArrender[0].token}")

                    val roomRepositoryFactory= RoomRepositoryFactory.getRoomRepositoryFactory(dataLocalArrender[0].token)

                    roomRepositoryFactory.registerRoom(body){apiResponse, errorCode, errorBody ->
                        if (apiResponse != null) {
                            println(
                                "Código de estado: $errorCode, Cuerpo de la respuesta: $apiResponse"
                            )
                            finishAddRoom()
                        }else{
                            println(
                                "Código de estado: $errorCode, Cuerpo de la respuesta: $errorBody"
                            )
                        }
                    }











                }) {
                    Text(text = "Listo")
                }



                Button(
                    colors= ButtonDefaults.buttonColors(contentColor =Color(0xFF846CD9), containerColor = Color.White),
                    onClick = {
                        finishAddRoom()

                    }) {
                    Text(text = "Cancelar")
                }


                MessageError(errorMessageModel, "Espera!")



                // Otros campos de texto con etiquetas similares
            }
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
            modifier = Modifier.fillMaxWidth(),
            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
            colors= OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color(0xFF846CD9),
            ),
            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RoomDetailPreview() {
    FlexiArrenderMobileTheme {
        RoomDetail(mutableStateOf(null),{})
    }
}