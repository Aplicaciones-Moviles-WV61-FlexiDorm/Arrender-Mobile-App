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
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderBody
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderState
import pe.edu.upc.flexiarrendermobile.shared.MessageError
import java.util.Calendar
import java.util.Date
import java.util.TimeZone


@OptIn(ExperimentalMaterial3Api::class)//Para poder usar el DatePicker
@Composable
fun SignUpSecondStep(
    requestSignUpArrenderState: RequestSignUpArrenderState,
    errorMessageModel: MutableState<String?>,
    pressOnBack:()->Unit,
    navigationToSignIn:()->Unit,
    ){

    //Valores para el DatePicker
    val state=rememberDatePickerState()
    val showDialog= remember { mutableStateOf(false) }
    val year=remember{mutableStateOf(0)}
    val month=remember{mutableStateOf(0)}
    val day=remember{mutableStateOf(0)}

    //Valores para el comboBox
    var isExpanded = remember { mutableStateOf(false) }
    val selectedOption = remember { mutableStateOf("") }

    //Para el titulo del error en el dialogo
    val dialogTitle= remember { mutableStateOf("") }



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
                        value = requestSignUpArrenderState.email.value,
                        onValueChange = {
                            requestSignUpArrenderState.email.value = it
                        },
                        placeholder = { Text("Ingresa tu e-mail") }
                    )


                    OutlinedTextField(
                        value = requestSignUpArrenderState.firstname.value,
                        onValueChange = {
                            requestSignUpArrenderState.firstname.value = it
                        },
                        placeholder = { Text("Ingresa tu nombre") }
                    )

                    OutlinedTextField(
                        value = requestSignUpArrenderState.lastname.value,
                        onValueChange = {
                            requestSignUpArrenderState.lastname.value = it
                        },
                        placeholder = { Text("Ingresa tu apellido") }
                    )

                    OutlinedTextField(
                        value = requestSignUpArrenderState.phoneNumber.value,
                        onValueChange = {
                            requestSignUpArrenderState.phoneNumber.value = it
                        },
                        placeholder = { Text("Ingresa tu numero de telefono") }
                    )


                    OutlinedTextField(
                        value = requestSignUpArrenderState.address.value,
                        onValueChange = {
                            requestSignUpArrenderState.address.value = it
                        },
                        placeholder = { Text("Ingresa tu domicilio") }
                    )

                    Button(onClick = {
                        showDialog.value=true
                    }) {
                        Text(text = "Fecha de nacimiento")
                    }


                    if(showDialog.value){
                        DatePickerDialog(
                            onDismissRequest = {//que queremos que pase cuando se cierre el dialogo
                                showDialog.value=false
                            },
                            confirmButton = {
                                Button(onClick = {
                                    showDialog.value=false
                                }) {
                                    Text("Aceptar")
                                }
                            }
                            
                        ){
                            DatePicker(
                                state=state
                            )

                        }
                    }

                    val dateMilliseconds = state.selectedDateMillis
                    if (dateMilliseconds != null) {
                        val date = Date(dateMilliseconds)
                        val calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC")).apply { time = date }

                        year.value = calendar.get(Calendar.YEAR)
                        month.value = calendar.get(Calendar.MONTH) + 1 // Los meses en Calendar son base 0, por lo que sumamos 1
                        day.value = calendar.get(Calendar.DAY_OF_MONTH)
                    }

                    if (year.value != 0 && month.value != 0 && day.value != 0) {
                        // Formatear día y mes con dos dígitos
                        val formattedDay = day.value.toString().padStart(2, '0')
                        val formattedMonth = month.value.toString().padStart(2, '0')

                        // Establecer la fecha en el formato "yyyy-MM-dd" para la solicitud
                        requestSignUpArrenderState.birthDate.value = "${year.value}-$formattedMonth-$formattedDay"

                        // Mostrar la fecha en el formato "dd-MM-yyyy"
                        Text(text = "Fecha de nacimiento: $formattedDay-$formattedMonth-${year.value}")
                    }


                    //comboBox para el sexo

                    ExposedDropdownMenuBox(
                        expanded = isExpanded.value,
                        onExpandedChange ={isExpanded.value=it}
                    ) {
                        TextField(
                            value = selectedOption.value,
                            onValueChange ={},
                            readOnly = true,
                            trailingIcon = {
                                           ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded.value)
                            },
                            colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            modifier = Modifier.menuAnchor()
                            )
                        ExposedDropdownMenu(
                            expanded =isExpanded.value ,
                            onDismissRequest = { isExpanded.value = false },
                        ) {
                            DropdownMenuItem(
                                text = { Text(text = "Male") },
                                onClick = {
                                    selectedOption.value="Male"
                                    isExpanded.value=false
                                })
                            DropdownMenuItem(
                                text = { Text(text = "Female") },
                                onClick = {
                                    selectedOption.value="Female"
                                    isExpanded.value=false
                                })

                        }

                    }


                    //Asignar el valor seleccionado al modelo
                    requestSignUpArrenderState.gender.value=selectedOption.value


                    Button(onClick = {
                        //Validar que todos los campos esten llenos
                        if (
                            requestSignUpArrenderState.firstname.value.isEmpty() ||
                            requestSignUpArrenderState.lastname.value.isEmpty() ||
                            requestSignUpArrenderState.username.value.isEmpty() ||
                            requestSignUpArrenderState.phoneNumber.value.isEmpty() ||
                            requestSignUpArrenderState.email.value.isEmpty() ||
                            requestSignUpArrenderState.password.value.isEmpty() ||
                            requestSignUpArrenderState.address.value.isEmpty() ||
                            requestSignUpArrenderState.birthDate.value.isEmpty() ||
                            requestSignUpArrenderState.gender.value.isEmpty()
                            //la imagen queda pendiente
                            ) {
                            //Mostrar un mensaje de error
                            errorMessageModel.value = "Por favor, llena todos los campos"
                            dialogTitle.value="Espera un momento!"
                            return@Button
                        }

                        //mapear los datos del modelo a un objeto que se pueda enviar al backend


                        val body = RequestSignUpArrenderBody(
                            firstname = requestSignUpArrenderState.firstname.value,
                            lastname = requestSignUpArrenderState.lastname.value,
                            username = requestSignUpArrenderState.username.value,
                            phoneNumber = requestSignUpArrenderState.phoneNumber.value,
                            email = requestSignUpArrenderState.email.value,
                            password = requestSignUpArrenderState.password.value,
                            address = requestSignUpArrenderState.address.value,
                            birthDate = requestSignUpArrenderState.birthDate.value,
                            profilePicture = requestSignUpArrenderState.profilePicture.value,
                            gender = requestSignUpArrenderState.gender.value
                        )



                        //Aca se manda al backend
                        val arrenderRepository= ArrenderRepositoryFactory.getArrenderRepository()

                        arrenderRepository.signUpArrender(body) { apiResponse, errorCode, errorBody ->
                            // Manejar la respuesta exitosa o el error aquí
                            if (apiResponse != null) {
                                dialogTitle.value="En hora buena!"
                                errorMessageModel.value = "Tu registro se completo con exito"
                                //Redirigir al login
                                navigationToSignIn()
                                // Si la respuesta es exitosa
                                println("Respuesta exitosa: $apiResponse")
                            } else {
                                dialogTitle.value="Espera!"
                                errorMessageModel.value = errorBody
                                // Si hubo un error
                                println("Error: Código de estado: $errorCode, Cuerpo del error: $errorBody")
                            }
                        }


                    }) {
                        Text("Registrarme")
                    }

                    Text(
                        text = "Volver",
                        modifier = Modifier.clickable {
                            //Regresar
                            pressOnBack()
                        },
                        style = TextStyle(textDecoration = TextDecoration.Underline)
                    )





                    MessageError(errorMessageModel, dialogTitle.value)
                }


            }
        }
    }



}