package pe.edu.upc.flexiarrendermobile.ui.signin

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignInArrenderBody
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignInArrenderState
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderState
import pe.edu.upc.flexiarrendermobile.shared.MessageError
import pe.edu.upc.flexiarrendermobile.ui.signup.SignUpSecondStep
import pe.edu.upc.flexiarrendermobile.ui.theme.FlexiArrenderMobileTheme

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
                .padding(paddingValues)
                .background(color = Color(0xFF846CD9)),
            contentAlignment = Alignment.Center
        ){

            Card (modifier = Modifier
                .fillMaxWidth(0.85f)
                .fillMaxHeight(0.6f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White, //Card background color
                ),
                shape = RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier= Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        modifier=Modifier.padding(16.dp),
                        text = "FlexiDorm!",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 30.sp, textAlign = TextAlign.Center, color = Color(0xFF846CD9))
                    )
                    Text(
                        modifier=Modifier.padding(14.dp),
                        text = "Hola Arrendador",
                        style = TextStyle(color = Color(0xFF846CD9))
                    )

                    Column {
                        Text(
                            modifier=Modifier.padding(5.dp),
                            text = "Correo electronico",
                            style = TextStyle(color = Color(0xFF846CD9))
                        )
                        OutlinedTextField(
                            value = requestSignInArrenderState.email.value,
                            onValueChange = {
                                requestSignInArrenderState.email.value = it
                            },
                            placeholder = { Text("Ingresa tu correo electronico",style = TextStyle(color = Color(0xFF846CD9))) },
                            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
                            shape = RoundedCornerShape(15.dp), // Redondear los bordes
                            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
                            colors= OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF846CD9),
                            )
                        )

                        Spacer(modifier = Modifier.height(10.dp))


                        Text(
                            modifier=Modifier.padding(5.dp),
                            text = "Contraseña",
                            style = TextStyle(color = Color(0xFF846CD9))
                        )
                        OutlinedTextField(
                            value = requestSignInArrenderState.password.value,
                            onValueChange = {
                                requestSignInArrenderState.password.value = it
                            },
                            placeholder = { Text("Ingresa tu contraseña",style = TextStyle(color = Color(0xFF846CD9))) },
                            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
                            shape = RoundedCornerShape(15.dp), // Redondear los bordes
                            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
                            colors= OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF846CD9),
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(30.dp))

                    Button(
                        colors= ButtonDefaults.buttonColors(contentColor =Color.White, containerColor = Color(0xFF846CD9)),
                        onClick = {

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
                                    
                                    
                                    //Limpiar la base de datos antes de guardar el arrender
                                    arrenderRepository.deleteAllArrenderDataLocal()
                                    
                                    
                                    println( "En la base de datos hay :"+ arrenderRepository.getArrenderDataLocal(arrenderEntity.userId.toString()))
                                    if(arrenderRepository.getArrenderDataLocal(arrenderEntity.userId.toString())==null){

                                        arrenderRepository.insertArrenderDataLocal(
                                            arrenderEntity
                                        )

                                        println("Se guardo el arrender en la base de datos local")
                                        signInSuccessful()

                                    }else{
                                        //si ya hay un arrender en la base de datos local , limpiar la base de datos local
                                        //arrenderRepository.deleteArrenderDataLocal( arrenderEntity.userId.toString())

                                        arrenderRepository.deleteAllArrenderDataLocal()//solo comente lo de arriba , agregue las quey en el dao y el reposiry
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
                            Text(text = "¿No tienes cuenta?",style=TextStyle(color = Color(0xFF846CD9)))
                            Text(
                                text = "Registrate",
                                modifier = Modifier.padding(horizontal = 3.dp).clickable {
                                    sinUpFirstStep()
                                },
                                style = TextStyle(textDecoration = TextDecoration.Underline, fontSize = 15.sp, color = Color(0xFF846CD9))
                            )
                        }


                    MessageError(errorMessageModel, "Espera un momento!")

                }
            }

        }
    }

}

@Preview(showBackground = true)
@Composable
fun SignInPreview() {
    FlexiArrenderMobileTheme {
        SignIn(
            errorMessageModel = mutableStateOf(null),
            sinUpFirstStep = {},
            signInSuccessful = {}
        )
    }
}