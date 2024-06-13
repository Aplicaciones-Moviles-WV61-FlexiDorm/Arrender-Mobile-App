package pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.navDrawer

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pe.edu.upc.flexiarrendermobile.factories.ArrenderRepositoryFactory
import pe.edu.upc.flexiarrendermobile.shared.routes.Routes
import pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.profile.Profile
import pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.profile.UpdateProfile
import pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.reservations.Reservation
import pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.room.roomdetail.RoomDetail
import pe.edu.upc.flexiarrendermobile.ui.screensSuccessfulLogin.room.roomlist.RoomList


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavDrawer(logoutEvent: MutableState<Boolean>) {
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current.applicationContext
    val navController = rememberNavController()

    val errorMessage = remember { mutableStateOf<String?>(null) }
    val selectedItem = remember { mutableStateOf("Mis habitaciones") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(300.dp)
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(0xFF846CD9))
                        .fillMaxSize()
                ) {
                    Box(
                        modifier = Modifier
                            .background(Color(0xFF846CD9))
                            .fillMaxWidth()
                            .height(60.dp)
                    ) {
                        IconButton(
                            onClick = {
                                coroutineScope.launch { drawerState.close() }
                            },
                            modifier = Modifier
                                .padding(10.dp)
                                .align(Alignment.CenterStart)
                        ) {
                            Icon(
                                Icons.Rounded.Menu, contentDescription = "Button to open the drawer",
                                modifier = Modifier
                                    .align(Alignment.Center)
                                    .size(40.dp),
                                tint = Color.White
                            )
                        }
                    }
                    HorizontalDivider(
                        modifier = Modifier.padding(10.dp),
                        color = Color(0xFF846CD9)
                    )

                    CustomNavigationDrawerItem(
                        text = "Mis habitaciones",
                        icon = Icons.Default.Search,
                        isSelected = selectedItem.value == "Mis habitaciones",
                        onItemClick = {
                            selectedItem.value = "Mis habitaciones"
                            coroutineScope.launch { drawerState.close() }
                            navController.navigate(Routes.RoomList.route) { popUpTo(0) }
                        }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(2.dp),
                        color = Color(0xFF846CD9)
                    )

                    CustomNavigationDrawerItem(
                        text = "Mis reservas",
                        icon = Icons.Default.Home,
                        isSelected = selectedItem.value == "Mis reservas",
                        onItemClick = {
                            selectedItem.value = "Mis reservas"
                            coroutineScope.launch { drawerState.close() }
                            navController.navigate(Routes.Reservation.route) { popUpTo(0) }
                        }
                    )

                    HorizontalDivider(
                        modifier = Modifier.padding(2.dp),
                        color = Color(0xFF846CD9)
                    )

                    CustomNavigationDrawerItem(
                        text = "Mi perfil",
                        icon = Icons.Default.Person,
                        isSelected = selectedItem.value == "Mi perfil",
                        onItemClick = {
                            selectedItem.value = "Mi perfil"
                            coroutineScope.launch { drawerState.close() }
                            navController.navigate(Routes.Profile.route) { popUpTo(0) }
                        }
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CustomNavigationDrawerItemLogout(
                            text = "Cerrar sesión",
                            icon = Icons.Default.ExitToApp,
                            isSelected = false,
                            onItemClick = {
                                val arrenderRepository = ArrenderRepositoryFactory.getArrenderRepository("")
                                arrenderRepository.deleteAllArrenderDataLocal()
                                logoutEvent.value = true // Activa el evento de cerrar sesión
                            }
                        )
                    }
                }
            }
        }
    ) {
        Scaffold(
            topBar = {
                CustomAppBar(
                    title = "Hola arrendador!",
                    backgroundColor = Color(0xFF846CD9),
                    onNavigationIconClick = {
                        coroutineScope.launch { drawerState.open() }
                    }
                )
            }
        ) {
            NavHost(navController = navController, startDestination = Routes.RoomList.route) {
                composable(Routes.RoomList.route) {
                    RoomList(
                        addRoom = { navController.navigate(Routes.RoomDetail.route) }
                    )
                }
                composable(Routes.RoomDetail.route) {
                    RoomDetail(
                        errorMessageModel = errorMessage,
                        finishAddRoom = { navController.popBackStack() }
                    )
                }

                composable(Routes.Profile.route) {
                    Profile(
                        updateProfile = { navController.navigate(Routes.UpdateProfile.route) }
                    )
                }

                composable(Routes.UpdateProfile.route) {
                    UpdateProfile(
                        popBack = { navController.popBackStack() }
                    )
                }

                composable(Routes.Reservation.route) {
                    Reservation()
                }
            }
        }
    }
}



@Composable
fun CustomNavigationDrawerItem(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color.White else Color(0xFF846CD9)
    val contentColor = if (isSelected) Color(0xFF846CD9) else Color.White

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(backgroundColor)
            .clickable(onClick = onItemClick)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = contentColor,
        )
    }
}

@Composable
fun CustomNavigationDrawerItemLogout(
    text: String,
    icon: ImageVector,
    isSelected: Boolean,
    onItemClick: () -> Unit
) {
    val backgroundColor = if (isSelected) Color.White else Color(0xFF846CD9)
    val contentColor = if (isSelected) Color(0xFF846CD9) else Color.White

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .background(backgroundColor)
            .clickable(onClick = onItemClick)
            .padding(16.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = contentColor,
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            color = contentColor,
        )
    }
}


@Composable
fun CustomAppBar(
    title: String,
    backgroundColor: Color,
    onNavigationIconClick: () -> Unit
) {
    Surface(
        color = backgroundColor,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .padding(horizontal = 5.dp)
        ) {
            IconButton(
                onClick = onNavigationIconClick,
                modifier = Modifier
                    .padding(start = 8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open Drawer",
                    tint = Color.White,
                    modifier = Modifier.size(40.dp)
                )
            }
            Text(
                text = title,
                color = Color.White,
                modifier = Modifier
                    .padding(start = 8.dp),
                fontSize = 18.sp
            )
        }
    }
}
