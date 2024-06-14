package pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.room.roomdetail

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column

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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.window.Dialog
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import kotlinx.coroutines.delay
import pe.edu.upc.flexiarrendermobile.R
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.factories.RoomRepositoryFactory
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomRequestBody
import pe.edu.upc.flexiarrendermobile.model.data.RegisterRoomState
import pe.edu.upc.flexiarrendermobile.shared.MessageError
import pe.edu.upc.flexiarrendermobile.ui.theme.FlexiArrenderMobileTheme

@Composable
fun RoomDetail(errorMessageModel: MutableState<String?>, finishAddRoom:()->Unit) {

    val registerRoomState = RegisterRoomState()

    val selectedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val showMap = remember { mutableStateOf(false) }


    val context = LocalContext.current

    val userLatitude = remember { mutableStateOf(0.0) }
    val userLongitude = remember { mutableStateOf(0.0) }


    val singleImagePickerLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
            if (uri != null) {
                val inputStream = context.contentResolver.openInputStream(uri)
                val imageSize = inputStream?.available() ?: 0
                println("El tamaño de la imagen es: $imageSize")
                if (imageSize <= 2 * 1024 * 1024) { // 3 MB
                    selectedImageUri.value = uri
                } else {
                    errorMessageModel.value =
                        "La imagen seleccionada excede el tamaño máximo permitido de 3 MB."
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



    val locationPermissionRequest = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            getLocation(context, userLatitude, userLongitude, showMap)
        } else {
            errorMessageModel.value =
                "Se requieren permisos de ubicación para acceder a la ubicación actual."
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
                    modifier = Modifier.padding(10.dp),
                    style = TextStyle(
                        color = Color(0xFF846CD9),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                )


                LabeledTextField(
                    label = { Text("Título", color = Color(0xFF846CD9)) },
                    value = registerRoomState.title.value,
                    onValueChange = {
                        registerRoomState.title.value = it
                    },
                    placeholder = { Text("") },
                    modifier = Modifier.padding(10.dp),
                )


                LabeledTextField(
                    label = { Text("Descripción", color = Color(0xFF846CD9)) },
                    value = registerRoomState.description.value,
                    onValueChange = {
                        registerRoomState.description.value = it
                    },
                    placeholder = { Text("") },
                    modifier = Modifier.padding(10.dp)
                )

                LabeledTextField(
                    label = { Text("Dirección", color = Color(0xFF846CD9)) },
                    value = registerRoomState.address.value,
                    onValueChange = {
                        registerRoomState.address.value = it
                    },
                    placeholder = { Text("") },
                    modifier = Modifier.padding(10.dp)
                )

                Button(
                    onClick = {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) == PackageManager.PERMISSION_GRANTED
                        ) {
                            println("Permisos concedidos")
                            locationPermissionRequest.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        } else {
                            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                            println("Permisos no concedidos")
                        }
                    }
                ) {
                    Text("Ver en el mapa")
                }

                if (showMap.value) {

                    MyGoogleMaps(
                        latitude = userLatitude,
                        longitude = userLongitude,
                        onLocationConfirmed = {
                            registerRoomState.latitude.value = userLatitude.value
                            registerRoomState.longitude.value = userLongitude.value
                        },
                        onClose = {
                            showMap.value = false
                        }
                    )
                }

                // Otros elementos de la interfaz de usuario...





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
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF846CD9)
                ),
                modifier = Modifier
                    .padding()
                    .width(160.dp),
                shape = RoundedCornerShape(10.dp),
                onClick = {
                    // Verificar si se tienen los permisos de lectura externa
                    if (ContextCompat.checkSelfPermission(
                            context,
                            Manifest.permission.READ_EXTERNAL_STORAGE
                        )
                        == PackageManager.PERMISSION_GRANTED
                    ) {
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
                label = { Text("Precio", color = Color(0xFF846CD9)) },
                value = registerRoomState.price.value,
                onValueChange = {
                    registerRoomState.price.value = it
                },
                placeholder = { Text("") },
                modifier = Modifier.padding(10.dp)
            )

            LabeledTextField(
                label = { Text("Universidades cercanas", color = Color(0xFF846CD9)) },
                value = registerRoomState.nearUniversities.value,
                onValueChange = {
                    registerRoomState.nearUniversities.value = it
                },
                placeholder = { Text("") },
                modifier = Modifier.padding(10.dp)
            )

            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color.White,
                    containerColor = Color(0xFF846CD9)
                ),
                modifier = Modifier.padding(),
                onClick = {
                    // Validar los campos y enviar la solicitud de registro
                    if (
                        registerRoomState.title.value.isEmpty() ||
                        registerRoomState.description.value.isEmpty() ||
                        registerRoomState.address.value.isEmpty() ||
                        registerRoomState.price.value.isEmpty() ||
                        registerRoomState.nearUniversities.value.isEmpty()
                    ) {

                        errorMessageModel.value = "Los campos no pueden estar vacios"
                        // Mostrar mensaje de error
                        return@Button
                    }
                    if (selectedImageUri.value == null) {
                        errorMessageModel.value = "Debes seleccionar una imagen"
                        return@Button
                    }

                    val arrenderRepository = ArrenderRepositoryFactory.getArrenderRepository("")
                    val dataLocalArrender = arrenderRepository.getArrender()

                    //Mapear el estado a la solicitud de registro
                    val body = RegisterRoomRequestBody(
                        title = registerRoomState.title.value,
                        description = registerRoomState.description.value,
                        address = registerRoomState.address.value,
                        price = registerRoomState.price.value.toDouble(),
                        nearUniversities = registerRoomState.nearUniversities.value,
                        arrenderId = dataLocalArrender[0].id.toLong()
                    )


                    println("Token en detail: ${dataLocalArrender[0].token}")

                    val roomRepositoryFactory =
                        RoomRepositoryFactory.getRoomRepositoryFactory(dataLocalArrender[0].token)



                    roomRepositoryFactory.registerRoom(
                        body,
                        selectedImageUri,
                        context
                    ) { apiResponse, errorCode, errorBody ->
                        if (apiResponse != null) {
                            println(
                                "Código de estado: $errorCode, Cuerpo de la respuesta: $apiResponse"
                            )
                            finishAddRoom()
                        } else {
                            println(
                                "Código de estado: $errorCode, Cuerpo de la respuesta: $errorBody"
                            )
                        }
                    }


                }) {
                Text(text = "Listo")
            }



            Button(
                colors = ButtonDefaults.buttonColors(
                    contentColor = Color(0xFF846CD9),
                    containerColor = Color.White
                ),
                onClick = {
                    finishAddRoom()

                }) {
                Text(text = "Cancelar")
            }
            MessageError(errorMessageModel, "Espera!")


            }    // Otros campos de texto con etiquetas similares
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



private fun getLocation(context: Context, userLatitude: MutableState<Double>, userLongitude: MutableState<Double>, showMap: MutableState<Boolean>) {
    if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                location?.let {
                    userLatitude.value = it.latitude
                    userLongitude.value = it.longitude
                    showMap.value = true
                }
            }
    } else {
        // Si el permiso no está otorgado, solicitarlo nuevamente
        // Esto debería ser manejado previamente en el flujo de tu aplicación
        // Puedes mostrar un mensaje al usuario para que otorgue los permisos
    }
}

@Composable
fun MyGoogleMaps(
    latitude: MutableState<Double>,
    longitude: MutableState<Double>,
    onLocationConfirmed: () -> Unit,
    onClose: () -> Unit
) {
    var marker: Marker? = null
    var isMapMoving by remember { mutableStateOf(false) }

    // Utilizamos LaunchedEffect para observar los cambios en la ubicación del usuario
    LaunchedEffect(latitude.value, longitude.value) {
        // Actualizamos la posición del marcador cada vez que la ubicación cambia
        marker?.position = LatLng(latitude.value, longitude.value)
   }

// Utilizamos LaunchedEffect para detectar cuando se detiene el movimiento del mapa
    //LaunchedEffect(isMapMoving) {
    //    delay(1000) // Espera 1 segundo después del último movimiento de la cámara
   //     isMapMoving = true
   // }

    Dialog(
        onDismissRequest = onClose
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Creamos un AndroidView para integrar el mapa de Google
            AndroidView(factory = { context ->
                val mapView = MapView(context).apply {
                    // Configuramos el mapa
                    onCreate(null)
                    getMapAsync { googleMap ->
                        // Configuramos la posición de la cámara inicial
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(latitude.value, longitude.value),
                                15f
                            )
                        )

                        // Agregamos un marcador en la ubicación actual
                        marker = googleMap.addMarker(
                            MarkerOptions().position(googleMap.cameraPosition.target)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                        )
                        // Listener para detectar el inicio del movimiento de la cámara
                        googleMap.setOnCameraMoveStartedListener {
                            isMapMoving = true
                        }

                        // Agregamos un listener para seguir la cámara y actualizar el marcador
                        googleMap.setOnCameraMoveListener {
                            marker?.position = googleMap.cameraPosition.target
                            latitude.value = googleMap.cameraPosition.target.latitude
                            longitude.value = googleMap.cameraPosition.target.longitude


                        }

                        // Listener para detectar cuando la cámara se ha detenido
                        googleMap.setOnCameraIdleListener {
                            isMapMoving = false
                            println("Camera has stopped moving: $isMapMoving")
                        }


                    }
                }
                mapView
            }, modifier = Modifier.fillMaxSize()
            ) // El mapa ocupará todo el tamaño disponible


            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.BottomCenter // Alinea el contenido al centro inferior
            ) {
                if (isMapMoving) {
                    CircularProgressIndicator(
                        color = Color(0xFF846CD9)
                    )
                } else {
                    Button(
                        onClick = {
                            println("Latitud: ${latitude.value}, Longitud: ${longitude.value}")
                            onLocationConfirmed()
                            onClose()
                        },
                        modifier = Modifier.padding(16.dp),
                        enabled = !isMapMoving
                    ) {
                        Text("Confirmar")
                    }
                }
            }
        }
    }
}



