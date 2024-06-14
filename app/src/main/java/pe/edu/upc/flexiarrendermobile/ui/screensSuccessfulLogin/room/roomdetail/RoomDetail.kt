package pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.room.roomdetail

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import pe.edu.upc.flexiarrendermobile.R
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.factories.RoomRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomRequestBody
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomState
import pe.edu.upc.flexiarrendermobile.model.data.RequestSignUpArrenderState
import pe.edu.upc.flexiarrendermobile.shared.MessageError
import pe.edu.upc.flexiarrendermobile.ui.signup.SignUpSignUpFirstStep
import pe.edu.upc.flexiarrendermobile.ui.theme.FlexiArrenderMobileTheme
import java.io.File

@Composable
fun RoomDetail(errorMessageModel: MutableState<String?>, finishAddRoom:()->Unit) {

    val registerRoomState= RegisterRoomState()

    val selectedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val context = LocalContext.current


    val singleImagePickerLauncher = rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
        if (uri != null) {
            val inputStream = context.contentResolver.openInputStream(uri)
            val imageSize = inputStream?.available() ?: 0
            println( "El tamaño de la imagen es: $imageSize")
            if (imageSize <= 2 * 1024 * 1024) { // 3 MB
                selectedImageUri.value = uri
            } else {
                errorMessageModel.value = "La imagen seleccionada excede el tamaño máximo permitido de 3 MB."
            }
        }
    }


    val requestPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            // Permiso concedido, lanzar el selector de imágenes
            singleImagePickerLauncher.launch(
                PickVisualMediaRequest(
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                )
            )
        } else {
            // Permiso denegado, mostrar mensaje de error o solicitar permisos nuevamente
            errorMessageModel.value = "Se requieren permisos para acceder a la galería de imágenes."
        }
    }



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
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(56.dp))


                Text(
                    text = "Agreguemos una habitacion!",
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
                BasicTextField(
                    value = "Imagen",
                    onValueChange = {},
                    textStyle = TextStyle(color = Color(0xFF846CD9), fontSize = 17.sp),
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                )


                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray)
                ) {
                    if (selectedImageUri.value != null) {
                        AsyncImage(
                            model = selectedImageUri.value,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    } else {

                            Icon(
                                painter = painterResource(id = R.drawable.baseline_image_search_24),
                                contentDescription = "image default",
                                tint = Color.White,
                                modifier = Modifier.size(70.dp)
                            )


                    }
                }

                Button(
                        colors= ButtonDefaults.buttonColors(contentColor =Color.White, containerColor = Color(0xFF846CD9)),
                        modifier=Modifier
                            .padding()
                            .width(160.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            // Verificar si se tienen los permisos de lectura externa
                            if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE)
                                == PackageManager.PERMISSION_GRANTED) {
                                System.out.println("Permisos concedidos")
                                // Si los permisos están concedidos, lanzar el selector de imágenes
                                singleImagePickerLauncher.launch(
                                    PickVisualMediaRequest(
                                        ActivityResultContracts.PickVisualMedia.ImageOnly
                                    )
                                )
                            } else {
                                // Si los permisos no están concedidos, solicitarlos al usuario
                                requestPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
                            }
                        }
                    ) {
                        if (selectedImageUri.value != null) {
                            Text("Cambiar imagen")
                        } else {
                            Text("Cargar imagen")
                        }

                    }




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
                        registerRoomState.price.value.isEmpty() ||
                        registerRoomState.nearUniversities.value.isEmpty()){

                        errorMessageModel.value= "Los campos no pueden estar vacios"
                        // Mostrar mensaje de error
                        return@Button
                    }
                        if (selectedImageUri.value == null) {
                            errorMessageModel.value = "Debes seleccionar una imagen"
                            return@Button
                        }

                    val arrenderRepository= ArrenderRepositoryFactory.getArrenderRepository("")
                    val dataLocalArrender = arrenderRepository.getArrender()

                    //Mapear el estado a la solicitud de registro
                    val body= RegisterRoomRequestBody(
                        title = registerRoomState.title.value,
                        description = registerRoomState.description.value,
                        address = registerRoomState.address.value,
                        price = registerRoomState.price.value.toDouble(),
                        nearUniversities = registerRoomState.nearUniversities.value,
                        arrenderId = dataLocalArrender[0].id.toLong()
                    )


                    println( "Token en detail: ${dataLocalArrender[0].token}")

                    val roomRepositoryFactory= RoomRepositoryFactory.getRoomRepositoryFactory(dataLocalArrender[0].token)



                    roomRepositoryFactory.registerRoom(body, selectedImageUri,context){apiResponse, errorCode, errorBody ->
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








@SuppressLint("UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun RoomDetailPreview() {
    FlexiArrenderMobileTheme {
        RoomDetail(errorMessageModel = mutableStateOf<String?>(null), finishAddRoom = {})

    }
}