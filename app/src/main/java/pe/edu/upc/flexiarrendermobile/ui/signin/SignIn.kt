package pe.edu.upc.flexiarrendermobile.ui.signin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignInArrenderBody
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignInArrenderState
import pe.edu.upc.flexiarrendermobile.shared.MessageError

@Composable
fun SignIn(errorMessageModel: MutableState<String?>, sinUpFirstStep:()->Unit, signInSuccessful:()->Unit) {

    val email = remember{
        mutableStateOf("")
    }

    val password = remember{
        mutableStateOf("")
    }

    val requestSignInArrenderState= RequestSignInArrenderState()

    Scaffold {paddingValues->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ){

            Card (modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight(0.8f)) {

                Text(text = "FlexiDorm")
                Text(text = "Hola Arrendador")

                Column {
                    Text(text = "Correo electronico")
                    OutlinedTextField(
                        value = requestSignInArrenderState.email.value,
                        onValueChange = {
                            requestSignInArrenderState.email.value = it
                        },
                        placeholder = { Text("Ingresa tu correo electronico") }
                    )

                    Text(text = "Contraseña")
                    OutlinedTextField(
                        value = requestSignInArrenderState.password.value,
                        onValueChange = {
                            requestSignInArrenderState.password.value = it
                        },
                        placeholder = { Text("Ingresa tu contraseña") }
                    )
                }

                Column {
                    Button(onClick = {

                        //verificar que los campos no esten vacios
                        if(requestSignInArrenderState.email.value.isEmpty() || requestSignInArrenderState.password.value.isEmpty()){
                            errorMessageModel.value= "Los campos no pueden estar vacios"
                            return@Button
                        }

                        //caso contrario, mapear el state a body
                        val body= RequestSignInArrenderBody(
                            email = requestSignInArrenderState.email.value,
                            password = requestSignInArrenderState.password.value
                        )

                        val arrenderRepository= ArrenderRepositoryFactory.getArrenderRepository("")


                        arrenderRepository.signInArrender(body){apiResponse, errorCode, errorBody ->
                            if (apiResponse != null) {

                                //arrenderRepository.insertArrenderDataLocal()
                                println(
                                    "Código de estado: $errorCode, Cuerpo de la respuesta: $apiResponse"
                                )

                                var arrenderEntity = apiResponse.data

                                println(
                                    "ArrenderEntity: $arrenderEntity"
                                )

                                //Guardar la data en la base de datos local
                                //arrenderRepository.insertArrenderDataLocal(
                               //     arrenderEntity
                                //)
                                //verificar que no haya un arrender en la base de datos local
                                //si no hay, guardar el arrender en la base de datos local
                                println( "En la base de datos hay :"+ arrenderRepository.getArrenderDataLocal(arrenderEntity.userId.toString()))
                                if(arrenderRepository.getArrenderDataLocal(arrenderEntity.userId.toString())==null){

                                    arrenderRepository.insertArrenderDataLocal(
                                        arrenderEntity
                                    )

                                    println("Se guardo el arrender en la base de datos local")
                                    signInSuccessful()

                                }else{
                                    //si ya hay un arrender en la base de datos local , limpiar la base de datos local
                                    arrenderRepository.deleteArrenderDataLocal( arrenderEntity.userId.toString())
                                    println("Se limpio la base de datos local")
                                }



                                //navegar a la vista de listado de habitaciones
                            } else {

                                errorMessageModel.value = errorBody
                                // Si hubo un error
                                println("Error: Código de estado: $errorCode, Cuerpo del error: $errorBody")
                            }

                        }





                    }) {
                        Text(text = "Iniciar sesion")
                    }

                   Row {
                       Text(text = "¿No tienes cuenta?")
                       Text(
                           text = "Registrate",
                           modifier = Modifier.clickable {
                               sinUpFirstStep()
                           },
                           style = TextStyle(textDecoration = TextDecoration.Underline)
                       )
                   }
                }

                MessageError(errorMessageModel, "Espera un momento!")

            }

        }
    }

}