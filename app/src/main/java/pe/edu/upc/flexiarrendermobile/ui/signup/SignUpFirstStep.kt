package pe.edu.upc.flexiarrendermobile.ui.signup

import android.app.AlertDialog
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AlertDialogDefaults
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
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderState
import pe.edu.upc.flexiarrendermobile.shared.MessageError


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


    // En tu composable Home




    val confirmPassword = remember {
        mutableStateOf("")
    }



    Scaffold { paddingValues ->
        //Aqui va el contenido principal

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.8f)
            ) {
                //El card es un contenedor que nos permite tener un contenedor con sombra

                Text(text = "Bienvenido a FlexiDorm Arrendador!")

                Text(text = "Ingresa tus datos para registrarte")

                Column {
                    //Username
                    OutlinedTextField(
                        value = requestSignUpArrender.username.value,
                        onValueChange = {
                            requestSignUpArrender.username.value = it
                        },
                        placeholder = { Text("Ingresa un nombre de usuario") }
                    )

                    //Password
                    OutlinedTextField(
                        value = requestSignUpArrender.password.value,
                        onValueChange = {
                            requestSignUpArrender.password.value = it
                        },
                        placeholder = { Text("Ingresa tu contraseña") }
                    )

                    //Confirm Password
                    OutlinedTextField(
                        value = confirmPassword.value,
                        onValueChange = {
                            confirmPassword.value = it
                        },
                        placeholder = { Text("Confirma tu contraseña") }
                    )


                    Button(onClick = {
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

                    //Llamar al message error
                    MessageError(errorMessageModel, "Espera un momento!")








                    Text(text = "¿Ya tienes una cuenta? Inicia sesión")
                    Text(
                        text = "Inicia sesión",
                        modifier = Modifier.clickable {
                            signInStep()
                        },
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                }


            }
        }

    }


}





