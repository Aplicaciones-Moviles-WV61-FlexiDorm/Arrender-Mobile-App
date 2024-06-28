package pe.edu.upc.flexiarrendermobile.ui.signup

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderBody
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderState
import pe.edu.upc.flexiarrendermobile.shared.Loader
import pe.edu.upc.flexiarrendermobile.shared.MessageError
import pe.edu.upc.flexiarrendermobile.ui.theme.FlexiArrenderMobileTheme
import java.util.Calendar
import java.util.Date
import java.util.TimeZone


@OptIn(ExperimentalMaterial3Api::class)//Para poder usar el DatePicker
@Composable
fun SignUpSecondStep(
    requestSignUpArrenderState: RequestSignUpArrenderState,
    errorMessageModel: MutableState<String?>,
    pressOnBack: () -> Unit,
    navigationToSignIn: () -> Unit,
    isLoading: MutableState<Boolean>,
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
                .padding(paddingValues)
                .background(color = Color(0xFF846CD9)),
            contentAlignment = Alignment.Center
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .fillMaxHeight(0.8f),
                colors= CardDefaults.cardColors(
                    containerColor=Color.White
                )
            ) {

                Column(
                    modifier= Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    //El card es un contenedor que nos permite tener un contenedor con sombra

                    Text(
                        text = "Estas a un paso de usar FlexiDorm",
                        style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 24.sp, color = Color(0xFF846CD9))
                    )

                    Text(
                        text = "Estas listo arrendador?",
                        style= TextStyle( color = Color(0xFF846CD9)),
                        modifier = Modifier.padding(10.dp)
                    )


                        //Username
                        OutlinedTextField(
                            value = requestSignUpArrenderState.email.value,
                            onValueChange = {
                                requestSignUpArrenderState.email.value = it
                            },
                            modifier=Modifier.height(60.dp),
                            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
                            label = { Text("Correo", color = Color(0xFF846CD9)) },
                            shape = RoundedCornerShape(15.dp), // Redondear los bordes
                            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
                            colors= OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF846CD9),
                            )
                        )


                        OutlinedTextField(
                            value = requestSignUpArrenderState.firstname.value,
                            onValueChange = {
                                requestSignUpArrenderState.firstname.value = it
                            },
                            modifier=Modifier.height(60.dp),
                            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
                            label = { Text("Primer nombre", color = Color(0xFF846CD9)) },
                            shape = RoundedCornerShape(15.dp), // Redondear los bordes
                            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
                            colors=OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF846CD9),
                            )
                        )

                        OutlinedTextField(
                            value = requestSignUpArrenderState.lastname.value,
                            onValueChange = {
                                requestSignUpArrenderState.lastname.value = it
                            },
                            modifier=Modifier.height(60.dp),
                            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
                            label = { Text("Apellido", color = Color(0xFF846CD9)) },
                            shape = RoundedCornerShape(15.dp), // Redondear los bordes
                            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
                            colors=OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF846CD9),
                            )
                        )

                        OutlinedTextField(
                            value = requestSignUpArrenderState.phoneNumber.value,
                            onValueChange = {
                                requestSignUpArrenderState.phoneNumber.value = it
                            },
                            modifier=Modifier.height(60.dp),
                            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
                            label = { Text("Telefono", color = Color(0xFF846CD9)) },
                            shape = RoundedCornerShape(15.dp), // Redondear los bordes
                            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
                            colors=OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF846CD9),
                            )
                        )


                        OutlinedTextField(
                            value = requestSignUpArrenderState.address.value,
                            onValueChange = {
                                requestSignUpArrenderState.address.value = it
                            },
                            modifier=Modifier.height(60.dp),
                            textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 15.sp),
                            label = { Text("Domicilio", color = Color(0xFF846CD9)) },
                            shape = RoundedCornerShape(15.dp), // Redondear los bordes
                            singleLine = true, // Para evitar que el texto se divida en múltiples líneas
                            colors=OutlinedTextFieldDefaults.colors(
                                unfocusedBorderColor = Color(0xFF846CD9),
                            )
                        )

                    Spacer(modifier = Modifier.height(10.dp))


                        Row {
                            Column {
                                Text(text = "Fecha de nacimiento", style = TextStyle(color = Color(0xFF846CD9)))

                                Spacer(modifier = Modifier.height(3.dp))

                                Button(
                                    modifier= Modifier
                                        .height(45.dp)
                                        .padding(0.dp),
                                    shape= RoundedCornerShape(20.dp),
                                    colors= ButtonDefaults.buttonColors(contentColor =Color.White, containerColor = Color(0xFF846CD9)),
                                    onClick = {
                                    showDialog.value=true
                                }) {
                                    Text(text = "Seleccionar")
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
                                    Text(text = "$formattedDay-$formattedMonth-${year.value}",style= TextStyle(fontSize = (13.sp), color = Color(0xFF846CD9)))
                                }


                            }

                            Spacer(modifier = Modifier.width(20.dp))


                            //comboBox para el sexo

                            Column {

                                Text(text = "Genero", style = TextStyle(color = Color(0xFF846CD9)))

                                ExposedDropdownMenuBox(
                                    expanded = isExpanded.value,
                                    onExpandedChange ={isExpanded.value=it},
                                   modifier= Modifier
                                       .width(145.dp)
                                       .height(52.dp)
                                       .padding(3.dp)
                                ) {
                                    TextField(
                                        value = selectedOption.value,
                                        onValueChange ={},
                                        readOnly = true,
                                        trailingIcon = {
                                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = isExpanded.value)
                                        },
                                        colors = ExposedDropdownMenuDefaults.textFieldColors(),
                                        modifier=Modifier.menuAnchor(),
                                        placeholder = { Text("Seleccionar",style= TextStyle(fontSize = (13.sp))) },
                                        textStyle = TextStyle(fontSize = (13.sp))
                                    )
                                    ExposedDropdownMenu(
                                        expanded =isExpanded.value ,
                                        onDismissRequest = { isExpanded.value = false },
                                        modifier = Modifier.width(145.dp)
                                    ) {
                                        DropdownMenuItem(
                                            text = { Text(text = "MALE",style= TextStyle(fontSize = (13.sp))) },
                                            onClick = {
                                                selectedOption.value="MALE"
                                                isExpanded.value=false
                                            }// Cambia el color de fondo del elemento del menú
                                        )
                                        DropdownMenuItem(
                                            text = { Text(text = "FEMALE",style= TextStyle(fontSize = (13.sp))) },
                                            onClick = {
                                                selectedOption.value="FEMALE"
                                                isExpanded.value=false
                                            }
                                        )

                                    }
                                    //creo que aqui puede ir el codigo de asignar el valor seleccionado al modelo
                                }
                            }
                        }
                    Spacer(modifier = Modifier.height(10.dp))


                    //Asignar el valor seleccionado al modelo
                        requestSignUpArrenderState.gender.value=selectedOption.value



                        Button(colors=ButtonDefaults.buttonColors(contentColor =Color.White, containerColor = Color(0xFF846CD9)),
                            onClick = {

                                isLoading.value=true

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
                            val arrenderRepository= ArrenderRepositoryFactory.getArrenderRepository("")

                            arrenderRepository.signUpArrender(body) { apiResponse, errorCode, errorBody ->
                                isLoading.value=false
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
                            style = TextStyle(textDecoration = TextDecoration.Underline,color=Color(0xFF846CD9))
                        )


                        MessageError(errorMessageModel, dialogTitle.value)

                        Loader(isLoading =isLoading);


                }

            }
        }
    }



}

