package pe.edu.upc.flexiarrendermobile.ui.signup

import android.app.AlertDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderState
import pe.edu.upc.flexiarrendermobile.shared.MessageError
import pe.edu.upc.flexiarrendermobile.ui.home.Home
import pe.edu.upc.flexiarrendermobile.ui.theme.FlexiArrenderMobileTheme


@Composable
fun SignUpSignUpFirstStep(
    requestSignUpArrender: RequestSignUpArrenderState,
    errorMessageModel: MutableState<String?>,
    secontStep: () -> Unit,
    signInStep:() -> Unit
    ) {

    //El scaffold es un contenedor que nos permite tener un appbar y un body
    //El appbar es un contenedor que nos permite tener un titulo y un menu
    //El body es un contenedor que nos permite tener un contenido principal


    val confirmPassword = remember {
        mutableStateOf("")
    }



    Scaffold { paddingValues ->
        //Aqui va el contenido principal

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(color = Color(0xFF846CD9)),
            contentAlignment = Alignment.Center,


        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.8f),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White, //Card background color
                ),
                shape = RoundedCornerShape(20.dp),


            ) {
                //El card es un contenedor que nos permite tener un contenedor con sombra
                Column (
                    modifier= Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,

                ) {
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Bienvenido a FlexiDorm!",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp, textAlign = TextAlign.Center, color = Color(0xFF846CD9))
                    )
                    Text(
                        text = "Arrendador",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp, textAlign = TextAlign.Center, color = Color(0xFF846CD9)),

                    )
                    Spacer(modifier = Modifier.height(60.dp))

                    Text(
                        text = "Ingresa tus datos para registrarte",
                        style= TextStyle( color = Color(0xFF846CD9))
                    )

                    Spacer(modifier = Modifier.height(30.dp))



                        //Username
                        OutlinedTextField(
                            value = requestSignUpArrender.username.value,
                            onValueChange = {
                                requestSignUpArrender.username.value = it
                            },
                            modifier=Modifier.height(60.dp),
                            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
                            label = { Text("Nombre de usuario", color = Color(0xFF846CD9)) },
                            shape = RoundedCornerShape(15.dp), // Redondear los bordes
                            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
                            colors=OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF846CD9),
                            )
                        )

                        //Password
                        OutlinedTextField(
                            value = requestSignUpArrender.password.value,
                            onValueChange = {
                                requestSignUpArrender.password.value = it
                            },
                            modifier=Modifier.height(60.dp),
                            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
                            label = { Text("Contraseña", color = Color(0xFF846CD9)) },
                            shape = RoundedCornerShape(15.dp), // Redondear los bordes
                            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
                            colors=OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF846CD9),
                            )
                        )

                        //Confirm Password
                        OutlinedTextField(
                            value = confirmPassword.value,
                            onValueChange = {
                                confirmPassword.value = it
                            },
                            modifier=Modifier.height(60.dp),
                            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
                            label = { Text("Confirma tu contraseña", color = Color(0xFF846CD9)) },
                            shape = RoundedCornerShape(15.dp), // Redondear los bordes
                            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
                            colors=OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF846CD9),
                            )
                        )

                    Spacer(modifier = Modifier.height(60.dp))



                    Button(
                        shape= RoundedCornerShape(8.dp),
                        colors=ButtonDefaults.buttonColors(contentColor =Color.White, containerColor = Color(0xFF846CD9)),
                        onClick = {
                            //Verificar si hay campos vacios
                            if (requestSignUpArrender.username.value.isEmpty() || requestSignUpArrender.password.value.isEmpty() || confirmPassword.value.isEmpty()) {
                                //Mostrar un mensaje de error
                                errorMessageModel.value = "Por favor, llena todos los campos"
                                return@Button
                            }
                            //Verificar que las contraseñas sean iguales
                            if (requestSignUpArrender.password.value != confirmPassword.value) {
                                //Mostrar un mensaje de error
                                errorMessageModel.value = "Las contraseñas no coinciden"
                                return@Button
                            }
                            //Continuar al siguiente paso
                            secontStep()

                        }) {
                            Text("Continuar")
                        }

                    Spacer(modifier = Modifier.height(30.dp))


                    Row {
                        Text(text = "¿Ya tienes una cuenta?",style=TextStyle(color = Color(0xFF846CD9)))
                        Text(
                            text = "Inicia sesión",
                            modifier = Modifier.padding(horizontal = 3.dp).clickable {
                                signInStep()
                            },
                            style = TextStyle(textDecoration = TextDecoration.Underline, fontSize = 15.sp, color = Color(0xFF846CD9))
                        )
                    }


                    //Llamar al message error
                    MessageError(errorMessageModel, "Espera un momento!")


                }


            }
        }

    }


}

@Preview(showBackground = true)
@Composable
fun SignUpSignUpFirstStepPreview() {
    FlexiArrenderMobileTheme {
        SignUpSignUpFirstStep(
            requestSignUpArrender = RequestSignUpArrenderState(),
            errorMessageModel = mutableStateOf(null),
            secontStep = {},
            signInStep = {}
        )
    }
}



