package pe.edu.upc.flexiarrendermobile.ui.signup

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrender
import pe.edu.upc.flexiarrendermobile.shared.MessageError


@Composable
fun SignUpSecondStep(
    requestSignUpArrender: RequestSignUpArrender,
    errorMessageModel: MutableState<String?>,
    pressOnBack:()->Unit) {




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

                Text(text = "Estas a un paso de usar FlexiDorm")

                Text(text = "Estas listo arrendador?")

                Column {
                    //Username
                    OutlinedTextField(
                        value = requestSignUpArrender.email.value,
                        onValueChange = {
                            requestSignUpArrender.email.value = it
                        },
                        placeholder = { Text("Ingresa tu e-mail") }
                    )


                    OutlinedTextField(
                        value = requestSignUpArrender.firstname.value,
                        onValueChange = {
                            requestSignUpArrender.firstname.value = it
                        },
                        placeholder = { Text("Ingresa tu nombre") }
                    )

                    OutlinedTextField(
                        value = requestSignUpArrender.lastname.value,
                        onValueChange = {
                            requestSignUpArrender.lastname.value = it
                        },
                        placeholder = { Text("Ingresa tu apellido") }
                    )


                    OutlinedTextField(
                        value = requestSignUpArrender.address.value,
                        onValueChange = {
                            requestSignUpArrender.address.value = it
                        },
                        placeholder = { Text("Ingresa tu domicilio") }
                    )


                    Button(onClick = {

                        //Validar
                        if(requestSignUpArrender.email.value.isEmpty() || requestSignUpArrender.firstname.value.isEmpty() || requestSignUpArrender.lastname.value.isEmpty() || requestSignUpArrender.address.value.isEmpty()){
                            errorMessageModel.value = "Todos los campos son obligatorios"
                            return@Button
                        }



                    }) {
                        Text("Registrarme")
                    }
                    MessageError(errorMessageModel)


                    Text(
                        text = "Volver",
                        modifier = Modifier.clickable {
                            //Regresar
                            pressOnBack()
                        },
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )
                }


            }
        }
    }



}